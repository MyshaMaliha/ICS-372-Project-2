package org.example.ics372project2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DealerTest {
    private Dealer dealer;
    private Vehicle testVehicle;


    // set up before we run the methods
    @BeforeEach
    void setUp() {
        dealer = new Dealer("001");
        dealer.setDealerName("Test Dealer");
        testVehicle = new Sedan("V-01", "Toyota", "Camry",
                0L, 23000.0, false);
    }


    // Test to see when enabled dealer should receive the new vehicles
    @Test
    void enableAcquisition_Test() {
        dealer.enableAcquisition();
        assertTrue(dealer.getIsAcquisitionEnabled());
    }



    // when disabled dealer should reject the new vehicles
    @Test
    void disableAcquisition_Test() {
        dealer.disableAcquisition();
        assertFalse(dealer.getIsAcquisitionEnabled());
    }



    // checks if adding vehicle works when dealer is enabled
    @Test
    void addVehicle_whenEnabled_Test() {
        dealer.enableAcquisition();
        assertTrue(dealer.addVehicle(testVehicle));
    }


    // ensures if adding vehicle fails when dealer is disabled
    @Test
    void addVehicle_whenDisabled_Test() {
        dealer.disableAcquisition();
        assertFalse(dealer.addVehicle(testVehicle));
    }


    // verifies if vehicle list contains what we added to it
    @Test
    void getVehicleList_Test() {
        dealer.enableAcquisition();
        dealer.addVehicle(testVehicle);
        assertEquals(1, dealer.getVehicleList().size());
    }


    // verifies removing a vehicle actually and checking if it actually removes it or not
    @Test
    void removeVehicle_Test() {
        dealer.enableAcquisition();
        dealer.addVehicle(testVehicle);
        dealer.removeVehicle(testVehicle);
        assertEquals(0, dealer.getVehicleList().size());
    }
}