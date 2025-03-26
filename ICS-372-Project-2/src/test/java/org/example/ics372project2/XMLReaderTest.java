package org.example.ics372project2;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class XMLReaderTest {

    void runXMLTest(String filePath, int expectedDealerCount) throws Exception {
        Set<Dealer> dealers = new HashSet<>();
        XMLReader reader = new XMLReader(filePath);
        reader.parse(dealers);

        assertEquals(expectedDealerCount, dealers.size(), "Unexpected number of dealers");

        if (expectedDealerCount == 1) {
            // Expecting only Dealer 485
            Dealer dealer = dealers.iterator().next();
            assertEquals("485", dealer.getDealerID());
            assertEquals("Wacky Bob’s Automall", dealer.getDealerName());
            assertEquals(4, dealer.getVehicleList().size());
        } else {
            // Full file with 3 dealers
            Dealer dealer485 = findDealer(dealers, "485");
            assertNotNull(dealer485);
            assertEquals(4, dealer485.getVehicleList().size());

            Dealer dealer786 = findDealer(dealers, "786");
            assertNotNull(dealer786);
            assertEquals(3, dealer786.getVehicleList().size());

            Dealer dealer999 = findDealer(dealers, "999");
            assertNotNull(dealer999);
            assertEquals(3, dealer999.getVehicleList().size());

            // Optional: check Ferrari 458 Italia
            Vehicle ferrari = dealer999.getVehicleList().stream()
                    .filter(v -> v.getVehicleID().equals("FS458"))
                    .findFirst().orElse(null);

            assertNotNull(ferrari);
            assertEquals("Ferrari", ferrari.getManufacturer());
            assertEquals("sports car", ferrari.getType());
        }
    }

    private Dealer findDealer(Set<Dealer> dealers, String id) {
        return dealers.stream()
                .filter(d -> d.getDealerID().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Test
    void testSingleDealerXML() throws Exception {
        File file = new File("vehicle.xml"); // Adjust path as needed
        runXMLTest(file.getAbsolutePath(), 1);
    }

    @Test
    void testFullDealerXML() throws Exception {
        File file = new File("vehicle2.xml"); // Adjust path as needed
        runXMLTest(file.getAbsolutePath(), 3);
    }
}
