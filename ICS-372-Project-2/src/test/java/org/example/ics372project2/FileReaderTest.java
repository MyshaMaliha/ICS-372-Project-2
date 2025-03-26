package org.example.ics372project2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FileReaderTest {

    @Test
    void testReadFileWithJson() throws IOException {
        Set<Dealer> dealerSet = new HashSet<>();

        // Create a subclass of File_Reader for testing
        File_Reader fileReader = new File_Reader() {
            @Override
            public void parse(Set<Dealer> dealerSet) throws IOException {
                // Simulate JSON file parsing by adding a dealer to the set
                dealerSet.add(new Dealer("D001"));
            }
        };

        // Test with a .json file
        String filePath = "vehicle.json";
        fileReader.readFile(filePath, dealerSet);

        assertEquals(2, dealerSet.size(), "Dealer should be added to the set for .json file");
    }

    @Test
    void testReadFileWithXml() throws IOException {
        Set<Dealer> dealerSet = new HashSet<>();

        // Create a subclass of File_Reader for testing
        File_Reader fileReader = new File_Reader() {
            @Override
            public void parse(Set<Dealer> dealerSet) throws IOException {
                // Simulate XML file parsing by adding a dealer to the set
                dealerSet.add(new Dealer("D002"));
            }
        };

        // Test with a .xml file
        String filePath = "vehicle.xml";
        fileReader.readFile(filePath, dealerSet);

        assertEquals(1, dealerSet.size(), "Dealer should be added to the set for .xml file");
    }



    @Test
    void testReadFileWithUnsupportedType() throws IOException {
        Set<Dealer> dealerSet = new HashSet<>();

        // Create a subclass of File_Reader for testing
        File_Reader fileReader = new File_Reader() {
            @Override
            public void parse(Set<Dealer> dealerSet) throws IOException {
                // No parsing happens here, we are only testing unsupported file types
            }
        };

        // Test with an unsupported file type
        String filePath = "vehicle";
        fileReader.readFile(filePath, dealerSet);

        assertTrue(dealerSet.isEmpty(), "Dealer set should remain empty for unsupported file type.");
    }
}
