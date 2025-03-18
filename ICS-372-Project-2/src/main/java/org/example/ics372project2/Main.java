package org.example.ics372project2;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Set<Dealer> dealerSet = new HashSet<>();
        Scanner s = new Scanner(System.in);

        File appFile = new File("Dealers_Vehicles.json");

        //Check if the file exists; if not create one
        if(!appFile.exists()){
            System.out.println("No existing file. Creating a new File.");
            appFile.createNewFile();
        }

        //Attempting to load the file (Even if its empty)
        if (appFile.length() > 0) {
            System.out.println("Loading existing dealership data...");
            try {
                File_Reader.readFile("Dealers_Vehicles.json", dealerSet);
            } catch (IOException e) {
                System.out.println("Error loading the file: " + e.getMessage());
            }
        } else{
            System.out.println("Existing File is empty");
        }


        String filePath;
        int num =0;
        while (true) {
            System.out.println("1: Check Dealers and their Vehicles: ");
            System.out.println("2: Enable dealer");
            System.out.println("3: Disable dealer");
            System.out.println("4: Add a new vehicle in desired dealer: ");
            System.out.println("5: ADD or Update dealer name:");
            System.out.println("6: Get the updated JSON file.");
            System.out.println("7: Exit and Export JSON file: ");
            System.out.println("Please Choose your desired option : ");
            num = s.nextInt();    //Read user input


            switch (num) {
                case 1: // printing  each dealer and  it's vehicle record

                    for (Dealer d : dealerSet) {
                        System.out.println("-----------------");
                        System.out.println("Dealer " + d.getDealerID());
                        System.out.println("-----------------");
                        for (Vehicle v : d.getVehicleList()) {
                            System.out.println("Vehicle ID: " + v.getVehicleID());
                            System.out.println("Model:" + v.getModel());
                        }
                    }
                    System.out.println(" ");
                    break;
                case 2:   //Enable a dealer
                    System.out.println("Which dealer you want to enable: ");
                    String dealerID = s.next();
                    boolean isenable = false;
                    for (Dealer d : dealerSet) {    // Loop through existing dealers
                        if (d.getDealerID().equals(dealerID)) {
                            d.enableAcquisition();
                            System.out.println("Dealer " + dealerID + " is now enable.");
                            isenable = true;
                            break;
                        }
                    }
                    if (!isenable) {
                        System.out.println("This Dealer ID does not exist.");
                    }
                    System.out.println(" ");
                    break;

                case 3:  //Disable a dealer
                    System.out.println("Which dealer you want to disable: ");
                    String dealerID2 = s.next();
                    boolean isDisable = false;
                    for (Dealer d : dealerSet) {
                        if (d.getDealerID().equals(dealerID2)) {
                            d.disableAcquisition();
                            System.out.println("Dealer " + dealerID2 + " is now disable.");
                            isDisable = true;
                            break;
                        }
                    }

                    if (!isDisable) {
                        System.out.println("This Dealer Id does not exist.");
                    }
                    System.out.println(" ");
                    break;
                case 4:   //Add a new vehicle to a dealer
                    System.out.println("Add vehicle via File or Manual (enter 'manual' or ' File' ) : ");
                    String inputMethod = s.next();

                    if (inputMethod.equalsIgnoreCase("File")) {
                        System.out.println("Enter desired  file path: ");
                        filePath = s.next();
                        File_Reader.readFile(filePath, dealerSet);

                    } else if (inputMethod.equalsIgnoreCase("manual")) {
                        // Manual input case
                        System.out.println("Enter Dealer ID to add the vehicle to: ");
                        dealerID = s.next();
                        Dealer selectedDealer = null;

                        for (Dealer d : dealerSet) {
                            if (d.getDealerID().equals(dealerID)) {
                                selectedDealer = d;
                                break;
                            }
                        }

                        if (selectedDealer == null) {
                            System.out.println("Dealer ID not found.");
                            break;
                        }

                        if (!selectedDealer.getIsAcquisitionEnabled()) {
                            System.out.println("Dealer Disabled");
                        } else {
                            System.out.println("Enter Vehicle ID: ");
                            String id = s.next();

                            System.out.println("Enter Manufacturer: ");
                            String manufacturer = s.next();

                            s.nextLine();

                            System.out.println("Enter Model: ");
                            String model = s.nextLine();

                            System.out.println("Enter Acquisition Date (as long value): ");
                            long acquisitionDate = s.nextLong();

                            System.out.println("Enter Price: ");
                            double price = s.nextDouble();

                            s.nextLine();

                            System.out.println("Enter Vehicle Type (SUV, Sedan, Pickup, Sports Car): ");
                            String type = s.nextLine();


                            Vehicle newVehicle = JSONReader.checkType(type, manufacturer, model, id, acquisitionDate, price);
                            boolean added = selectedDealer.addVehicle(newVehicle);
                            if (added) {
                                System.out.println("Vehicle added successfully to Dealer " + dealerID);
                            }
                        }


                    } else {
                        System.out.println("Invalid option. Please enter 'manual' or 'json'.");
                    }

                    System.out.println(" ");
                    break;


                case 5:  //add or update dealer name*
                    System.out.println("Enter dealer ID :");
                    s.nextLine();  // Consume the leftover newline after previous nextInt()*
                    String ID =s.nextLine();

                    boolean dealerFound = false; //Track if at least 1 was updated

                    for (Dealer dealer : dealerSet) {
                        // Debugging: Print the current dealer's ID
                        System.out.println("Checking dealer with ID: " + dealer.getDealerID());
                        if (dealer.getDealerID().equals(ID)) {
                            System.out.println("Enter desired name for the dealer : ");
                            String dealerName = s.nextLine().trim();
                            dealer.setDealerName(dealerName);
                            dealerFound = true;

                        }
                    }

                    if (dealerFound) {
                        System.out.println("Dealer name updated successfully.");
                    } else {
                        System.out.println("Not a valid dealer ID. Please try again. ");
                    }
                    break;



                case 6:  //Get the updated JSON File
                    File_Writer.exportJSON(dealerSet);
                    System.out.println("---Check you new JSON file---");
                    System.out.println(" ");
                    break;
                case 7:   //Exit
                    System.out.println("Exiting the system.");
                    File_Writer.exportJSON(dealerSet);
                    System.exit(0);
                    break;

                default:
                    System.out.println("Not a valid option");
            }
        }
    }
}





