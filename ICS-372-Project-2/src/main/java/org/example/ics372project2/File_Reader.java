package org.example.ics372project2;

import java.io.IOException;
import java.util.Set;

public abstract class File_Reader {

    // static method to read files
    public static void readFile(String filePath, Set<Dealer> dealerSet) throws IOException {
        if (filePath.endsWith(".json")) {
            new JSONReader(filePath).parse(dealerSet);  // Calls the JSON parser if file is JSON
        } else if (filePath.endsWith(".xml")) {
            new XMLReader(filePath).parse(dealerSet);  // Calls the XML parser if file is XML
        } else {
            System.out.println("Unsupported file type.");
        }
    }

    public abstract void parse(Set<Dealer> dealerSet) throws IOException;
}



