package org.example.ics372project2;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to handle loaning and returning of vehicles from a dealer's inventory.
 * It provides methods for loaning a vehicle, returning a loaned vehicle, and getting a list of loaned vehicles.
 */
public class Loan_Vehicle {


    /**
     * Loans a vehicle if it is not a sports car.
     * @param dealer    The dealer from which the vehicle is being loaned.
     * @param vehicleID The ID of the vehicle to be loaned.
     * @return {@code true} if the vehicle is successfully loaned, {@code false} if the vehicle is a sports car or not found.
     */
    public static boolean loanVehicle(Dealer dealer,String vehicleID) {

        for (Vehicle v : dealer.getVehicleList()) {
            if (v.getVehicleID().equals(vehicleID)) {
                if (v.getType().toLowerCase().contains("sports car")) {
                    return false; //cannot rent sports car
                }
                v.setLoaned(true);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a loaned vehicle back to the dealer's inventory.
     * @param dealer    The dealer to whom the vehicle is being returned.
     * @param vehicleID The ID of the vehicle to be returned.
     * @return {@code true} if the vehicle is successfully returned, {@code false} if the vehicle is not found or not loaned.
     */
    public static boolean returnVehicle(Dealer dealer, String vehicleID) {
        for (Vehicle v : dealer.getVehicleList()) {
            if (v.getVehicleID().equals(vehicleID) && v.getIsLoaned()) {
                v.setLoaned(false);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a list of all vehicles that are currently loaned out by the dealer.
     * @param dealer The dealer whose loaned vehicles are to be retrieved.
     * @return A list of loaned vehicles.
     */
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
