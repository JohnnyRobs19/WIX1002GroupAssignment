package tableview;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


/**
 * FXML Controller class
 *
 * @author seren
 */

 


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static tableview.CustomerCSV.loadCSVData;

public class TableViewController {

    @FXML
    private TableColumn<CustomerCSV, String> customerIDColumn;

    @FXML
    private TableColumn<CustomerCSV, String> customerNameColumn;

    @FXML
    private TableColumn<CustomerCSV, String> phoneColumn;

    @FXML
    private TableColumn<CustomerCSV, String> postcodeColumn;

    @FXML
    private TableView<CustomerCSV> tableView;

    private ObservableList<CustomerCSV> customerDataList;

    @FXML
    private TextField customerNameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField postcodeTextField;

    @FXML
    void initialize() {
        assert customerIDColumn != null : "fx:id=\"customerIDColumn\" was not injected: check your FXML file 'TableView.fxml'.";
        assert customerNameColumn != null : "fx:id=\"customerNameColumn\" was not injected: check your FXML file 'TableView.fxml'.";
        assert phoneColumn != null : "fx:id=\"phoneColumn\" was not injected: check your FXML file 'TableView.fxml'.";
        assert postcodeColumn != null : "fx:id=\"postcodeColumn\" was not injected: check your FXML file 'TableView.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'TableView.fxml'.";

        customerDataList = FXCollections.observableArrayList();

        loadCSVData("C:\\Users\\seren\\Downloads\\cust.csv");

        tableView.setItems(customerDataList);

        customerIDColumn.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty());
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        postcodeColumn.setCellValueFactory(cellData -> cellData.getValue().postcodeProperty());
    }

    @FXML
    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent viewSalesParent = FXMLLoader.load(getClass().getResource("Sales.fxml"));
        Scene viewSalesScene = new Scene(viewSalesParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(viewSalesScene);
        window.show();
    }

    @FXML
    public void changeScreenButtonPushed1(ActionEvent event) throws IOException {
        Parent viewVehicleParent = FXMLLoader.load(getClass().getResource("Vehicle.fxml"));
        Scene viewVehicleScene = new Scene(viewVehicleParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(viewVehicleScene);
        window.show();
    }

    public void newPersonButtonPushed() {
        // Automatically generate the customerID using the static counter in CustomerCSV
        String nextCustomerID = String.valueOf(CustomerCSV.getNextCustomerID());

        // Create a new CustomerCSV object
        CustomerCSV newCustomer = new CustomerCSV(
                nextCustomerID,
                customerNameTextField.getText(),
                phoneTextField.getText(),
                postcodeTextField.getText()
        );

        // Update the customer ID based on the generated ID
        newCustomer.updateCustomerID(nextCustomerID);

        //clear the text filed after adding object
        customerNameTextField.clear();
        phoneTextField.clear();
        postcodeTextField.clear();

        // Add the new customer to the TableView
        tableView.getItems().add(newCustomer);

        // Update the CSV file with the modified data
        updateCSVFile("C:\\Users\\seren\\Downloads\\cust.csv", newCustomer);
    }

    @FXML
    private void loadCSVData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headersLine = reader.readLine(); // Assuming the first line contains headers

            // Read and add data to the ObservableList
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                // Allow null values if the row doesn't have enough elements
                CustomerCSV customer = new CustomerCSV(
                        (row.length > 0) ? row[0] : "",  // Assuming the first element is customerID
                        (row.length > 1) ? row[1] : "",  // Assuming the second element is customerName
                        (row.length > 2) ? row[2] : "",  // Assuming the third element is phone
                        (row.length > 3) ? row[3] : ""   // Assuming the fourth element is postcode
                );
                // Process the created CustomerCSV object as needed (e.g., add it to a list)
                customerDataList.add(customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCSVFile(String filePath, CustomerCSV newCustomer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Append the new data to the CSV file
            String newLine = String.join(",", newCustomer.getCustomerID(), newCustomer.getCustomerName(), newCustomer.getPhone(), newCustomer.getPostcode());
            writer.write(newLine);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    


    


    
   

    