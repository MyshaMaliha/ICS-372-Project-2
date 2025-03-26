package org.example.ics372project2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FileWriterTest {

    private static final String OUTPUT_FILE = "Dealers_Vehicles.json";
    private Set<Dealer> dealerSet;

    @BeforeEach
    void setUp() {
        dealerSet = new HashSet<>();

        Dealer dealer = new Dealer("12345");
        dealer.setDealerName("Test Dealer");
        dealer.setAcquisitionEnabled(true);

        Vehicle vehicle1 = new SUV("V001", "Toyota", "RAV4", 1682601600000L, 30000.0, false);
        Vehicle vehicle2 = new Sedan("V002", "Honda", "Accord", 1682601600000L, 28000.0, true);

        dealer.addVehicle(vehicle1);
        dealer.addVehicle(vehicle2);

        dealerSet.add(dealer);
    }

    @Test
    void testExportJSON_CreatesExpectedFileAndContent() throws Exception {
        // Run the export
        File_Writer.exportJSON(dealerSet);

        File outputFile = new File(OUTPUT_FILE);
        assertTrue(outputFile.exists(), "Output JSON file should exist");

        // Parse and verify content
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(outputFile);

        assertTrue(root.has("car_inventory"), "Root should contain 'car_inventory'");

        JsonNode inventory = root.get("car_inventory");
        assertEquals(2, inventory.size(), "Should contain 2 vehicles");

        // Check contents of first vehicle
        JsonNode vehicle = inventory.get(0);
        assertEquals("12345", vehicle.get("dealership_id").asText());
        assertEquals("Test Dealer", vehicle.get("dealer_name").asText());
        assertTrue(vehicle.has("vehicle_type"));
        assertTrue(vehicle.has("price"));
    }

    @AfterEach
    void tearDown() {
        // Cleanup the test output file
        File file = new File(OUTPUT_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
}
