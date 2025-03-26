package org.example.ics372project2;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GUIController {
    @FXML
    private Set<Dealer> dealerSet = new HashSet<>();
    private final String FILE_NAME = "Dealers_Vehicles.json";

    @FXML
    public void initialize() {  //JAVAFX will call initialize() method  before displaying the GUI
        File file = new File(FILE_NAME);
        if (file.exists()) {
            System.out.println("Loading dealership data...");
            try {
                File_Reader.readFile(FILE_NAME, dealerSet);
            } catch (IOException e) {
                showAlert("Error loading the file: " + e.getMessage());
            }
        } else {
            System.out.println("existing file is empty not found");
        }
    }

    @FXML
    private void checkDealers() {   //printing each dealer and it's vehicle record
        StringBuilder result = new StringBuilder();
        for (Dealer d : dealerSet) {
            result.append("---Dealer : ").append(d.getDealerID()).append(" ---\n");
            result.append("---Dealer Name : ").append(d.getDealerName()).append(" ---\n");
            result.append("---AcquisitionEnabled:  ").append(d.getIsAcquisitionEnabled()).append("---\n");

            for (Vehicle v : d.getVehicleList()) {
                result.append("Vehicle ID : ").append(v.getVehicleID()).append("\nModel: ").append((v.getModel())).append("\n");
                result.append("Loaned: ").append(v.getIsLoaned()).append("\n");
            }
            result.append("\n");
        }
        showAlert2(result.toString().isEmpty() ? "No delaers found." : result.toString());

    }

    @FXML
    private void enableDealer() throws IOException {
        String dealerID = getUserInput("Enter Dealer ID to Enable:");
        if (dealerID == null) return;

        boolean found = false;
        for (Dealer d : dealerSet) {
            if (d.getDealerID().equals(dealerID)) {
                d.enableAcquisition();
                showAlert("Dealer " + dealerID + " is now enabled.");
                found = true;
                File_Writer.exportJSON(dealerSet);
                break;
            }
        }
        if (!found) showAlert("Dealer ID not found.");

    }

    @FXML
    private void disableDealer() throws IOException {
        String dealerID = getUserInput("Enter Dealer ID to Disable:");
        if (dealerID == null) return;

        boolean found = false;
        for (Dealer d : dealerSet) {
            if (d.getDealerID().equals(dealerID)) {
                d.disableAcquisition();
                showAlert("Dealer " + dealerID + " is now disabled.");
                found = true;
                File_Writer.exportJSON(dealerSet);
                break;
            }
        }
        if (!found) showAlert("Dealer ID not found.");

    }

    @FXML
    private void addVehicle() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ADD Vehicle");
        alert.setHeaderText("Choose how you want to add a vehicle: ");
        alert.setContentText("Select an option below:");

        //Create Buttons
        ButtonType manualButton = new ButtonType("Manual");
        ButtonType fileButton = new ButtonType("File");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(manualButton, fileButton, cancelButton);


        //Apply Styles to Dialog Pane
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");


        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == fileButton) {
                handleFileInput();
            } else if (result.get() == manualButton) {
                handleManualInput();
            }
        }
    }


    private void handleFileInput() {
        String filePath = getUserInput("Enter desired file path:");
        if (filePath != null && !filePath.trim().isEmpty()) {
            try {
                File_Reader.readFile(filePath, dealerSet);
                showAlert("Vehicles loaded from file.");
                File_Writer.exportJSON(dealerSet);
            } catch (IOException e) {
                showAlert("Error loading file: " + e.getMessage());
            }

        } else {
            showAlert("Invalid file path.");
        }
    }

    private void handleManualInput()  {
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
            boolean isLoaned = Boolean.parseBoolean(getUserInput("Loaned(true/false: "));

            Vehicle newVehicle = JSONReader.checkType(type, manufacturer, model, id, acquisitionDate, price, isLoaned);
            boolean added = selectedDealer.addVehicle(newVehicle);
            if (added) {
                showAlert("Vehicle added successfully to dealer" + dealerID);
            }

        }
    }

    @FXML
    private void updateDealerName() throws IOException {
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
        File_Writer.exportJSON(dealerSet);

    }

    @FXML
    private void exportJSON() throws IOException {
        try {
            File_Writer.exportJSON(dealerSet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File_Writer.exportJSON(dealerSet);
        showAlert("Exported JSON file successfully.");
    }

    @FXML

    private void transferInventory() throws IOException{
        InventoryTransfer iT = new InventoryTransfer();

        if(dealerSet.isEmpty()){
            showAlert("No dealers available for transfer.");
            return;
        }

        boolean continueToTransfer = true;

        while(continueToTransfer){
            // asks the user input for dealer IDs and Vehicle ID
            String dealerIdFrom = getUserInput("Enter dealer ID transferring from");
            if (dealerIdFrom == null) return;
            String dealerIdTo = getUserInput("Enter dealer ID transferring to");
            if (dealerIdTo == null) return;

            String vehicleId = getUserInput("Enter Vehicle ID to transfer:");
            if (vehicleId == null) return;

            // call the transferVehicle method to transfer.
            boolean success = iT.transferVehicle(dealerSet, dealerIdFrom, dealerIdTo, vehicleId);

            // if it did not transfer, print error message
            if (!success) {
                showAlert("Transfer failed. Please check your inputs and try again.");
            }

            // asks the user if they want to transfer another vehicle
            String response = getUserInput("Do you want to transfer another vehicle? (yes/no)");
            if (response == null || !response.equalsIgnoreCase("yes")) {
                continueToTransfer = false;
            }


        }


    }

    @FXML
    private void loanVehicle() throws IOException {
        String dealerID = getUserInput("Enter Dealer ID:");
        if (dealerID == null || dealerID.trim().isEmpty()) {
            showAlert("Dealer ID cannot be empty.");
            return;
        }

        Dealer dealer = dealerSet.stream()
                .filter(d -> d.getDealerID().equals(dealerID))
                .findFirst()
                .orElse(null);

        if (dealer == null) {
            showAlert("Dealer ID not found.");
            return;
        }

        String vehicleID = getUserInput("Enter Vehicle ID:");
        if (vehicleID == null || vehicleID.trim().isEmpty()) {
            showAlert("Vehicle ID cannot be empty.");
            return;
        }

        if (Loan_Vehicle.loanVehicle(dealer, vehicleID)) {
            showAlert("Vehicle " + vehicleID + " has been loaned.");
        } else {
            showAlert("Vehicle ID not found or cannot be loaned (sports cars are not allowed).");
        }
        File_Writer.exportJSON(dealerSet);

    }

    @FXML
    private void returnVehicle() throws IOException {
        String dealerID = getUserInput("Enter Dealer ID:");
        if (dealerID == null || dealerID.trim().isEmpty()) {
            showAlert("Dealer ID cannot be empty.");
            return;
        }

        Dealer dealer = dealerSet.stream()
                .filter(d -> d.getDealerID().equals(dealerID))
                .findFirst()
                .orElse(null);

        if (dealer == null) {
            showAlert("Dealer ID not found.");
            return;
        }

        String vehicleID = getUserInput("Enter Vehicle ID:");
        if (vehicleID == null || vehicleID.trim().isEmpty()) {
            showAlert("Vehicle ID cannot be empty.");
            return;
        }

        if (Loan_Vehicle.returnVehicle(dealer,vehicleID)) {
            showAlert("Vehicle " + vehicleID + " has been returned.");
        } else {
            showAlert("Vehicle ID not found or was not loaned out.");
        }
        File_Writer.exportJSON(dealerSet);

    }
    @FXML
    private void showLoanedVehicles() throws IOException {
        StringBuilder result = new StringBuilder("Loaned Vehicles:\n");

        for (Dealer d : dealerSet) {
            List<Vehicle> loanedVehicles = Loan_Vehicle.getLoanedVehicles(d);
            if (!loanedVehicles.isEmpty()) {
                result.append("\nDealer: ").append(d.getDealerName()).append("\n");
                for (Vehicle v : loanedVehicles) {
                    result.append("Vehicle: ").append(v.getVehicleID()).append("\n");
                }
            }
        }

        showAlert(result.toString().isEmpty() ? "No vehicles are currently loaned." : result.toString());
        File_Writer.exportJSON(dealerSet);

    }



    // Helper methods
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Car Dealership System");
        alert.setContentText(message);

        //Apply Styles
        DialogPane dialogPane =alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        alert.showAndWait();
    }

    private String getUserInput(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input Required");
        dialog.setHeaderText(prompt);

        //Apply styles
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
        //making a  ScrollPane to make the alert's content scrollable
        private void showAlert2(String message) {
            //Create a TextArea with the content
            TextArea textArea = new TextArea(message);
            textArea.setWrapText(true);   //Wrap the text so it does not go out of the TextArea
            textArea.setEditable(false);  //Disable editing of the TextArea

            //Create a ScrollPane and add the TextArea to it
            ScrollPane scrollPane = new ScrollPane(textArea);
            scrollPane.setFitToWidth(true);  //Ensures the content fits the width of the dialog

            //Create a custom alert with the scrollable content
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Dealer and Vehicle Information");

            //Set the dialog's content to the ScrollPane
            dialog.getDialogPane().setContent(scrollPane);

            //Add an ok Button ti close the dialog
            ButtonType okButton = new ButtonType("OK");
            dialog.getDialogPane().getButtonTypes().add(okButton);

            //Apply Styles
            DialogPane dialogPane =dialog.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            dialogPane.getStyleClass().add("dialog-pane");

            //shoe the dialog and wait for the user to close it
            dialog.showAndWait();

        }

}