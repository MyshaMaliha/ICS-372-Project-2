package org.example.ics372project2;

import java.util.Scanner;
import java.util.Set;

/**
 * transfers inventory from one dealer to another
 */
public class InventoryTransfer {

    /**
     * helper method that returns the dealerId from the set given to
     * find a dealer that matches the id
     * @param dealerID the dealer ID of the dealer that needs to be found
     * @param dealerSet the set of dealers used to search for the dealer
     * @return the dealer that matches the dealerId, or null if not found
     */
    public Dealer findDealer(String dealerID, Set<Dealer> dealerSet){
        for(Dealer dealer: dealerSet){
            if(dealer.getDealerID().equals(dealerID)){
                return dealer;
            }
        }
        return null;
    }

    /**
     * helper method that returns the Vehicle from dealer set given
     *
     * @param dealer the set of dealers used to search for the dealer
     * @param vehicleID the vehicle ID of the vehicle that needs to be found
     * @return the vehicle that matches the vehicleID, or null if not found
     */

    public Vehicle findVehicleById(Dealer dealer, String vehicleID) {
        for (Vehicle v : dealer.getVehicleList()) {
            if (v.getVehicleID().equals(vehicleID)) {
                return v;
            }
        }
        return null;
    }


}
