package org.example.ics372project2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class Loan_VehicleTest {

    private Dealer dealer;
    private Vehicle suv;
    private Vehicle sedan;
    private Vehicle sportsCar;

    @BeforeEach
    void setUp() {
        // Initialize dealer and vehicles
        dealer = new Dealer("123");
        suv = new SUV("V123", "Toyota", "Highlander", 0L, 35000.00, false); // not loaned
        sedan = new Sedan("V124", "Honda", "Civic", 0L, 20000.00, false); // not loaned
        sportsCar = new SportsCar("V125", "Ferrari", "488", 0L, 300000.00, false); // not loaned

        // Add vehicles to dealer's vehicle list
        dealer.getVehicleList().add(suv);
        dealer.getVehicleList().add(sedan);
        dealer.getVehicleList().add(sportsCar);
    }

    @Test
    void testLoanVehicleSuccessful() {
        // Test loaning a non-sports car vehicle
        boolean result = Loan_Vehicle.loanVehicle(dealer, "V123");
        assertTrue(result, "SUV should be loaned successfully.");
        assertTrue(suv.getIsLoaned(), "The SUV should be marked as loaned.");
    }

    @Test
    void testLoanVehicleSportsCar() {
        // Test loaning a sports car (should fail)
        boolean result = Loan_Vehicle.loanVehicle(dealer, "V125");
        assertFalse(result, "Sports car should not be loaned.");
        assertFalse(sportsCar.getIsLoaned(), "The sports car should remain not loaned.");
    }

    @Test
    void testLoanVehicleNotFound() {
        // Test loaning a vehicle that doesn't exist
        boolean result = Loan_Vehicle.loanVehicle(dealer, "V999");
        assertFalse(result, "Non-existent vehicle should not be loaned.");
    }

    @Test
    void testReturnLoanedVehicle() {
        // Loan a vehicle and then return it
        Loan_Vehicle.loanVehicle(dealer, "V123");
        boolean result = Loan_Vehicle.returnVehicle(dealer, "V123");
        assertTrue(result, "The vehicle should be successfully returned.");
        assertFalse(suv.getIsLoaned(), "The SUV should no longer be loaned.");
    }

    @Test
    void testReturnVehicleNotLoaned() {
        // Try to return a vehicle that is not loaned
        boolean result = Loan_Vehicle.returnVehicle(dealer, "V124");
        assertFalse(result, "A non-loaned vehicle should not be returned.");
    }

    @Test
    void testReturnNonExistentVehicle() {
        // Try to return a non-existent vehicle
        boolean result = Loan_Vehicle.returnVehicle(dealer, "V999");
        assertFalse(result, "Non-existent vehicle should not be returned.");
    }

    @Test
    void testGetLoanedVehicles() {
        // Loan some vehicles and then check the list of loaned vehicles
        Loan_Vehicle.loanVehicle(dealer, "V123");
        Loan_Vehicle.loanVehicle(dealer, "V124");

        List<Vehicle> loanedVehicles = Loan_Vehicle.getLoanedVehicles(dealer);
        assertEquals(2, loanedVehicles.size(), "There should be two loaned vehicles.");
        assertTrue(loanedVehicles.contains(suv), "SUV should be in the loaned vehicles list.");
        assertTrue(loanedVehicles.contains(sedan), "Sedan should be in the loaned vehicles list.");
    }

    @Test
    void testGetLoanedVehiclesEmpty() {
        // No vehicles are loaned
        List<Vehicle> loanedVehicles = Loan_Vehicle.getLoanedVehicles(dealer);
        assertTrue(loanedVehicles.isEmpty(), "The loaned vehicles list should be empty.");
    }
}
