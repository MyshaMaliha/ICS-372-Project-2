package org.example.ics372project2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JSONReaderTest {

    private String testFilePath;

    @BeforeEach
    void setUp() {
        File file = new File("Vehicle.json");
        testFilePath = file.getAbsolutePath();
    }

    @Test
    void parse() throws Exception {
        Set<Dealer> dealers = new HashSet<>();
        JSONReader reader = new JSONReader(testFilePath);
        reader.parse(dealers);

        assertEquals(2, dealers.size(), "Expected two dealers");

        Dealer dealer1 = dealers.stream().filter(d -> d.getDealerID().equals("12513")).findFirst().orElse(null);
        Dealer dealer2 = dealers.stream().filter(d -> d.getDealerID().equals("77338")).findFirst().orElse(null);

        assertNotNull(dealer1);
        assertEquals(3, dealer1.getVehicleList().size());

        assertNotNull(dealer2);
        assertEquals(1, dealer2.getVehicleList().size());

        // Extra: confirm the sports car
        Vehicle supra = dealer2.getVehicleList().get(0);
        assertEquals("sports car", supra.getType());
        assertEquals("Supra", supra.getModel());
        assertEquals("Toyota", supra.getManufacturer());
        assertEquals("229393", supra.getVehicleID());
    }
}
