package tableview;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SalesController {

    @FXML
    private TableColumn<SalesCSV, String> CarPlateColumn;

    @FXML
    private TextField CarPlateTextField;

    @FXML
    private TableColumn<SalesCSV, String> CustomerIDColumn;

    @FXML
    private TableColumn<SalesCSV, String> DateColumn;

    @FXML
    private TextField DateTextField;

    @FXML
    private TableColumn<SalesCSV, String> EmployeeIDColumn;

    @FXML
    private TextField EmployeeIDTextField;

    @FXML
    private TableColumn<SalesCSV, String> SalesIDColumn;

    @FXML
    private TableView<SalesCSV> tableview;

    private final ObservableList<SalesCSV> salesDataList = FXCollections.observableArrayList();

    // ...

    @FXML
    public void changeScreenButtonPushed2(ActionEvent event) throws IOException {
        Parent viewTableViewParent = FXMLLoader.load(getClass().getResource("TableView.fxml"));
        Scene viewTableViewScene = new Scene(viewTableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewTableViewScene);
        window.show();
    }

    @FXML
    public void changeScreenButtonPushed1(ActionEvent event) throws IOException {
        Parent viewVehicleParent = FXMLLoader.load(getClass().getResource("Vehicle.fxml"));
        Scene viewVehicleScene = new Scene(viewVehicleParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewVehicleScene);
        window.show();
    }

    @FXML
    public void initialize() {
        CarPlateColumn.setCellValueFactory(cellData -> cellData.getValue().carPlateProperty());
        CustomerIDColumn.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty());
        DateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        EmployeeIDColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIDProperty());
        SalesIDColumn.setCellValueFactory(cellData -> cellData.getValue().salesIDProperty());

        loadCSVData("C:\\Users\\seren\\Downloads\\sales.csv");

        tableview.setItems(salesDataList);
    }

    public void newPersonButtonPushed2() {
        SalesCSV newSales = new SalesCSV(
            SalesCSV.getNextSalesID(),
            CarPlateTextField.getText(),
            DateTextField.getText(),
            SalesCSV.getNextCustomerID(),
            EmployeeIDTextField.getText()
        );

        salesDataList.add(newSales);
        clearTextFields();
        
        // Update the CSV file with the modified data
        updateCSVFile("C:\\Users\\seren\\Downloads\\sales.csv", newSales);
    }

    private void loadCSVData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headersLine = reader.readLine(); // Assuming the first line contains headers

            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length >= 5) {
                    SalesCSV sales = new SalesCSV(row[0], row[1], row[2], row[3], row[4]);
                    salesDataList.add(sales);
                } else {
                    System.err.println("Invalid row: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearTextFields() {
        CarPlateTextField.clear();
        DateTextField.clear();
        EmployeeIDTextField.clear();
    }
    
    private void updateCSVFile(String filePath, SalesCSV newSales) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        // Append the new data to the CSV file
        String newLine = String.join(",", newSales.getCarPlate(), newSales.getCustomerID(), newSales.getDate(), newSales.getEmployeeID(), newSales.getSalesID());
        writer.write(newLine);
        writer.newLine();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
    
