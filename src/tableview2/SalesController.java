package tableview2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

// Import statements for necessary libraries and classes
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import importData2.Vehicle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import viewinfo2.AppContext;

// Definition of the controller class for the Sales FXML file
public class SalesController {

    // FXML annotations for JavaFX Components

    // Definition of the TableColumn for the car plate in the TableView
    @FXML
    private TableColumn<SalesCSV, String> carPlateColumn;

    // Definition of the TextField for the car plate
    @FXML
    private TextField carPlateTextField;

    // Definition of the TableColumn for the customer ID in the TableView
    @FXML
    private TableColumn<SalesCSV, String> customerIDColumn;

    // Definition of the TableColumn for the date in the TableView (type updated to LocalDateTime)
    @FXML
    private TableColumn<SalesCSV, LocalDateTime> dateColumn;

    // Definition of the TextField for the date
    
    @FXML
    private DatePicker dateTextField;
    // Definition of the TableColumn for the employee ID in the TableView
    @FXML
    private TableColumn<SalesCSV, String> employeeIDColumn;

    // Definition of the TextField for the customer ID
    @FXML
    private TextField customerIDTextField;

    // Definition of the TableColumn for the sales ID in the TableView
    @FXML
    private TableColumn<SalesCSV, String> salesIDColumn;

    // Definition of the TableView for displaying sales data
    @FXML
    private TableView<SalesCSV> tableView;

     private final ObservableList<SalesCSV> salesDataList = FXCollections.observableArrayList();

    private final ObservableList<VehicleCSV> vehicleDataList = FXCollections.observableArrayList();

    private void loadVehicleCSVData(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] headers = reader.readNext(); // Read headers, if any

            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                // Assuming VehicleCSV class has a constructor that takes relevant parameters
                VehicleCSV vehicle = new VehicleCSV(row[0], row[1], row[2], row[3], row[4]);
                vehicleDataList.add(vehicle);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
private Map<String, List<VehicleCSV>> connectSalesAndVehicles(List<SalesCSV> salesData, List<VehicleCSV> vehicleData) {
    Map<String, List<VehicleCSV>> carPlateToVehicleMap = new HashMap<>();
    String loggedInEmployeeId = AppContext.getLoggedInUserId();

    for (SalesCSV sale : salesData) {
        if (sale.getEmployeeID().equals(loggedInEmployeeId)) {
            // Find all vehicles with the matching car plate and status "0" (sold)
            List<VehicleCSV> matchedVehicles = vehicleData.stream()
                    .filter(vehicle -> vehicle.getCarPlate().equals(sale.getCarPlate()) && vehicle.getCarStatus().equals("0"))
                    .collect(Collectors.toList());

            if (!matchedVehicles.isEmpty()) {
                carPlateToVehicleMap.put(sale.getCarPlate(), matchedVehicles);
            }
        }
    }

    return carPlateToVehicleMap;
}
    @FXML
    public void initialize() {
        
        
        
        carPlateColumn.setCellValueFactory(cellData -> cellData.getValue().carPlateProperty());
        customerIDColumn.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty());

        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDate());
        dateColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    String formattedDate = item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    setText(formattedDate);
                }
            }
        });

        employeeIDColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIDProperty());
        salesIDColumn.setCellValueFactory(cellData -> cellData.getValue().salesIDProperty());

        // Load CSV data and set it to the TableView
        loadCSVData("src\\backups\\salesBackup.csv");
        tableView.setItems(salesDataList);

        // Load vehicle data
        loadVehicleCSVData("src\\backups\\vehicleBackup.csv");

        // Connect sales and vehicles
        Map<String, List<VehicleCSV>> carPlateToVehicleMap = connectSalesAndVehicles(salesDataList, vehicleDataList);

        // Print the connected data (You can use this data as needed)
        carPlateToVehicleMap.forEach((carPlate, vehicles) -> {
            System.out.println("Car Plate: " + carPlate);
            System.out.println("Associated Vehicles: " + vehicles);
        });
    }
    // Event handler for the "Home" button
    @FXML
    public void homeButtonPushed(ActionEvent event) throws IOException, CsvException {
        try {
            // Determine the user's access level from the application context
            String accessLevel = AppContext.getAccessLevel();

            // Path to the FXML file for the next screen
            String fxmlPath = null;

            // Determine which FXML page to load based on the user's access level
            if ("management".equals(accessLevel)) {
                fxmlPath = "/ManagementMenuFXML.fxml";
            } else if ("sales".equals(accessLevel)) {
                fxmlPath = "/SalesMenuFXML.fxml";
            } else {
                // Handle other access levels or scenarios as needed
            }

            // Load the appropriate FXML page
            if (fxmlPath != null) {
                Parent menuParent = FXMLLoader.load(getClass().getResource(fxmlPath));
                Scene menuScene = new Scene(menuParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(menuScene);
                window.show();
            }
        } catch (UnsupportedOperationException e) {
            // Handle the UnsupportedOperationException
            System.err.println("UnsupportedOperationException: " + e.getMessage());
            // You can log the exception or show an error message to the user
        }
    }

    // Change screen to TableView.fxml
    @FXML
    public void changeScreenButtonPushed2(ActionEvent event) throws IOException {
        // Load the TableView FXML page
        Parent viewTableViewParent = FXMLLoader.load(getClass().getResource("TableView.fxml"));
        Scene viewTableViewScene = new Scene(viewTableViewParent);

        // Set the new scene on the stage
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(viewTableViewScene);
        window.show();
    }

    // Change screen to Vehicle.fxml
    @FXML
    public void changeScreenButtonPushed1(ActionEvent event) throws IOException {
        // Load the Vehicle FXML page
        Parent viewVehicleParent = FXMLLoader.load(getClass().getResource("Vehicle.fxml"));
        Scene viewVehicleScene = new Scene(viewVehicleParent);

        // Set the new scene on the stage
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(viewVehicleScene);
        window.show();
    }


        
    

    // Event handler for adding new sales data
    @FXML
  
public void newPersonButtonPushed2() {
      // Get the current date and time
    LocalDateTime currentDateTime = LocalDateTime.now().withNano(0);

    // Check if the entered car plate exists in the list of sold vehicles
    List<VehicleCSV> matchingVehicles = vehicleDataList.stream()
            .filter(vehicle -> vehicle.getCarPlate().equals(carPlateTextField.getText()))
            .filter(vehicle -> vehicle.getCarStatus().equals("0")) // Filter only sold vehicles
            .collect(Collectors.toList());

    if (!matchingVehicles.isEmpty()) {
        // Create a new SalesCSV object with the provided data
        SalesCSV newSales = new SalesCSV(
                SalesCSV.getNextSalesID(),
                currentDateTime,
                carPlateTextField.getText(),
                customerIDTextField.getText(),
                SalesCSV.getLoggedInUserID()
        );

        // Add new sales data to the ObservableList
        salesDataList.add(newSales);

        // Clear the text fields after adding the data to the TableView
        clearTextFields();

        // Update the CSV file with the modified data
        updateCSVFile("src\\backups\\salesBackup.csv", newSales);
    } else {
        System.out.println("The specified vehicle is not sold or does not exist.");
    }
}

public void deleteButtonPushed() {
     // Get the selected item from the TableView
    SalesCSV selectedSale = tableView.getSelectionModel().getSelectedItem();

    if (selectedSale != null) {
        // Remove the selected item from the TableView
        tableView.getItems().remove(selectedSale);

        // Update the CSV file without the deleted sale
        updateCSVFileWithoutSale("src\\backups\\salesBackup.csv", selectedSale);
    } else {
        // Handle the case where no item is selected
        System.out.println("No item selected for deletion.");
    }
}

// Method to update the CSV file without the deleted sale
private void updateCSVFileWithoutSale(String filePath, SalesCSV deletedSale) {
    try {
        // Synchronize on salesDataList to avoid concurrent modification issues
        synchronized (salesDataList) {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            List<String> updatedLines = new ArrayList<>();

            // Update the lines by excluding the deleted sale
            for (String line : lines) {
                if (!line.contains(deletedSale.getSalesID())) {
                    updatedLines.add(line);
                }
            }

            // Write the updated lines back to the CSV file
            Files.write(Paths.get(filePath), updatedLines, StandardCharsets.UTF_8);
        }
    } catch (IOException e) {
        // Handle IOException
        e.printStackTrace();
    }
}
    



    // Method to load CSV data into the ObservableList
    private void loadCSVData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read the headers line from the CSV file
            String headersLine = reader.readLine();

            // Process each line in the CSV file
            String line;
           

            while ((line = reader.readLine()) != null) {
                // Split the line into an array of values
                String[] row = line.split(",");
                
                // Check if the row has at least 5 values
                if (row.length >= 5) {
                    try {
                       
                        // Create a SalesCSV object with the parsed data
                        SalesCSV sales = new SalesCSV(row[0], parseDateTime(row[1]), row[2], row[3], row[4]);

                        // Modify the date-time string in the row
                        String dateTimeString = row[1];
                        dateTimeString = dateTimeString.replace('T', ' ');
                        row[1] = dateTimeString;

                        // Add the SalesCSV object to the ObservableList
                        salesDataList.add(sales);
                    } catch (DateTimeParseException e) {
                        // Handle the case where date parsing fails
                        System.err.println("Error parsing date in row: " + line);
                    }
                } else {
                    // Handle the case where the row has less than 5 values
                    System.err.println("Invalid row: " + line);
                }
            }
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
    }

    // Method to parse date-time strings into LocalDateTime
    private static LocalDateTime parseDateTime(String dateTimeString) {
    try {
        // Try parsing with 'T' separator
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    } catch (DateTimeParseException e) {
        try {
            // Try parsing with space separator
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    .atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.systemDefault())
                    .toLocalDateTime();
        } catch (DateTimeParseException ex) {
            System.err.println("Invalid date-time format: " + dateTimeString);
            return null;
        }
    }
}

    // Method to clear text fields
    private void clearTextFields() {
        carPlateTextField.clear();
      
        customerIDTextField.clear();
    }

    // Method to update the CSV file with new sales data
  // Method to update the CSV file with new sales data
private void updateCSVFile(String filePath, SalesCSV newSales) {
    try {
        // Synchronize on salesDataList to avoid concurrent modification issues
        synchronized (salesDataList) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                // Check if the file is empty and write headers if needed
                if (salesDataList.isEmpty()) {
                    String headers = "SalesID,Date,CarPlate,CustomerID,EmployeeID";
                    writer.write(headers);
                    writer.newLine();
                }

                // Format the LocalDateTime object before writing to the CSV file
                String formattedDate = newSales.getDate().get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));

                // Append the new data to the CSV file
                String newLine = String.join(",", newSales.getSalesID(), formattedDate, newSales.getCarPlate(), newSales.getCustomerID(), newSales.getEmployeeID());
                writer.write(newLine);
                writer.newLine();
            }
        }
    } catch (IOException e) {
        // Handle IOException
        e.printStackTrace();
    }
}

}

