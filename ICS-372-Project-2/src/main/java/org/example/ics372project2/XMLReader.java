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
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // Creates a new instance of DocumentBuilderFactory, which is needed to create a DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder(); // Uses the factory to create a DocumentBuilder object, which is used to parse the XML file.
            Document doc = builder.parse(xmlFile);  //Parses the XML file and loads it into a Document object.
            doc.getDocumentElement().normalize();   //Normalizes the document structure, ensuring consistency in how elements and text nodes are processed

            // Get all Dealer nodes
            NodeList dealerNodes = doc.getElementsByTagName("Dealer");

            for (int i = 0; i < dealerNodes.getLength(); i++) {  //Iterates through all dealer nodes.
                Node dealerNode = dealerNodes.item(i);
                if (dealerNode.getNodeType() == Node.ELEMENT_NODE) {  //Ensures that the node is an element (not text or comment).
                    Element dealerElement = (Element) dealerNode;
                    String dealerID = dealerElement.getAttribute("id");

                    Dealer dealer = getOrCreateDealer(dealerID, dealerSet); //Get or create the Dealer object

                    // Extract dealer name if it exists
                    String dealerName;
                    NodeList nameNodes = dealerElement.getElementsByTagName("Name");
                    if (nameNodes.getLength() > 0) {
                        dealerName = nameNodes.item(0).getTextContent();
                        dealer.setDealerName(dealerName);
                    }


                    // Get all Vehicle nodes inside this dealer
                    NodeList vehicleNodes = dealerElement.getElementsByTagName("Vehicle");

                    for (int j = 0; j < vehicleNodes.getLength(); j++) {
                        Node vehicleNode = vehicleNodes.item(j);
                        if (vehicleNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element vehicleElement = (Element) vehicleNode;

                            // Extract attributes and elements
                            String type = vehicleElement.getAttribute("type");
                            String vehicleID = vehicleElement.getAttribute("id");
                            String manufacturer = vehicleElement.getElementsByTagName("Make").item(0).getTextContent();
                            String model = vehicleElement.getElementsByTagName("Model").item(0).getTextContent();
                            double price = Double.parseDouble(vehicleElement.getElementsByTagName("Price").item(0).getTextContent());

                            // No acquisition date in XML, setting to 0
                            Vehicle vehicle = checkType(type, manufacturer, model, vehicleID, 0, price);
                            if (vehicle != null) {
                                dealer.addVehicle(vehicle);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing XML file: " + e.getMessage());
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

    private static Vehicle checkType(String type, String manufacturer, String model, String id, long acquisitionDate, double price) {
        return switch (type.toLowerCase()) {
            case "suv" -> new SUV(id, manufacturer, model, acquisitionDate, price);
            case "sedan" -> new Sedan(id, manufacturer, model, acquisitionDate, price);
            case "pickup" -> new Pickup(id, manufacturer, model, acquisitionDate, price);
            case "sports car" -> new SportsCar(id, manufacturer, model, acquisitionDate, price);
            default -> {
                System.out.println("Unknown vehicle type: " + type);
                yield null;
            }
        };
    }
}









