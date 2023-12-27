package tableview;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CustomerCSV {

    private static int nextCustomerID = 1;

    private final StringProperty customerID;
    private final StringProperty customerName;
    private final StringProperty phone;
    private final StringProperty postcode;

    public CustomerCSV(String customerID, String customerName, String phone, String postcode) {
        this.customerID = new SimpleStringProperty(customerID);
        this.customerName = new SimpleStringProperty(customerName);
        this.phone = new SimpleStringProperty(phone);
        this.postcode = new SimpleStringProperty(postcode);

        // Update nextCustomerID based on the current customerID
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

    public String getCustomerID() {
        return customerID.get();
    }

    public StringProperty customerIDProperty() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getPostcode() {
        return postcode.get();
    }

    public StringProperty postcodeProperty() {
        return postcode;
    }

    public void updateCustomerID(String newCustomerID) {
        customerID.set(newCustomerID);
    }

    public static String getNextCustomerID() {
        return "C0" + nextCustomerID;
    }

    public static ObservableList<CustomerCSV> loadCSVData(String filePath) {
        ObservableList<CustomerCSV> customerDataList = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headersLine = reader.readLine(); // Assuming the first line contains headers

            // Read and add data to the ObservableList
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length == 4) { // Validate row length
                    CustomerCSV customer = new CustomerCSV(row[0], row[1], row[2], row[3]);
                    customerDataList.add(customer);
                } else {
                    System.err.println("Invalid row: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return customerDataList;
    }
}
    

