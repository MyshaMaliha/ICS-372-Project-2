package org.example.ics372project2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GUIController {
    @FXML
    private Set<Dealer> dealerSet = new HashSet<>();
    private final String FILE_NAME = "Dealers_Vehicles.json";

    @FXML
    private void loadDealershipData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            showAlert("No existing file found. Creating a new one.");
            try {
                file.createNewFile();
            } catch (IOException e) {
                showAlert("Error creating file: " + e.getMessage());
            }
        } else if (file.length() > 0) {
            showAlert("Loading dealership data...");
            try {
                File_Reader.readFile(FILE_NAME, dealerSet);
            } catch (IOException e) {
                showAlert("Error loading the file: " + e.getMessage());
            }
        } else {
            showAlert("Existing file is empty.");
        }
    }

    @FXML
    private void checkDealers() {
        StringBuilder result = new StringBuilder();
        for (Dealer d : dealerSet) {
            result.append("\n--- Dealer ").append(d.getDealerID()).append(" ---\n");
            for (Vehicle v : d.getVehicleList()) {
                result.append("Vehicle ID: ").append(v.getVehicleID()).append("\nModel: ").append(v.getModel()).append("\n");
            }
        }
        showAlert(result.toString().isEmpty() ? "No dealers found." : result.toString());
    }

    @FXML
    private void enableDealer() {
        String dealerID = getUserInput("Enter Dealer ID to Enable:");
        if (dealerID == null) return;

        boolean found = false;
        for (Dealer d : dealerSet) {
            if (d.getDealerID().equals(dealerID)) {
                d.enableAcquisition();
                showAlert("Dealer " + dealerID + " is now enabled.");
                found = true;
                break;
            }
        }
        if (!found) showAlert("Dealer ID not found.");
    }

    @FXML
    private void disableDealer() {
        String dealerID = getUserInput("Enter Dealer ID to Disable:");
        if (dealerID == null) return;

        boolean found = false;
        for (Dealer d : dealerSet) {
            if (d.getDealerID().equals(dealerID)) {
                d.disableAcquisition();
                showAlert("Dealer " + dealerID + " is now disabled.");
                found = true;
                break;
            }
        }
        if (!found) showAlert("Dealer ID not found.");
    }

    @FXML
    private void addVehicle() {
        String input = getUserInput("Add vehicle via File or Manual (enter 'manual' or 'file'):").toLowerCase();

        if (input.equals("file")) {
            String filePath = getUserInput("Enter desired file path:");
            if (filePath != null && !filePath.trim().isEmpty()) {
                try {
                    File_Reader.readFile(filePath, dealerSet);
                } catch (IOException e) {
                    showAlert("Error loading file: " + e.getMessage());
                }
                showAlert("Vehicles loaded from file.");
            } else {
                showAlert("Invalid file path.");
            }
            showAlert("Successfully added the file.");
        } else if (input.equals("manual")) {  // Manual input case
            String dealerID = getUserInput("Enter Dealer ID to add a vehicle:");
            if (dealerID == null) return;

            Dealer selectedDealer = null;
            for (Dealer d : dealerSet) {
                if (d.getDealerID().equals(dealerID)) {
                    selectedDealer = d;
                    break;
                }
            }
            if (selectedDealer == null) {
                showAlert("Dealer ID not found.");
                return;
            }
            if (!selectedDealer.getIsAcquisitionEnabled()) {
                showAlert("Dealer Disabled");
            } else {
                String id = getUserInput("Enter Vehicle ID:");
                String manufacturer = getUserInput("Enter manufacturer:");
                String model = getUserInput("Enter Model:");
                long acquisitionDate = Long.parseLong(getUserInput("Enter Acquisition Date(as long value):"));
                double price = Double.parseDouble(getUserInput("Enter price:"));
                String type = getUserInput("Enter Vehicle Type(SUV, sedan, Pickup, Sports Car):");

                Vehicle newVehicle = JSONReader.checkType(type, manufacturer, model, id, acquisitionDate, price);
                boolean added = selectedDealer.addVehicle(newVehicle);
                if (added) {
                    showAlert("Vehicle added successfully to dealer" + dealerID);
                }
            }
        } else {
            showAlert("Invalid option. Please enter 'manual' or 'file'.");
        }
    }


    @FXML
    private void updateDealerName() {
        String dealerID = getUserInput("Enter Dealer ID to update name:");
        if (dealerID == null) return;

        Dealer dealer = dealerSet.stream().filter(d -> d.getDealerID().equals(dealerID)).findFirst().orElse(null);
        if (dealer == null) {
            showAlert("Dealer ID not found.");
            return;
        }

        String newName = getUserInput("Enter new Dealer Name:");
        dealer.setDealerName(newName);
        showAlert("Dealer name updated successfully.");
    }

    @FXML
    private void exportJSON() {
        try {
            File_Writer.exportJSON(dealerSet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        showAlert("Exported JSON file successfully.");
    }

    // Helper methods
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Car Dealership System");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String getUserInput(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input Required");
        dialog.setHeaderText(prompt);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
}