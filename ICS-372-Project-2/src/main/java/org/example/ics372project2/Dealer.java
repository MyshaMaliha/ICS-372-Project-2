package org.example.ics372project2;
import java.util.ArrayList;
import java.util.List;

public class Dealer {
    private String dealerID;
    private String dealerName;   //new field dealerName, default empty string
    private boolean isAcquisitionEnabled;
    private List<Vehicle> vehicleList;

    public Dealer(String dealerID) {  //initially all dealer's isAcquisition is TRUE
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
    public void setAcquisitionEnabled(boolean isAcquisitionEnabled){
        this.isAcquisitionEnabled = isAcquisitionEnabled;
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



}


