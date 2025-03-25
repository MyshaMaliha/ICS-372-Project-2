package org.example.ics372project2;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class XMLReader extends File_Reader {
    private String filePath;

    public XMLReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void parse(Set<Dealer> dealerSet) throws IOException {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList dealerNodes = doc.getElementsByTagName("Dealer");

            for (int i = 0; i < dealerNodes.getLength(); i++) {
                Node dealerNode = dealerNodes.item(i);
                if (dealerNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element dealerElement = (Element) dealerNode;

                    // Ensure dealer has a valid ID
                    String dealerID = dealerElement.getAttribute("id").trim();
                    if (dealerID.isEmpty()) {
                        throw new IOException("Error: Dealer is missing 'id' attribute. Stopping file processing.");
                    }

                    Dealer dealer = getOrCreateDealer(dealerID, dealerSet);

                    // Extract dealer name if it exists
                    NodeList nameNodes = dealerElement.getElementsByTagName("Name");
                    if (nameNodes.getLength() > 0) {
                        dealer.setDealerName(nameNodes.item(0).getTextContent().trim());
                    }

                    NodeList vehicleNodes = dealerElement.getElementsByTagName("Vehicle");
                    for (int j = 0; j < vehicleNodes.getLength(); j++) {
                        Node vehicleNode = vehicleNodes.item(j);
                        if (vehicleNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element vehicleElement = (Element) vehicleNode;

                            // Ensure vehicle has a type and ID
                            String type = vehicleElement.getAttribute("type").trim();
                            String vehicleID = vehicleElement.getAttribute("id").trim();
                            if (type.isEmpty() || vehicleID.isEmpty()) {
                                throw new IOException("Error: Vehicle missing 'type' or 'id' in Dealer " + dealerID + ". Stopping file processing.");
                            }

                            // Extract optional fields with defaults
                            String manufacturer = getElementText(vehicleElement, "Make", "Unknown");
                            String model = getElementText(vehicleElement, "Model", "Unknown");
                            double price = getElementDouble(vehicleElement, "Price", 0.0);
                            boolean vehicleIsLoaned = getElementBoolean(vehicleElement, "is_loaned", false);

                            // Create vehicle and add it to the dealer
                            Vehicle vehicle = checkType(type, manufacturer, model, vehicleID, 0, price, vehicleIsLoaned);
                            if (vehicle != null) {
                                dealer.addVehicle(vehicle);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage()); // Print the error message before stopping
            throw e; // Rethrow the exception to completely stop the program
        } catch (Exception e) {
            throw new IOException("Error parsing XML file: " + e.getMessage());
        }
    }
    private Dealer getOrCreateDealer(String dealerID, Set<Dealer> dealerSet) {
        for (Dealer d : dealerSet) {
            if (d.getDealerID().equals(dealerID)) {
                return d;
            }
        }
        Dealer newDealer = new Dealer(dealerID);
        dealerSet.add(newDealer);
        return newDealer;
    }

    private static Vehicle checkType(String type, String manufacturer, String model, String id, long acquisitionDate, double price, boolean vehicleIsLoaned) {
        return switch (type.toLowerCase()) {
            case "suv" -> new SUV(id, manufacturer, model, acquisitionDate, price, vehicleIsLoaned);
            case "sedan" -> new Sedan(id, manufacturer, model, acquisitionDate, price, vehicleIsLoaned);
            case "pickup" -> new Pickup(id, manufacturer, model, acquisitionDate, price, vehicleIsLoaned);
            case "sports car" -> new SportsCar(id, manufacturer, model, acquisitionDate, price, vehicleIsLoaned);
            default -> {
                System.out.println("Unknown vehicle type: " + type + " (Dealer ID: " + id + ")");
                yield null;
            }
        };
    }

    // Helper method to safely get text content from an XML element, with a default value
    private String getElementText(Element parent, String tagName, String defaultValue) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        return (nodeList.getLength() > 0) ? nodeList.item(0).getTextContent().trim() : defaultValue;
    }

    // Helper method to safely get a double value from an XML element, with a default value
    private double getElementDouble(Element parent, String tagName, double defaultValue) {
        try {
            NodeList nodeList = parent.getElementsByTagName(tagName);
            return (nodeList.getLength() > 0) ? Double.parseDouble(nodeList.item(0).getTextContent().trim()) : defaultValue;
        } catch (NumberFormatException e) {
            System.out.println("Warning: Invalid number format for " + tagName + ". Using default: " + defaultValue);
            return defaultValue;
        }
    }

    // Helper method to safely get a boolean value from an XML element, with a default value
    private boolean getElementBoolean(Element parent, String tagName, boolean defaultValue) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        return (nodeList.getLength() > 0) ? Boolean.parseBoolean(nodeList.item(0).getTextContent().trim()) : defaultValue;
    }
}









