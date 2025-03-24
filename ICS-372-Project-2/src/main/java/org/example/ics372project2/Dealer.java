package org.example.ics372project2;
import java.util.ArrayList;
import java.util.List;

public class Dealer {
    private String dealerID;
    private String dealerName;   //new field dealerName, default empty string
    private boolean isAcquisitionEnabled;
    private List<Vehicle> vehicleList;

    public Dealer(String dealerID) {
        this.dealerID = dealerID;
        this.dealerName = "";
        this.vehicleList = new ArrayList<>();
        this.isAcquisitionEnabled = true;
    }

    public void enableAcquisition() {
        this.isAcquisitionEnabled = true;
    }

    public void disableAcquisition() {
        this.isAcquisitionEnabled = false;
    }

    public boolean addVehicle(Vehicle vehicle) {
        if (isAcquisitionEnabled) {
            vehicleList.add(vehicle);
            return true;
        }
        System.out.println("Dealer disabled.");
        return false;
    }

    public String getDealerID() {
        return dealerID;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public boolean getIsAcquisitionEnabled() {
        return isAcquisitionEnabled;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    /**
     * loops through the list of vehicles and removes
     * if a match was found based on the given vehicle object
     *
     * @param vehicle the vehicle object that is being removed
     */
    public void removeVehicle(Vehicle vehicle) {
        vehicleList.removeIf(v -> v.getVehicleID().equals(vehicle.getVehicleID()));
    }

    //Loan a vehicle only if it's not a sports car
    public boolean loanVehicle(String vehicleID) {
        for (Vehicle v : vehicleList) {
            if (v.getVehicleID().equals(vehicleID)) {
                if (v.getModel().toLowerCase().contains("Sports")) {
                    return false; //cannot rent sports car
                }
                v.setLoaned(true);
                return true;
            }
        }
        return false;
    }

    //Return a loaned vehicle
    public boolean returnVehicle(String vehicleID) {
        for (Vehicle v : vehicleList) {
            if (v.getVehicleID().equals(vehicleID) && v.getIsLoaned()) {
                v.setLoaned(false);
                return true;
            }
        }
        return false;
    }

    //getting a list of loaned vehicle
    public List<Vehicle> getLoanedVehicles(){
      List<Vehicle> lonedVehicleList = new ArrayList<>();
      for(Vehicle v: vehicleList){
          if(v.isLoaned){
              lonedVehicleList.add(v);
          }
      }
      return lonedVehicleList;
    }

}


