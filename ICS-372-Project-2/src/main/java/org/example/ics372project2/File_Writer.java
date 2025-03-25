package org.example.ics372project2;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class File_Writer {

    /**
     * @param dealerSet
     * @throws IOException
     */
    static void exportJSON(Set<Dealer> dealerSet) throws IOException {
        ObjectMapper objMap = new ObjectMapper();               //Used to convert JAVA obj into JSON
        objMap.enable(SerializationFeature.INDENT_OUTPUT);         //makes output JSON more readable
        Map<String, List<Map<String, Object>>> outerList = new LinkedHashMap<>();     //outerList:Map-> key="Car Inventory", value= List of vehicles (inner List)
        //Stores the entire inventory under a key ("Car Inventory"), making the JSON structured.
        List<Map<String, Object>> innerList = new ArrayList<>();             //innerList: list of multiple vehicle records(For Each Vehicle-> Vehicle's attributed stored in a Map(key-value pairs)

        for (Dealer d : dealerSet) {   //loops through all dealers in dealerSet
            for (Vehicle v : d.getVehicleList()) {    //loops through all vehicles owned by that dealer
                Map<String, Object> vehicleData = new LinkedHashMap<>();     //Stores each  attribute of a vehicle as a Map(key-value pairs)
                vehicleData.put("dealership_id", d.getDealerID());    // HashMap.put("key", value) | assuming getDealerID() exists
                vehicleData.put("dealer_name", d.getDealerName());    // Add the dealer name here
                vehicleData.put("is_acquisition_enabled", d.getIsAcquisitionEnabled());
                vehicleData.put("vehicle_type", v.getType());
                vehicleData.put("vehicle_manufacturer", v.getManufacturer());
                vehicleData.put("vehicle_model", v.getModel());
                vehicleData.put("vehicle_id", v.getVehicleID());       // Assuming getId() exists
                vehicleData.put("price", v.getPrice());
                vehicleData.put("acquisition_date", v.getAcquisitionDate());
                vehicleData.put("is_loaned", v.getIsLoaned());

                innerList.add(vehicleData);     //add the  vehicle Obj to innerList (list)
            }
        }
        innerList.sort(Comparator.comparing(m -> (String) m.get("dealership_id")));   //sort by dealership_id to group similar dealerships together
        outerList.put("car_inventory", innerList);
        objMap.writerWithDefaultPrettyPrinter().writeValue(new File("Dealers_Vehicles.json"), outerList);    //using: writeValue( File resultFile, Obj value) from Jackson's ObjectMapper class
    }
}

