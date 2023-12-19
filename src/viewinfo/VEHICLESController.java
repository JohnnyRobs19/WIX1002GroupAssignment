/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package viewinfo;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class VEHICLESController implements Initializable {
    @FXML
    private TableView<vehicles> vehicleTableView;

    @FXML
    private TableColumn<vehicles, String> carPlate;

    @FXML
    private TableColumn<vehicles, String> carModel;

    @FXML
    private TableColumn<vehicles, String> acquiredPrice;

    @FXML
    private TableColumn<vehicles, String> carStatus;

    @FXML
    private TableColumn<vehicles, String> salesPrice;

    @FXML
    private TextField carPlateFilterField;

    @FXML
    private TextField carModelFilterField;

    @FXML
    private TextField acquiredPriceMaxFilterField;

    @FXML
    private ChoiceBox<String> statusFilterField;

    @FXML
    private TextField salesPriceMaxFilterField;

    @FXML
    private TextField acquiredPriceMinFilterField;

    @FXML
    private TextField salesPriceMinFilterField;

    private ObservableList<vehicles> data = FXCollections.observableArrayList();

    // Define the filtered data list to be used for filtering
    private FilteredList<vehicles> filteredData;
    private final ChangeListener<String> carPlateChangeListener = (observable, oldValue, newValue) -> applyFilters();
    private final ChangeListener<String> carModelChangeListener = (observable, oldValue, newValue) -> applyFilters();
    private final ChangeListener<String> statusChangeListener = (observable, oldValue, newValue) -> applyFilters();
    private final ChangeListener<String> acquiredPriceMinChangeListener = (observable, oldValue, newValue) -> applyFilters();
    private final ChangeListener<String> acquiredPriceMaxChangeListener = (observable, oldValue, newValue) -> applyFilters();
    private final ChangeListener<String> salesPriceMinChangeListener = (observable, oldValue, newValue) -> applyFilters();
    private final ChangeListener<String> salesPriceMaxChangeListener = (observable, oldValue, newValue) -> applyFilters();

    @FXML
    public void vehiclesBackButtonPushed(ActionEvent event) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("MANAGEMENTFXML.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainViewScene);
        window.show();
    }

   @Override
public void initialize(URL url, ResourceBundle rb) {
    // TODO
    carPlate.setCellValueFactory(new PropertyValueFactory<>("carPlate"));
    carModel.setCellValueFactory(new PropertyValueFactory<>("carModel"));
    acquiredPrice.setCellValueFactory(new PropertyValueFactory<>("acquiredPrice"));
    carStatus.setCellValueFactory(new PropertyValueFactory<>("carStatus"));
    salesPrice.setCellValueFactory(new PropertyValueFactory<>("salesPrice"));
statusFilterField.getItems().addAll("SOLD", "AVAILABLE");
    // Add the carStatus column with a custom cellValueFactory
   
    carStatus.setCellValueFactory(cellData -> {
        SimpleStringProperty property = new SimpleStringProperty();
        String status = cellData.getValue().getCarStatus();
        property.setValue(status.equals("0") ? "SOLD" : (status.equals("1") ? "AVAILABLE" : "UNKNOWN"));
        return property;
    });

    // Add the columns to the TableView


    try {
        loadCSVData();

        // Initialize the filteredData list with all data
        filteredData = new FilteredList<>(data, b -> true);

        // Update the TableView with loaded data
        updateTableView();
    } catch (CsvException ex) {
        Logger.getLogger(EMPVEHICLESController.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    @FXML
    private void searchButtonPushed(ActionEvent event) {
        // Apply the filters and update the filtered data
        applyFilters();

        // Update the TableView
        updateTableView();
    }

    @FXML
    private void resetButtonPushed(ActionEvent event) {
        // Clear the filter fields
        carPlateFilterField.clear();
        carModelFilterField.clear();
        statusFilterField.getSelectionModel().clearSelection();
        acquiredPriceMinFilterField.clear();
        acquiredPriceMaxFilterField.clear();
        salesPriceMinFilterField.clear();
        salesPriceMaxFilterField.clear();

        // Reset the filtered data to display all data
        filteredData.setPredicate(vehicle -> true);

        // Update the TableView
        updateTableView();
    }

    private void updateTableView() {
        // Wrap the filtered data in a SortedList
        SortedList<vehicles> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(vehicleTableView.comparatorProperty());

        // Set the sorted (and filtered) data to the TableView
        vehicleTableView.setItems(sortedData);
    }

    // other methods and initialization code
   
@FXML
private void applyFilters() {
    String carPlateFilter = carPlateFilterField.getText().toLowerCase();
    String carModelFilter = carModelFilterField.getText().toLowerCase();
    String acquiredPriceMinFilter = acquiredPriceMinFilterField.getText();
    String acquiredPriceMaxFilter = acquiredPriceMaxFilterField.getText();
    String salesPriceMinFilter = salesPriceMinFilterField.getText();
    String salesPriceMaxFilter = salesPriceMaxFilterField.getText();

    // Automatically set status filter to "SOLD" if searching by salesPrice
    if (!salesPriceMinFilter.isEmpty() || !salesPriceMaxFilter.isEmpty()) {
        statusFilterField.setValue("SOLD");
    }

    filteredData.setPredicate(vehicle -> {
        String carPlate = vehicle.getCarPlate().toLowerCase();
        String carModel = vehicle.getCarModel().toLowerCase();
        String status = vehicle.getCarStatus();
        String salesPrice = vehicle.getSalesPrice().toLowerCase();
        String acquiredPrice = vehicle.getAcquiredPrice();

        boolean isCarPlateMatch = carPlate.contains(carPlateFilter);
        boolean isCarModelMatch = carModel.contains(carModelFilter);
        boolean isStatusMatch = isStatusMatch(status, statusFilterField.getValue());
        boolean isAcquiredPriceInRange = isWithinPriceRange(acquiredPrice, acquiredPriceMinFilter,
                acquiredPriceMaxFilter);
        boolean isSalesPriceInRange = isWithinPriceRange(salesPrice, salesPriceMinFilter, salesPriceMaxFilter);

        return isCarPlateMatch && isCarModelMatch && isStatusMatch && isAcquiredPriceInRange
                && isSalesPriceInRange;
    });
}


private boolean isStatusMatch(String actualStatus, String filterStatus) {
    if (filterStatus == null || filterStatus.isEmpty()) {
        return true; // If filter status is null or empty, consider it as a match
    }

    // Map actualStatus to "SOLD", "AVAILABLE", or "UNKNOWN"
    String mappedStatus = actualStatus == null ? "UNKNOWN" : (actualStatus.equals("0") ? "SOLD" : (actualStatus.equals("1") ? "AVAILABLE" : "UNKNOWN"));

   

    return mappedStatus.equalsIgnoreCase(filterStatus);
}


    private boolean isWithinPriceRange(String price, String minPrice, String maxPrice) {
    // Handle the case where the price is empty
    if (price == null || price.isEmpty()) {
        return true;
    }

    try {
        double priceValue = Double.parseDouble(price);
        double minPriceValue = minPrice.isEmpty() ? Double.MIN_VALUE : Double.parseDouble(minPrice);
        double maxPriceValue = maxPrice.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPrice);

        return priceValue >= minPriceValue && priceValue <= maxPriceValue;
    } catch (NumberFormatException e) {
        // Handle invalid number format
        return false;
    }
}


    private void loadCSVData() throws CsvException {
        // Specify your CSV file path
        String csvFilePath = "C:\\Users\\HP\\Documents\\WIIX1002GROUPRAGA\\VIEWINFO\\src\\vehicle.csv";

           try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
        // Read all records at once
        List<String[]> allData = csvReader.readAll();

        // Assume the first row contains column headers
        String[] headers = allData.get(0);

        // Start from index 1 to skip headers
        for (int i = 1; i < allData.size(); i++) {
            String[] row = allData.get(i);

            // Create an instance of vehicles and populate its properties
            vehicles vehiclesData = new vehicles();
            vehiclesData.setCarPlate(row[0]);
            vehiclesData.setCarModel(row[1]);
            vehiclesData.setAcquiredPrice(row[2]);
            vehiclesData.setCarStatus(row[3]);
            vehiclesData.setSalesPrice(row[4]);

            // Add the populated data to the ObservableList
            data.add(vehiclesData);
        }
    } catch (IOException | CsvException e) {
        e.printStackTrace();
        // Handle exceptions (e.g., file not found, CSV parsing errors)
    }
}}