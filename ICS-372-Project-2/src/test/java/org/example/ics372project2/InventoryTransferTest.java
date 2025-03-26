package org.example.ics372project2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTransferTest {
    private InventoryTransfer inventoryTransfer;
    private Set<Dealer> dealerSet;
    private Dealer dealerFrom;
    private Dealer dealerTo;
    private Vehicle vehicle;
    private Dealer dealerID;
    private Vehicle testVehicle;

    @BeforeEach
    void setUp() {
        dealerSet = new HashSet<>();
        dealerID = new Dealer("001");
        dealerID.setDealerName("Test Dealer");

        testVehicle = new Sedan("V-01", "Toyota", "Camry",
                0L, 23000.0, false);

        dealerSet.add(dealerID);

    }

    @Test
    void FindDealer_Test() {
        InventoryTransfer inventoryTransfer = new InventoryTransfer();
        Dealer foundDealer = inventoryTransfer.findDealer(dealerID.getDealerID(), dealerSet);

        assertNotNull(foundDealer, "Dealer should be found in the set");
        assertEquals(dealerID, foundDealer, "Found dealer should match the expected dealer");
    }

    @Test
    void TransferVehicle_Test() throws IOException {
        InventoryTransfer inventoryTransfer = new InventoryTransfer();


        Dealer dealerTo = new Dealer("002");
        dealerSet.add(dealerTo);


        dealerID.addVehicle(testVehicle);

        boolean success = inventoryTransfer.transferVehicle(dealerSet, dealerID.getDealerID(), dealerTo.getDealerID(), testVehicle.getVehicleID());

        assertTrue(success, "Transfer should be successful");
        assertFalse(dealerID.getVehicleList().contains(testVehicle), "Source dealer should no longer have the vehicle");
        assertTrue(dealerTo.getVehicleList().contains(testVehicle), "Destination dealer should have the transferred vehicle");
    }

    @Test
    void FindVehicle_Test() {
        InventoryTransfer inventoryTransfer = new InventoryTransfer();


        dealerID.addVehicle(testVehicle);


        Vehicle foundVehicle = inventoryTransfer.findVehicleById(dealerID, testVehicle.getVehicleID());
        assertNotNull(foundVehicle, "Vehicle should be found in the dealer's inventory");
        assertEquals(testVehicle, foundVehicle, "Found vehicle should match the expected vehicle");


        Vehicle notFoundVehicle = inventoryTransfer.findVehicleById(dealerID, "NonExistentVehicle");
        assertNull(notFoundVehicle, "Should return null for a non-existent vehicle");
    }


    @AfterEach
    void tearDown() {
        dealerSet.clear();
    }

}
