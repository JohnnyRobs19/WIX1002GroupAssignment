package tableview;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import javafx.scene.control.TableView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author seren
 */
public class VehicleController implements Initializable {
    
    @FXML
    private TableColumn<VehicleCSV, String> AcquiredPriceColumn;

    @FXML
    private TableColumn<VehicleCSV, String> CarModelColumn;

    @FXML
    private TableColumn<VehicleCSV, String> CarPlateColumn;

    @FXML
    private TableColumn<VehicleCSV, String> CarStatusColumn;

    @FXML
    private TableColumn<VehicleCSV, String> SoldPriceColumn;

    @FXML
    private TableView<VehicleCSV> tableview;

    @FXML
    private TextField AcquiredPriceTextField;
    @FXML
    private TextField CarModelTextField;
    @FXML
    private TextField CarPlateTextField;
    @FXML
    private TextField SoldPriceTextField;
    @FXML
    private TextField CarStatusTextField;

    private ObservableList<VehicleCSV> VehicleDataList;

    // change Scene to TableView
    @FXML
    public void changeScreenButtonPushed2(ActionEvent event) throws IOException {
        Parent viewTableViewParent = FXMLLoader.load(getClass().getResource("TableView.fxml"));
        Scene viewTableViewScene = new Scene(viewTableViewParent);

        // This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(viewTableViewScene);
        window.show();
    }

    // when this method is called, it will change the Scene to Sales
    @FXML
    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent viewSalesParent = FXMLLoader.load(getClass().getResource("Sales.fxml"));
        Scene viewSalesScene = new Scene(viewSalesParent);

        // This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(viewSalesScene);
        window.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AcquiredPriceColumn != null : "fx:id=\"AcquiredPriceColumn\" was not injected: check your FXML file 'Vehicle.fxml'.";
        assert CarModelColumn != null : "fx:id=\"CarModelColumn\" was not injected: check your FXML file 'Vehicle.fxml'.";
        assert CarPlateColumn != null : "fx:id=\"CarPlateColumn\" was not injected: check your FXML file 'Vehicle.fxml'.";
        assert CarStatusColumn != null : "fx:id=\"CarStatusColumn\" was not injected: check your FXML file 'Vehicle.fxml'.";
        assert SoldPriceColumn != null : "fx:id=\"SoldPriceColumn\" was not injected: check your FXML file 'Vehicle.fxml'.";
        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'Vehicle.fxml'.";

        // Initialize your data list
        VehicleDataList = FXCollections.observableArrayList();

        try {
            // Call a method to load CSV data into TableView
            loadCSVData("C:\\Users\\seren\\Downloads\\vehicle.csv");
        } catch (IOException ex) {
            Logger.getLogger(VehicleController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Set the items of the TableView to your data list
        tableview.setItems(VehicleDataList);

        // Set cell value factories to display data in TableView columns
        CarPlateColumn.setCellValueFactory(cellData -> cellData.getValue().carPlateProperty());
        CarModelColumn.setCellValueFactory(cellData -> cellData.getValue().carModelProperty());
        AcquiredPriceColumn.setCellValueFactory(cellData -> cellData.getValue().acquirePriceProperty());
        CarStatusColumn.setCellValueFactory(cellData -> cellData.getValue().carStatusProperty());
        SoldPriceColumn.setCellValueFactory(cellData -> cellData.getValue().salesPriceProperty());
    }

    @FXML
    public void newPersonButtonPushed3() {
        VehicleCSV newVehicle = new VehicleCSV(
                CarPlateTextField.getText(),
                CarModelTextField.getText(),
                AcquiredPriceTextField.getText(),
                CarStatusTextField.getText(),
                SoldPriceTextField.getText()
        );

        tableview.getItems().add(newVehicle);

        clearTextFields();

        // Update the CSV file with the modified data
        updateCSVFile("C:\\Users\\seren\\Downloads\\vehicle.csv", newVehicle);
    }

    private void loadCSVData(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headersLine = reader.readLine(); // Assuming the first line contains headers

            // Read and add data to the ObservableList
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                // Allow null values if the row doesn't have enough elements
                VehicleCSV vehicle = new VehicleCSV(
                        (row.length > 0) ? row[0] : null,
                        (row.length > 1) ? row[1] : null,
                        (row.length > 2) ? row[2] : null,
                        (row.length > 3) ? row[3] : null,
                        (row.length > 4) ? row[4] : null
                );
                VehicleDataList.add(vehicle);

                // Debugging output
                System.out.println("Line: " + line);
                System.out.println("Row array: " + Arrays.toString(row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCSVFile(String filePath, VehicleCSV newVehicle) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Append the new data to the CSV file
            String newLine = String.join(",", newVehicle.getCarPlate(), newVehicle.getCarModel(), newVehicle.getAcquirePrice(), newVehicle.getCarStatus(), newVehicle.getSalesPrice());
            writer.write(newLine);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearTextFields() {
        CarPlateTextField.clear();
        CarModelTextField.clear();
        AcquiredPriceTextField.clear();
        CarStatusTextField.clear();
        SoldPriceTextField.clear();
    }
}

    



    
