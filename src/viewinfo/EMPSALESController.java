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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
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

    
    public void empSalesBackButtonPushed(ActionEvent event) throws IOException
    {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("SALESFXML.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(mainViewScene);
        window.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         salesId.setCellValueFactory(new PropertyValueFactory<sales,String>("salesId"));
       dateAndTime.setCellValueFactory(new PropertyValueFactory<sales,String>("dateAndTime"));
        carPlate.setCellValueFactory(new PropertyValueFactory<sales,String>("carPlate"));
        customerId.setCellValueFactory(new PropertyValueFactory<sales,String>("customerId"));
       
        try {
            loadCSVData();
        } catch (CsvException ex) {
            Logger.getLogger(SALESController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   private void loadCSVData() throws CsvException {
        // Specify your CSV file path
        String csvFilePath = "C:\\Users\\HP\\Documents\\WIIX1002GROUPRAGA\\VIEWINFO\\src\\sales.csv";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            // Read all records at once
            List<String[]> allData = csvReader.readAll();

            // Assume the first row contains column headers
            String[] headers = allData.get(0);

            // Create ObservableList for TableView
            ObservableList<sales> data = FXCollections.observableArrayList();

            // Start from index 1 to skip headers
            for (int i = 1; i < allData.size(); i++) {
                String[] row = allData.get(i);

                // Create an instance of Customer and populate its properties
                sales salesData = new sales();

                // Assuming the order of columns is customerId, customerName, phoneNumber, postCode
                salesData.setSalesId(row[0]);
                salesData.setDateAndTime(row[1]);
                salesData.setCarPlate(row[2]);
                salesData.setCustomerId(row[3]);
                salesData.setEmployeeId(row[4]);
                // Add the populated data to the ObservableList
                data.add(salesData);
            }

            // Set the data to the TableView
            empSalesTable.setItems(data);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., file not found, CSV parsing errors)
        } 
    
    }}
