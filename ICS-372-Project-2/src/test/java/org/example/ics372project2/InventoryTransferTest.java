package org.example.ics372project2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTransferTest {
    private Dealer dealerID;
    private Vehicle testVehicle;
    private Set<Dealer> dealerSet;

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
        Dealer foundDealer = null;
        for (Dealer d : dealerSet) {
            if (d.getDealerID().equals(dealerID.getDealerID())) {
                foundDealer = d;
                break;
            }
        }
        assertNotNull(foundDealer, "Dealer should be found in the set");
        assertEquals(dealerID, foundDealer, "Found dealer should match the expected dealer");
    }
}
