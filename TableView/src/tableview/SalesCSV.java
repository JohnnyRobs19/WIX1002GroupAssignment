package tableview;

 
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author seren
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SalesCSV {

    private static int nextSalesID = 1;
    private static int nextCustomerID = 1;

    private final StringProperty carPlate;
    private final StringProperty customerID;
    private final StringProperty date;
    private final StringProperty employeeID;
    private final StringProperty salesID;

    public SalesCSV(String carPlate, String customerID, String date, String employeeID, String salesID) {
        this.carPlate = new SimpleStringProperty(carPlate);
        this.customerID = new SimpleStringProperty(customerID);
        this.date = new SimpleStringProperty(date);
        this.employeeID = new SimpleStringProperty(employeeID);
        this.salesID = new SimpleStringProperty(salesID);

        // Update nextSalesID based on the current salesID
        updateNextCustomerID(customerID);
    }

    private void updateNextCustomerID(String currentCustomerID) {
        try {
            int currentID = Integer.parseInt(currentCustomerID.substring(1)); // Remove prefix "C"
            nextCustomerID = Math.max(nextCustomerID, currentID + 1);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            System.err.println("Invalid customer ID format: " + currentCustomerID);
        }
    }

    public String getCarPlate() {
        return carPlate.get();
    }

    public StringProperty carPlateProperty() {
        return carPlate;
    }

    public String getCustomerID() {
        return customerID.get();
    }

    public StringProperty customerIDProperty() {
        return customerID;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getEmployeeID() {
        return employeeID.get();
    }

    public StringProperty employeeIDProperty() {
        return employeeID;
    }

    public String getSalesID() {
        return salesID.get();
    }

    public StringProperty salesIDProperty() {
        return salesID;
    }

    public static String getNextCustomerID() {
        String customerIDPrefix = "C00";
        return customerIDPrefix + nextCustomerID++;
    }

    public static String getNextSalesID() {
        String salesIDPrefix = "A00";
        return salesIDPrefix + nextSalesID++;
    }

    public static ObservableList<SalesCSV> loadCSVData(String filePath) {
        ObservableList<SalesCSV> salesDataList = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headersLine = reader.readLine(); // Assuming the first line contains headers

            // Read and add data to the ObservableList
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length >= 5) { // Validate row length
                    SalesCSV sales = new SalesCSV(row[0], row[1], row[2], row[3], row[4]);
                    salesDataList.add(sales);
                } else {
                    System.err.println("Invalid row: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return salesDataList;
    }
}


   