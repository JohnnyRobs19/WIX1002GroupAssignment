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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class CUSTOMERController implements Initializable {
    @FXML
    private TableView<customer> customerTableView;

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

    // Original data loaded from CSV
    private ObservableList<customer> originalData;

    public void customerBackButtonPushed(ActionEvent event) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("MANAGEMENTFXML.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainViewScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerId.setCellValueFactory(new PropertyValueFactory<customer, String>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<customer, String>("customerName"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<customer, String>("phoneNumber"));
        postCode.setCellValueFactory(new PropertyValueFactory<customer, String>("postCode"));

        try {
            loadCSVData();
        } catch (CsvException ex) {
            Logger.getLogger(CUSTOMERController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Save the original data loaded from CSV
        originalData = FXCollections.observableArrayList(customerTableView.getItems());
    }

    private void loadCSVData() throws CsvException {
        // Specify your CSV file path
        String csvFilePath = "C:\\Users\\HP\\Documents\\WIIX1002GROUPRAGA\\VIEWINFO\\src\\cust.csv";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            // Read all records at once
            List<String[]> allData = csvReader.readAll();

            // Assume the first row contains column headers
            String[] headers = allData.get(0);

            // Create ObservableList for TableView
            ObservableList<customer> data = FXCollections.observableArrayList();

            // Start from index 1 to skip headers
            for (int i = 1; i < allData.size(); i++) {
                String[] row = allData.get(i);

                // Create an instance of Customer and populate its properties
                customer customerData = new customer();

                // Assuming the order of columns is customerId, customerName, phoneNumber, postCode
                customerData.setCustomerId(row[0]);
                customerData.setCustomerName(row[1]);
                customerData.setPhoneNumber(row[2]);
                customerData.setPostCode(row[3]);

                // Add the populated data to the ObservableList
                data.add(customerData);
            }

            // Set the data to the TableView
            customerTableView.setItems(data);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., file not found, CSV parsing errors)
        }
    }

    @FXML
    private void searchButtonPushed(ActionEvent event) {
        // Get the filter values from text fields
        String filterCustomerId = customerIdFilterField.getText().trim().toLowerCase();
        String filterCustomerName = customerNameFilterField.getText().trim().toLowerCase();
        String filterPhoneNumber = phoneNumberFilterField.getText().trim().toLowerCase();
        String filterPostCode = postCodeFilterField.getText().trim().toLowerCase();

        // Create a filtered list based on the filter values
        ObservableList<customer> filteredData = originalData.filtered(
                customer -> customer.getCustomerId().toLowerCase().contains(filterCustomerId)
                        && customer.getCustomerName().toLowerCase().contains(filterCustomerName)
                        && customer.getPhoneNumber().toLowerCase().contains(filterPhoneNumber)
                        && customer.getPostCode().toLowerCase().contains(filterPostCode)
        );

        // Set the filtered data to the TableView
        customerTableView.setItems(filteredData);
    }

    @FXML
    private void resetButtonPushed(ActionEvent event) {
        // Reset the TableView to its original state
        customerTableView.setItems(originalData);

        // Clear the filter text fields
        customerIdFilterField.clear();
        customerNameFilterField.clear();
        phoneNumberFilterField.clear();
        postCodeFilterField.clear();
    }
}
