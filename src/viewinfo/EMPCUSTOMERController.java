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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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
public class EMPCUSTOMERController implements Initializable {

    @FXML
    private TableView<customer> empCustomerTableView;

    @FXML
    private TableColumn<customer, String> customerId;

    @FXML
    private TableColumn<customer, String> customerName;

    @FXML
    private TableColumn<customer, String> phoneNumber;

    @FXML
    private TableColumn<customer, String> postCode;

    @FXML
    private TextField customerIdFilterField;

    @FXML
    private TextField customerNameFilterField;

    @FXML
    private TextField phoneNumberFilterField;

    @FXML
    private TextField postCodeFilterField;

   

    private static String selectedEmployeeId;
    // Map to store employee ID for each customer
    private Map<String, List<customer>> employeeToCustomerMap;

    private ObservableList<customer> data;
    private FilteredList<customer> filteredData;

    public void setEmployeeId(String employeeId) {
        this.selectedEmployeeId = employeeId;
        loadCSVData();
        // Load data when the employee ID is set
    }

    public void empCustomerBackButtonPushed(ActionEvent event) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("SALESFXML.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainViewScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        postCode.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        employeeToCustomerMap = new HashMap<>();
        loadCSVData();
        setupSearchFunctionality();
    }

    private void loadCSVData() {
        String salesCsvFilePath = "C:\\Users\\HP\\Documents\\WIIX1002GROUPRAGA\\VIEWINFO\\src\\sales.csv";
        String custCsvFilePath = "C:\\Users\\HP\\Documents\\WIIX1002GROUPRAGA\\VIEWINFO\\src\\cust.csv";

        try (CSVReader salesCsvReader = new CSVReader(new FileReader(salesCsvFilePath));
             CSVReader custCsvReader = new CSVReader(new FileReader(custCsvFilePath))) {

            // Load sales data
            List<sales> salesData = loadSalesData(salesCsvReader);

            // Load customer data
            List<customer> customerData = loadCustomerData(custCsvReader);

            // Connect sales and customer data based on employeeId
            Map<String, List<customer>> employeeToCustomerMap = connectSalesAndCustomers(salesData, customerData);

            // Retrieve the list of customers for the selected employee from the map
            List<customer> customerList = employeeToCustomerMap.getOrDefault(selectedEmployeeId, new ArrayList<>());

            // Set the filtered data to the TableView
            empCustomerTableView.setItems(FXCollections.observableArrayList(customerList));
            setupSearchFunctionality();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    private List<sales> loadSalesData(CSVReader csvReader) throws IOException, CsvException {
        // Load sales data from CSV

        List<String[]> allData = csvReader.readAll();
        // Assume the first row contains column headers
        String[] headers = allData.get(0);
        // Create a list to store sales data
        List<sales> salesData = new ArrayList<>();
        // Start from index 1 to skip headers
        for (int i = 1; i < allData.size(); i++) {
            String[] row = allData.get(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalDateTime dateTime = LocalDateTime.parse(row[1], formatter);
            sales salesDataItem = new sales(row[0], dateTime, row[2], row[3], row[4]);

            // Add the populated data to the list
            salesData.add(salesDataItem);
        }
        return salesData;
    }

    private List<customer> loadCustomerData(CSVReader csvReader) throws IOException, CsvException {
        // Load customer data from CSV
        List<String[]> allData= csvReader.readAll();
        // Assume the first row contains column headers
        String[] headers = allData.get(0);
        // Create a list to store customer data
        List<customer> customerData = new ArrayList<>();
        // Start from index 1 to skip headers
        for (int i = 1; i < allData.size(); i++) {
            String[] row = allData.get(i);
            // Create an instance of Customer and populate its properties
            customer customerDataItem = new customer(row[0], row[1], row[2], row[3]);
            // Add the populated data to the list
            customerData.add(customerDataItem);
        }
        return customerData;
    }

    private Map<String, List<customer>> connectSalesAndCustomers(List<sales> salesData, List<customer> customerData) {
        // Connect sales and customer data based on employeeId
        Map<String, List<customer>> employeeToCustomerMap = new HashMap<>();
        for (sales sale : salesData) {
            // Find the customer with the matching customerId
            customer matchedCustomer = customerData.stream()
                    .filter(customer -> customer.getCustomerId().equals(sale.getCustomerId()))
                    .findFirst()
                    .orElse(null);

            // If a matching customer is found, associate it with the employeeId
            if (matchedCustomer != null) {
                employeeToCustomerMap
                        .computeIfAbsent(sale.getEmployeeId(), k -> new ArrayList<>())
                        .add(matchedCustomer);
            }
        }
        return employeeToCustomerMap;
    }

    @FXML
    private void searchButtonPushed(ActionEvent event) {
        applyFilters();
        updateTableView();
    }

    @FXML
    private void resetButtonPushed(ActionEvent event) {
        customerIdFilterField.clear();
        customerNameFilterField.clear();
        phoneNumberFilterField.clear();
        postCodeFilterField.clear();
       
        filteredData.setPredicate(customer -> true);
        updateTableView();
    }

    private void applyFilters() {
        String customerIdFilter = customerIdFilterField.getText().toLowerCase();
        String customerNameFilter = customerNameFilterField.getText().toLowerCase();
        String phoneNumberFilter = phoneNumberFilterField.getText().toLowerCase();
        String postCodeFilter = postCodeFilterField.getText().toLowerCase();
      
        filteredData.setPredicate(customer -> {
            String customerId = customer.getCustomerId().toLowerCase();
            String name = customer.getCustomerName().toLowerCase();
            String phone = customer.getPhoneNumber().toLowerCase();
            String postCode = customer.getPostCode().toLowerCase();

            return customerId.contains(customerIdFilter) &&
                    name.contains(customerNameFilter) &&
                    phone.contains(phoneNumberFilter) &&
                    postCode.contains(postCodeFilter);
                    
        });
    }

    private void updateTableView() {
        SortedList<customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(empCustomerTableView.comparatorProperty());
        empCustomerTableView.setItems(sortedData);
    }

    private void setupSearchFunctionality() {
        filteredData = new FilteredList<>(empCustomerTableView.getItems(), b -> true);

        // Set the filter Predicate whenever the filter changes.
  
        // Wrap the FilteredList in a SortedList.
        SortedList<customer> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(empCustomerTableView.comparatorProperty());

        // Add sorted (and filtered) data to the table.
        empCustomerTableView.setItems(sortedData);
    }
}
