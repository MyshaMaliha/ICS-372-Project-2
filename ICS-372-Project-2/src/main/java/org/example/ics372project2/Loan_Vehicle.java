package org.example.ics372project2;

import java.util.ArrayList;
import java.util.List;

public class Loan_Vehicle {


    //Loan a vehicle only if it's not a sports car
    public static boolean loanVehicle(Dealer dealer,String vehicleID) {

        for (Vehicle v : dealer.getVehicleList()) {
            if (v.getVehicleID().equals(vehicleID)) {
                if (v.getModel().toLowerCase().contains("sports car")) {
                    return false; //cannot rent sports car
                }
                v.setLoaned(true);
                return true;
            }
        }
        return false;
    }

    //Return a loaned vehicle
    public static boolean returnVehicle(Dealer dealer, String vehicleID) {
        for (Vehicle v : dealer.getVehicleList()) {
            if (v.getVehicleID().equals(vehicleID) && v.getIsLoaned()) {
                v.setLoaned(false);
                return true;
            }
        }
        return false;
    }

    //getting a list of loaned vehicleget
    public static List<Vehicle> getLoanedVehicles(Dealer dealer){
        List<Vehicle> lonedVehicleList = new ArrayList<>();
        for(Vehicle v: dealer.getVehicleList()){
            if(v.isLoaned){
                lonedVehicleList.add(v);
            }
        }
        return lonedVehicleList;
    }
}
