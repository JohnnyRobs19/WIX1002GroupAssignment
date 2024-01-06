/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package viewinfo2;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
public class EMPLOYEEController implements Initializable {
    @FXML
    private TableView<employee> employeeTableView;

    @FXML
    private TableColumn<employee, String> employeeID;

    @FXML
    private TableColumn<employee, String> employeeName;

    @FXML
    private TableColumn<employee, String> employeeStatus;

    @FXML
    private TableColumn<employee, String> employeePassword;

    @FXML
    private TextField employeeIdFilterField;

    @FXML
    private TextField employeeNameFilterField;

  @FXML
    private ChoiceBox<String> employeeStatusFilterField;

    @FXML
    private TextField employeePasswordFilterField;

    // Original data loaded from CSV
    private ObservableList<employee> originalData;

    public void employeeBackButtonPushed(ActionEvent event) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("MANAGEMENTFXML.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainViewScene);
        window.show();
    }

   @Override
public void initialize(URL url, ResourceBundle rb) {


    
    employeeID.setCellValueFactory(new PropertyValueFactory<employee, String>("employeeID"));
    employeeName.setCellValueFactory(new PropertyValueFactory<employee, String>("employeeName"));
  employeeStatusFilterField.getItems().addAll("SALES", "MANAGEMENT");

    // Set the default value or prompt text for the choice box

    // Modify the cellValueFactory for employeeStatus column
    employeeStatus.setCellValueFactory(cellData -> {
        SimpleStringProperty property = new SimpleStringProperty();
        String status = cellData.getValue().getEmployeeStatus();
        property.setValue(status.equals("0") ? "SALES" : (status.equals("1") ? "MANAGEMENT" : "UNKNOWN"));
        return property;
        
        
    });

  
    try {
        loadCSVData();
    } catch (CsvException ex) {
        Logger.getLogger(EMPLOYEEController.class.getName()).log(Level.SEVERE, null, ex);
    }

    // Save the original data loaded from CSV
    originalData = FXCollections.observableArrayList(employeeTableView.getItems());
}


    private void loadCSVData() throws CsvException {
        // Specify your CSV file path
        String csvFilePath = "src\\backups\\employeeBackup.csv";

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

                // Create an instance of Employee and populate its properties
                employee employeeData = new employee();

                // Assuming the order of columns is employeeID, employeeName, employeeStatus, employeePassword
                employeeData.setEmployeeID(row[0]);
                employeeData.setEmployeeName(row[1]);
                employeeData.setEmployeeStatus(row[2]);
           
                // Add the populated data to the ObservableList
                data.add(employeeData);
            }

            // Set the data to the TableView
            employeeTableView.setItems(data);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., file not found, CSV parsing errors)
        }
    }


@FXML
private void searchButtonPushed(ActionEvent event) {
    // Get the filter values from text fields
    String filterEmployeeId = employeeIdFilterField.getText().trim().toLowerCase();
    String filterEmployeeName = employeeNameFilterField.getText().trim().toLowerCase();
    String inputEmployeeStatus = employeeStatusFilterField.getValue();
    
    // Map user-input values to internal values
    final String mappedEmployeeStatus = mapStatusInputToInternalValue(inputEmployeeStatus);

    // Create a filtered list based on the filter values
    ObservableList<employee> filteredData = FXCollections.observableArrayList();

    for (employee emp : originalData) {
        boolean idMatch = emp.getEmployeeID() != null && emp.getEmployeeID().toLowerCase().contains(filterEmployeeId);
        boolean nameMatch = emp.getEmployeeName() != null && emp.getEmployeeName().toLowerCase().contains(filterEmployeeName);
        boolean statusMatch = (mappedEmployeeStatus == null || mappedEmployeeStatus.isEmpty())
                || (emp.getEmployeeStatus() != null && emp.getEmployeeStatus().toLowerCase().contains(mappedEmployeeStatus));
       
        if (idMatch && nameMatch && statusMatch ) {
            filteredData.add(emp);
        }
    }

    // Set the filtered data to the TableView
    employeeTableView.setItems(filteredData);

    // Check if the filtered data is empty
    if (filteredData.isEmpty()) {
        // If no employees found, display a message
        showNoEmployeesFoundLabel();
    } else {
        // If there are results, hide the message label
        hideNoEmployeesFoundLabel();
    }
}

private void showNoEmployeesFoundLabel() {
    Label noEmployeesFoundLabel = new Label("NO EMPLOYEES FOUND");
    // Customize the label appearance if needed
    // noEmployeesFoundLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14;");
    
    // Set the label as the placeholder
    employeeTableView.setPlaceholder(noEmployeesFoundLabel);
}

private void hideNoEmployeesFoundLabel() {
    // Reset the placeholder (clear the "NO EMPLOYEES FOUND" message)
    employeeTableView.setPlaceholder(null);
}




private String mapStatusInputToInternalValue(String statusInput) {
    // Map user-input values to internal values
    if ("SALES".equalsIgnoreCase(statusInput)) {
        return "0";
    } else if ("MANAGEMENT".equalsIgnoreCase(statusInput)) {
        return "1";
    }
    // Add more mappings if needed
    return statusInput;
}
@FXML
private void resetButtonPushed(ActionEvent event) {
    // Reset the TableView to its original state
    employeeTableView.setItems(originalData);

    // Clear the filter text fields
    employeeIdFilterField.clear();
    employeeNameFilterField.clear();
    
    // Set the employeeStatusFilterField value to null or an empty string
    employeeStatusFilterField.setValue(null);
   // or employeeStatusFilterField.setValue("");
}}
