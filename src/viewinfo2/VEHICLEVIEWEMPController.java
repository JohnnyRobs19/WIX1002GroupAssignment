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
public class VEHICLEVIEWEMPController implements Initializable {
    @FXML
    private TableView<vehicles> vehicleDetailedView;

    @FXML
    private TableColumn<vehicles, String> carPlate;

    @FXML
    private TableColumn<vehicles, String> carModel;

    @FXML
    private TableColumn<vehicles, String> acquiredPrice;
    @FXML
    private TableColumn<vehicles, String> carStatus;

    @FXML
    private TableColumn<vehicles, String> salesPrice;

    @FXML
    private Button backButton;

    private String selectedCarPlate; // Remove static

    public void setCarPlate(String carPlate) {
        this.selectedCarPlate = carPlate;
        

        // Load data when the employee ID is set
        loadData();
    }

    @FXML
    public void backButtonPushed(ActionEvent event) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/viewinfo2/EMPSALES.fxml"));

        Scene mainViewScene = new Scene(mainViewParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainViewScene);
        window.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carPlate.setCellValueFactory(new PropertyValueFactory<vehicles,String>("carPlate"));
        carModel.setCellValueFactory(new PropertyValueFactory<vehicles,String>("carModel"));
        acquiredPrice.setCellValueFactory(new PropertyValueFactory<vehicles,String>("acquiredPrice"));
        carStatus.setCellValueFactory(new PropertyValueFactory<vehicles,String>("carStatus"));
        salesPrice.setCellValueFactory(new PropertyValueFactory<vehicles,String>("salesPrice"));
    }

    private void loadData() {
        // Specify your CSV file path
        String csvFilePath = "src\\backups\\vehicleBackup.csv";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            // Read all records at once
            List<String[]> allData = csvReader.readAll();

            // Assume the first row contains column headers
            String[] headers = allData.get(0);

            // Create ObservableList for TableView
            ObservableList<vehicles> data = FXCollections.observableArrayList();

            // Start from index 1 to skip headers
            for (int i = 1; i < allData.size(); i++) {
                String[] row = allData.get(i);

                // Create an instance of Customer and populate its properties
                vehicles vehiclesData = new vehicles();

                // Assuming the order of columns is carPlate, carModel, acquiredPrice, carStatus, salesPrice
                vehiclesData.setCarPlate(row[0]);
                vehiclesData.setCarModel(row[1]);
                vehiclesData.setAcquiredPrice(row[2]);
                vehiclesData.setCarStatus(row[3]);
                vehiclesData.setSalesPrice(row[4]);
                // Add the populated data to the ObservableList
                data.add(vehiclesData);
            }

            ObservableList<vehicles> filteredData = data.filtered(vehicles -> vehicles.getCarPlate().equals(selectedCarPlate));

            // Set the filtered data to the TableView
            vehicleDetailedView.setItems(filteredData);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}
