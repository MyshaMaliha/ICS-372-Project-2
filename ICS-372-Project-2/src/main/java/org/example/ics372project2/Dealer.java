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
    public void enableAcquisition(){this.isAcquisitionEnabled = true;}
    public void disableAcquisition(){this.isAcquisitionEnabled = false;}
    public boolean addVehicle(Vehicle vehicle){
        if(isAcquisitionEnabled){
            vehicleList.add(vehicle);
            return true;
        }
        System.out.println("Dealer disabled.");
        return false;
    }
    public String getDealerID(){return dealerID;}
    public List<Vehicle> getVehicleList(){return vehicleList; }
    public boolean getIsAcquisitionEnabled(){return isAcquisitionEnabled;}
    public String getDealerName(){return dealerName;}
    public void setDealerName(String dealerName){this.dealerName = dealerName;}
}

