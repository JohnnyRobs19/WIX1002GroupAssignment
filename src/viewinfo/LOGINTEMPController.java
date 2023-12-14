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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class LOGINTEMPController implements Initializable {
    
    @FXML
    private TableView<employee> employeeIdPick;

    @FXML
    private TableColumn<employee, String> employeeId;

    @FXML
    private Button empDashboard;
    
    public void empDashboardPushed(ActionEvent event) throws IOException
    {
         FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("EMPSALES.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        
        
        EMPSALESController controller = loader.getController();
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();

    }
     public void userClickedOnTable(){
  
  this.empDashboard.setDisable(false);  
  }
public void empBackPushed(ActionEvent event) throws IOException
    {
        Parent SALESFXML = FXMLLoader.load(getClass().getResource("/viewinfo/MAINVIEW.fxml"));
        Scene SALESFXMLScene = new Scene(SALESFXML);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(SALESFXMLScene);
        window.show();

    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         employeeId.setCellValueFactory(new PropertyValueFactory<employee,String>("employeeID"));
       this.empDashboard.setDisable(true);
        try {
            loadCSVData();
        } catch (CsvException ex) {
            Logger.getLogger(EMPLOYEEController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
    }    
private void loadCSVData() throws CsvException {
        // Specify your CSV file path
        String csvFilePath = "C:\\Users\\HP\\Documents\\WIIX1002GROUPRAGA\\VIEWINFO\\src\\employee.csv";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            // Read all records at once
            List<String[]> allData = csvReader.readAll();

            // Assume the first row contains column headers
            String[] headers = allData.get(0);

            // Create ObservableList for TableView
            ObservableList<employee> data = FXCollections.observableArrayList();

            // Start from index 1 to skip headers
            for (int i = 1; i < allData.size(); i++) {
                String[] row = allData.get(i);

                // Create an instance of Customer and populate its properties
                employee employeeData = new employee();

                // Assuming the order of columns is customerId, customerName, phoneNumber, postCode
                employeeData.setEmployeeID(row[0]);
                
                
                // Add the populated data to the ObservableList
                data.add(employeeData);
            }

            // Set the data to the TableView
            employeeIdPick.setItems(data);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., file not found, CSV parsing errors)
        }}    
}
