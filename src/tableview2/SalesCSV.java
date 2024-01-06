package tableview2;

 
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author seren
 */
// Import statements for necessary JavaFX and Java libraries
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.beans.value.ObservableValue;
import viewinfo2.AppContext;

// SalesCSV class representing sales data
public class SalesCSV {

    // Static variables to keep track of the next available sales and employee IDs
    private static int nextSalesID = 1;
    private static int nextEmployeeID = 1;

    // StringProperty for salesID
    private final StringProperty salesID;
    // ObjectProperty for date (LocalDateTime)
    private final ObjectProperty<LocalDateTime> date;
    // StringProperty for carPlate
    private final StringProperty carPlate;
    // StringProperty for customerID
    private final StringProperty customerID;
    // StringProperty for employeeID
    private final StringProperty employeeID;

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
    // Constructor to create a new SalesCSV object
    public SalesCSV(String salesID, LocalDateTime date, String carPlate, String customerID, String employeeID) {
        // Initialize StringProperty and ObjectProperty with provided values
        this.salesID = new SimpleStringProperty(salesID);
        this.date = new SimpleObjectProperty<>(date);
        this.carPlate = new SimpleStringProperty(carPlate);
        this.customerID = new SimpleStringProperty(customerID);
        this.employeeID = new SimpleStringProperty(employeeID);
  this.date.set(date);
  
  // If employeeID is not provided, use the ID of the logged-in user
if (employeeID == null || employeeID.isEmpty()) {
    // Retrieve the logged-in user ID from AppContext
    String loggedInUserID = getLoggedInUserID();

    // Set the employeeID property to the ID of the logged-in user
    this.employeeID.set(loggedInUserID);
}
        // Update nextSalesID and nextEmployeeID based on the provided IDs
        updateNextSalesID(salesID);
        
    }

    // Method to update the nextSalesID based on the provided currentSalesID
    private void updateNextSalesID(String currentSalesID) {
        try {
            int currentID = Integer.parseInt(currentSalesID.substring(1));
            nextSalesID = Math.max(nextSalesID, currentID + 1);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            System.err.println("Invalid sales ID format: " + currentSalesID);
        }
    }

    
    
    public static String getLoggedInUserID() {
    // Retrieve the logged-in user ID from AppContext
    return AppContext.getInstance().getLoggedInUserId();
}

    // Getter method for salesID
    public String getSalesID() {
        return salesID.get();
    }

    // Getter method for salesID property
    public StringProperty salesIDProperty() {
        return salesID;
    }

    // Getter method for date property
    public ObjectProperty<LocalDateTime> getDate() {
        return date;
    }

    // Getter method for formatted date property
    public StringProperty getFormattedDateProperty() {
        // Format the LocalDateTime to a string without the 'T'
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = date.get().format(formatter);

        // Create a new SimpleStringProperty with the formatted date
        return new SimpleStringProperty(formattedDate);
    }

    // Getter method for carPlate
    public String getCarPlate() {
        return carPlate.get();
    }

    // Getter method for carPlate property
    public StringProperty carPlateProperty() {
        return carPlate;
    }

    // Getter method for customerID
    public String getCustomerID() {
        return customerID.get();
    }

    // Getter method for customerID property
    public StringProperty customerIDProperty() {
        return customerID;
    }

    // Getter method for employeeID
    public String getEmployeeID() {
        return employeeID.get();
    }

    // Getter method for employeeID property
    public StringProperty employeeIDProperty() {
        return employeeID;
    }

    // Static method to get the next sales ID
    public static String getNextSalesID() {
        return "A0" + String.format("%03d", nextSalesID++);
    }

    
    // Static method to load CSV data from a file into an ObservableList of SalesCSV objects
    public static ObservableList<SalesCSV> loadCSVData(String filePath) {
        ObservableList<SalesCSV> salesDataList = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read the headers line from the CSV file
            String headersLine = reader.readLine();

            // Process each line in the CSV file
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into an array of values
                String[] row = line.split(",");
                
                // Check if the row has exactly 5 values
                if (row.length == 5) {
                    // Create a SalesCSV object with the parsed data
                    SalesCSV sales = new SalesCSV(row[0], parseDateTime(row[1]), row[2], row[3], row[4]);

                    // Modify the date-time string in the row
                    String dateTimeString = row[1];
                    dateTimeString = dateTimeString.replace('T', ' ');
                    row[1] = dateTimeString;

                    // Add the SalesCSV object to the ObservableList
                    salesDataList.add(sales);
                } else {
                    // Handle the case where the row has a different number of values
                    System.err.println("Invalid row: " + line);
                }
            }
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }

        return salesDataList;
    }
}
   