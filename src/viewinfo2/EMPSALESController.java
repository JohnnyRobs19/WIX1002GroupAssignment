package viewinfo2;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import viewinfo2.SALESFXMLController.EmployeeIdSetter;

public class EMPSALESController implements Initializable, SALESFXMLController.EmployeeIdSetter {

    private String employeeId;
    @FXML
    private TableView<sales> empSalesTable;

    @FXML
    private TableColumn<sales, String> salesId;

    @FXML
    private TableColumn<sales, String> dateAndTime;

    @FXML
    private TableColumn<sales, String> carPlate;

    @FXML
    private TableColumn<sales, String> customerId;

    @FXML
    private TextField salesIdFilterField;

    @FXML
    private DatePicker dateAndTimeDatePicker;

    @FXML
    private TextField carPlateFilterField;

    @FXML
    private TextField customerIdFilterField;
private static FilteredList<sales> filteredData;
    private static  ObservableList<sales> dataList = FXCollections.observableArrayList();
     private static String loggedInUserId;

    // ...

 

    public void empSalesBackButtonPushed(ActionEvent event) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("SALESFXML.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainViewScene);
        window.show();
    }
    public void userClickedOnTable(MouseEvent event) throws IOException {
    // Get the selected item from the table
    sales selectedCP = empSalesTable.getSelectionModel().getSelectedItem();

    // Check if a sale is selected
    if (selectedCP != null) {
        // Get the selected sale's car plate
        String selectedCarPlate = selectedCP.getCarPlate();

        // Pass the selected sale's car plate to VEHICLEVIEWController
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("VEHICLEVIEWEMP.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);

        VEHICLEVIEWEMPController controller = loader.getController();

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
  filteredData = new FilteredList<>(dataList, b -> true);
     loggedInUserId = AppContext.getInstance().getLoggedInUserId();
        employeeId = loggedInUserId;
 loadCSVData();
    // Set up search functionality
    setupSearchFunctionality();

    }
     @Override
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
        // Now you have the employeeId, you can use it as needed
        
    }

    private void setupSearchFunctionality() {
        // Set up filter fields for each variable
       

        // Wrap the FilteredList in a SortedList.
        SortedList<sales> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(empSalesTable.comparatorProperty());

        // Add sorted (and filtered) data to the table.
        empSalesTable.setItems(sortedData);
    }

    @FXML
    private void searchButtonPushed(ActionEvent event) {
        // Get the values from the filter fields
        String salesIdFilter = salesIdFilterField.getText().toLowerCase();
        LocalDate dateFilter = dateAndTimeDatePicker.getValue();
        String carPlateFilter = carPlateFilterField.getText().toLowerCase();
        String customerIdFilter = customerIdFilterField.getText().toLowerCase();

        // Set up the search filter
        filteredData.setPredicate(sale ->
                filterByEmployeeId(sale) &&
                filterBySalesId(sale, salesIdFilter) &&
                filterByDateAndTime(sale, dateFilter) &&
                filterByCarPlate(sale, carPlateFilter) &&
                filterByCustomerId(sale, customerIdFilter));

        // Update the TableView
        updateTableView();
    }
 @FXML
    private void resetButtonPushed(ActionEvent event) {
        // Clear the filter fields
        salesIdFilterField.clear();
        dateAndTimeDatePicker.setValue(null);
        carPlateFilterField.clear();
        customerIdFilterField.clear();

        // Clear the existing filters
        filteredData.setPredicate(null);

        // Apply the filter based on the selected employee's ID
        if (loggedInUserId != null && !loggedInUserId.isEmpty()) {
            filteredData.setPredicate(sale -> sale.getEmployeeId().equals(loggedInUserId));
        }

        // Update the TableView
        updateTableView();
    }


 private void updateTableView() {
    // Wrap the filtered data in a SortedList
    SortedList<sales> sortedData = new SortedList<>(filteredData);

    // Bind the SortedList comparator to the TableView comparator
    sortedData.comparatorProperty().bind(empSalesTable.comparatorProperty());

    // Set the sorted (and filtered) data to the TableView
    empSalesTable.setItems(sortedData);

    // Check if the filtered data is empty
    if (filteredData.isEmpty()) {
        // If no sales found, display a message
        showNoSalesFoundLabel();
    } else {
        // If there are results, hide the message label
        hideNoSalesFoundLabel();
    }
}

private void showNoSalesFoundLabel() {
    Label noSalesFoundLabel = new Label("NO SALES FOUND");
    // Customize the label appearance if needed
    // noSalesFoundLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14;");

    // Set the label as the placeholder
    empSalesTable.setPlaceholder(noSalesFoundLabel);
}

private void hideNoSalesFoundLabel() {
    // Reset the placeholder (clear the "NO SALES FOUND" message)
    empSalesTable.setPlaceholder(null);
}




    private boolean filterBySalesId(sales sale, String filter) {
        return filter == null || filter.isEmpty() || sale.getSalesId().toLowerCase().contains(filter.toLowerCase());
    }

    private boolean filterByDateAndTime(sales sale, LocalDate filter) {
        return filter == null || sale.getDateAndTime().toLocalDate().equals(filter);
    }

    private boolean filterByCarPlate(sales sale, String filter) {
        return filter == null || filter.isEmpty() || sale.getCarPlate().toLowerCase().contains(filter.toLowerCase());
    }

    private boolean filterByCustomerId(sales sale, String filter) {
        return filter == null || filter.isEmpty() || sale.getCustomerId().toLowerCase().contains(filter.toLowerCase());
    }
    private boolean filterByEmployeeId(sales sale) {
        // Check if the employeeId of the sale matches the logged-in user's employeeId
        return sale.getEmployeeId().equals(loggedInUserId);
    }



  public void loadCSVData() {
    // Clear existing data to avoid duplication
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
                    .filter(vehicleRow -> vehicleRow[0].equals(salesRow[2]))
                    .collect(Collectors.toList());

            // Assuming there is only one matching vehicle for each sale
            if (!matchedVehicles.isEmpty()) {
                String[] vehicleRow = matchedVehicles.get(0);

                sales salesData = new sales();
                salesData.setSalesId(salesRow[0]);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                salesData.setDateAndTime(LocalDateTime.parse(salesRow[1], formatter));

                salesData.setCarPlate(salesRow[2]);
                salesData.setCustomerId(salesRow[3]);
                salesData.setEmployeeId(salesRow[4]);
                salesData.setPrice(vehicleRow[2]); // Add other attributes as needed

                // Add the populated data to the ObservableList
                dataList.add(salesData);
            }
        }

        // Set the data to the TableView
        // Filter data based on the selected employee's ID
        filteredData.setPredicate(sales -> sales.getEmployeeId().equals(loggedInUserId));

        // Set the filtered data to the TableView
        updateTableView();

    } catch (IOException | CsvException e) {
        e.printStackTrace();
    }
}


}



