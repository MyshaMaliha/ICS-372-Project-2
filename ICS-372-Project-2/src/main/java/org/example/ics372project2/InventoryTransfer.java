package org.example.ics372project2;

import java.util.Scanner;
import java.util.Set;

/**
 * transfers inventory from one dealer to another
 */
public class InventoryTransfer {
    public void transferInventory(Set<Dealer> dealerSet){
        Scanner s = new Scanner(System.in);

        // getting input for dealer transferring to
        System.out.println("Enter dealer ID transferring to: ");
        String dealerIdTo = s.next();
        Dealer dealerTo = findDealer(dealerIdTo, dealerSet);

        // validating if dealer exists
        if(dealerTo == null){
            System.out.println("Not a Valid dealer");
            return;
        }

        // getting input for dealer transferring from
        System.out.println("Enter dealer ID transferring from: ");
        String dealerIdFrom = s.next();
        Dealer dealerFrom = findDealer(dealerIdFrom, dealerSet);

        // validating if dealer exists
        if(dealerFrom == null){
            System.out.println("Not a Valid dealer");
            return;
        }

        boolean continueTransfer = true;

        while (continueTransfer) {
            System.out.println("Enter Vehicle ID:");
            String vehicleID = s.next();

            Vehicle vehicleToTransfer = findVehicleById(dealerFrom, vehicleID);

            if (vehicleToTransfer != null) {
                dealerFrom.removeVehicle(vehicleToTransfer);
                dealerTo.addVehicle(vehicleToTransfer);
                System.out.println("Vehicle " + vehicleID + " successfully transferred.");
            } else {
                System.out.println("Error: Vehicle ID not found.");
            }

            System.out.println("Do you want to add another vehicle? (yes/no):");
            String response = s.next().toLowerCase();

            if (response.equals("no")) {
                continueTransfer = false;
                System.out.println("Transfer is done!.");
            }
        }




    }

    /**
     * helper method that returns the dealerId from the set given to
     * find a dealer that matches the id
     * @param dealerID the dealer ID of the dealer that needs to be found
     * @param dealerSet the set of dealers used to search for the dealer
     * @return the dealer that matches the dealerId, or null if not found
     */
    private Dealer findDealer(String dealerID, Set<Dealer> dealerSet){
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

    private Vehicle findVehicleById(Dealer dealer, String vehicleID) {
        for (Vehicle v : dealer.getVehicleList()) {
            if (v.getVehicleID().equals(vehicleID)) {
                return v;
            }
        }
        return null;
    }


}
