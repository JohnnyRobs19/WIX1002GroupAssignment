/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package viewinfo2;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class SALESController implements Initializable {
     @FXML
    private TableView<sales> salesTableView;

    @FXML
    private TableColumn<sales, String> salesId;

    @FXML
    private TableColumn<sales, String> dateAndTime;

    @FXML
    private TableColumn<sales, String> carPlate;

    @FXML
    private TableColumn<sales, String> customerId;

    @FXML
    private TableColumn<sales, String> employeeId;
       @FXML
    private TextField salesIdFilterField;

    @FXML
    private DatePicker dateAndTimePicker;

    @FXML
    private TextField carPlateFilterField;

    @FXML
    private TextField customerIdFilterField;

    @FXML
    private TextField employeeIdFilterField;

    @FXML
    private TextField maxPriceField;

    @FXML
    private TextField minPriceField;

    private final ObservableList<sales> dataList = FXCollections.observableArrayList();
    private String selectedCP;

    public void setCarPlate(String carPlate) {
        this.selectedCP = carPlate;
        // Load data when the employee ID is set
    }

 
    
public void salesBackButtonPushed(ActionEvent event) throws IOException
    {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("MANAGEMENTFXML.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(mainViewScene);
        window.show();
    }
public void userClickedOnTable(MouseEvent event) throws IOException {
    // Get the selected item from the table
    sales selectedCP = salesTableView.getSelectionModel().getSelectedItem();

    // Check if a sale is selected
    if (selectedCP != null) {
        // Get the selected sale's car plate
        String selectedCarPlate = selectedCP.getCarPlate();

        // Pass the selected sale's car plate to VEHICLEVIEWController
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("VEHICLEVIEW.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);

        VEHICLEVIEWController controller = loader.getController();

        if (controller != null) {
            // Use Platform.runLater to ensure UI-related tasks are executed on the JavaFX Application Thread
            Platform.runLater(() -> {
                controller.setCarPlate(selectedCarPlate);

                // This line gets the Stage information
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            });
        }
    }
}


  @FXML
    private void searchButtonPushed(ActionEvent event) {
        String salesIdSearch = salesIdFilterField.getText().trim().toLowerCase();
        String carPlateSearch = carPlateFilterField.getText().toLowerCase();
        String customerIdSearch = customerIdFilterField.getText().toLowerCase();
        String employeeIdSearch = employeeIdFilterField.getText().toLowerCase();

        try {
            double minPrice = minPriceField.getText().isEmpty() ? 0.0 : Double.parseDouble(minPriceField.getText());
            double maxPrice = maxPriceField.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceField.getText());

            ObservableList<sales> filteredData = dataList.filtered(sale ->
                    (salesIdSearch.isEmpty() || sale.getSalesId().toLowerCase().equals(salesIdSearch)) &&
                            (carPlateSearch.isEmpty() || sale.getCarPlate().toLowerCase().contains(carPlateSearch)) &&
                            (customerIdSearch.isEmpty() || sale.getCustomerId().toLowerCase().contains(customerIdSearch)) &&
                            (employeeIdSearch.isEmpty() || sale.getEmployeeId().toLowerCase().contains(employeeIdSearch)) &&
                            Double.parseDouble(sale.getPrice()) >= minPrice &&
                            Double.parseDouble(sale.getPrice()) <= maxPrice &&
                            (dateAndTimePicker.getValue() == null || sale.getDateAndTime().toLocalDate().isEqual(dateAndTimePicker.getValue()))
            );

             salesTableView.setItems(filteredData);

        // Check if the filtered data is empty
        if (filteredData.isEmpty()) {
            showNoSalesFoundLabel();
        } else {
            hideNoSalesFoundLabel();
        }
    } catch (NumberFormatException e) {
        // Handle the case where minPrice or maxPrice is not a valid double
        showInvalidPriceFormatLabel();
        salesTableView.setItems(FXCollections.emptyObservableList()); // Clear the TableView
    }
}

private void showInvalidPriceFormatLabel() {
    Label invalidPriceFormatLabel = new Label("Invalid price format. Please enter a valid number.");
    // Customize the label appearance if needed
    // invalidPriceFormatLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14;");

    // Set the label as the placeholder
    salesTableView.setPlaceholder(invalidPriceFormatLabel);
}


@FXML
    private void resetButtonPushed(ActionEvent event) {
        try {
            salesIdFilterField.clear();
            carPlateFilterField.clear();
            customerIdFilterField.clear();
            employeeIdFilterField.clear();
            minPriceField.clear();
            maxPriceField.clear();

            dateAndTimePicker.setValue(null);

            loadCSVData();

            salesTableView.setItems(dataList);

            // Check if the data list is empty
            if (dataList.isEmpty()) {
                showNoSalesFoundLabel();
            } else {
                hideNoSalesFoundLabel();
            }

            salesTableView.getSelectionModel().clearSelection();
        } catch (CsvException ex) {
            Logger.getLogger(SALESController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showNoSalesFoundLabel() {
        Label noSalesFoundLabel = new Label("NO SALES FOUND");
        salesTableView.setPlaceholder(noSalesFoundLabel);
    }

    private void hideNoSalesFoundLabel() {
        salesTableView.setPlaceholder(null);
    }


    /**
     * Initializes the controller class.
     */
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        salesId.setCellValueFactory(new PropertyValueFactory<>("salesId"));
       dateAndTime.setCellValueFactory(cellData -> {
    LocalDateTime dateTime = cellData.getValue().getDateAndTime();
    String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    return new javafx.beans.property.SimpleStringProperty(formattedDateTime);
});
        carPlate.setCellValueFactory(new PropertyValueFactory<>("carPlate"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        employeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        try {
            loadCSVData();
        } catch (CsvException ex) {
            Logger.getLogger(SALESController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadCSVData() throws CsvException {
        dataList.clear();

        String salesCsvFilePath = "src\\backups\\salesBackup.csv";
        String vehicleCsvFilePath = "src\\backups\\vehicleBackup.csv";

        try (CSVReader salesCsvReader = new CSVReader(new FileReader(salesCsvFilePath));
             CSVReader vehicleCsvReader = new CSVReader(new FileReader(vehicleCsvFilePath))) {

  
        List<String[]> salesAllData = salesCsvReader.readAll();
        List<String[]> vehicleAllData = vehicleCsvReader.readAll();

        // Assume the first row contains column headers
        String[] salesHeaders = salesAllData.get(0);
        String[] vehicleHeaders = vehicleAllData.get(0);

        for (int i = 1; i < salesAllData.size(); i++) {
            String[] salesRow = salesAllData.get(i);

            // Find the corresponding vehicle data based on carPlate
            List<String[]> matchedVehicles = vehicleAllData.stream()
                    .filter(vehicleRow -> vehicleRow[0].equals(salesRow[2])) // Assuming carPlate is the first column in vehicle.csv
                    .collect(Collectors.toList());

            // Assuming there is only one matching vehicle for each sale
            if (!matchedVehicles.isEmpty()) {
                String[] vehicleRow = matchedVehicles.get(0);

                sales salesData = new sales();
                salesData.setSalesId(salesRow[0]);

                // Convert the string representation of date and time to LocalDateTime
                LocalDateTime dateTime = LocalDateTime.parse(salesRow[1], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
                salesData.setDateAndTime(dateTime);

                salesData.setCarPlate(salesRow[2]);
                salesData.setCustomerId(salesRow[3]);
                salesData.setEmployeeId(salesRow[4]);
                salesData.setPrice(vehicleRow[2]); // Add other attributes as needed

                // Add the populated data to the ObservableList
                dataList.add(salesData);
            }
        }

        // Set the data to the TableView
        salesTableView.setItems(dataList);

    } catch (IOException | CsvException e) {
        e.printStackTrace();
        // Handle exceptions (e.g., file not found, CSV parsing errors)
    }
}

}
