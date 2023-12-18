package viewinfo;

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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class EMPSALESController implements Initializable {

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
    private static String selectedEmployeeId;

    public void setEmployeeId(String employeeId) {
        this.selectedEmployeeId = employeeId;
        loadCSVData(); // Load data when the employee ID is set
    }


    public void empSalesBackButtonPushed(ActionEvent event) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("SALESFXML.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainViewScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        salesId.setCellValueFactory(new PropertyValueFactory<>("salesId"));
        dateAndTime.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        carPlate.setCellValueFactory(new PropertyValueFactory<>("carPlate"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
  filteredData = new FilteredList<>(dataList, b -> true);
        
    }

    private void setupSearchFunctionality() {
        // Set up filter fields for each variable
       

        salesIdFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(sale -> filterBySalesId(sale, newValue)));

        dateAndTimeDatePicker.valueProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(sale -> filterByDateAndTime(sale, newValue)));

        carPlateFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(sale -> filterByCarPlate(sale, newValue)));

        customerIdFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(sale -> filterByCustomerId(sale, newValue)));

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

    // Create a new FilteredList based on the existing empSalesTable items
    FilteredList<sales> filteredData = new FilteredList<>(empSalesTable.getItems());

    // Apply the filters only to the data that belongs to the selected employee
    filteredData.setPredicate(sale ->
            filterBySalesId(sale, salesIdFilter) &&
            filterByDateAndTime(sale, dateFilter) &&
            filterByCarPlate(sale, carPlateFilter) &&
            filterByCustomerId(sale, customerIdFilter));

    // Wrap the filtered data in a SortedList
    SortedList<sales> sortedData = new SortedList<>(filteredData);

    // Bind the SortedList comparator to the TableView comparator
    sortedData.comparatorProperty().bind(empSalesTable.comparatorProperty());

    // Set the sorted (and filtered) data to the TableView
    empSalesTable.setItems(sortedData);
}

private void updateTableView() {
    // Wrap the filtered data in a SortedList
    SortedList<sales> sortedData = new SortedList<>(filteredData);

    // Bind the SortedList comparator to the TableView comparator
    sortedData.comparatorProperty().bind(empSalesTable.comparatorProperty());

    // Set the sorted (and filtered) data to the TableView
    empSalesTable.setItems(sortedData);
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
    if (selectedEmployeeId != null && !selectedEmployeeId.isEmpty()) {
        filteredData.setPredicate(sale -> sale.getEmployeeId().equals(selectedEmployeeId));
    }

    // Update the TableView
    updateTableView();
}


  public void loadCSVData() {
    // Clear existing data to avoid duplication
    dataList.clear();

    String salesCsvFilePath = "C:\\Users\\HP\\Documents\\WIIX1002GROUPRAGA\\VIEWINFO\\src\\sales.csv";
    String vehicleCsvFilePath = "C:\\Users\\HP\\Documents\\WIIX1002GROUPRAGA\\VIEWINFO\\src\\vehicle.csv";

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
        filteredData.setPredicate(customer -> customer.getEmployeeId().equals(selectedEmployeeId));

        // Set the filtered data to the TableView
        updateTableView();

    } catch (IOException | CsvException e) {
        e.printStackTrace();
    }
}


}


