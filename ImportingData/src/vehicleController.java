import com.opencsv.exceptions.CsvException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import com.opencsv.*;
import javafx.scene.control.TableView;


public class vehicleController implements Initializable {
    
    @FXML
    private AnchorPane baseAnchorPane;

    @FXML
    private Button importButton;

    @FXML
    private Button chooseFileButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<Vehicle> tableView;

    @FXML
    private TableColumn<Vehicle, String> PlateNumber;

    @FXML
    private TableColumn<Vehicle, String> CarModel;

    @FXML
    private TableColumn<Vehicle, String> AcquirePrice;

    @FXML
    private TableColumn<Vehicle, String> VehicleStatus;

    @FXML
    private TableColumn<Vehicle, String> SalesPrice;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private File selectedFile;

    public void initialize(URL location, ResourceBundle resources) {
        // Set PropertyValueFactory for each TableColumn
        PlateNumber.setCellValueFactory(new PropertyValueFactory<>("carPlate"));
        CarModel.setCellValueFactory(new PropertyValueFactory<>("carModel"));
        AcquirePrice.setCellValueFactory(new PropertyValueFactory<>("acquirePrice"));
        VehicleStatus.setCellValueFactory(new PropertyValueFactory<>("carStatus"));
        SalesPrice.setCellValueFactory(new PropertyValueFactory<>("salesPrice"));
    }

    public void chooseFileButtonPressed(ActionEvent event) throws CsvException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select CSV File");
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            loadCSVData();
        }
    }

    public void importFileButtonPressed(ActionEvent event) throws IOException {

        if (selectedFile == null) {
            showErrorMessage("There is no file selected.");
            return;
        }
        File originalFile = new File(selectedFile.getPath());
        File vehicleBackup = new File("backups/vehicleBackup.csv");

        if (vehicleBackup.exists()) {
            // Display confirmation alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("File Already Exists");
            alert.setHeaderText("Confirmation");
            alert.setContentText("The file already exists. Do you want to overwrite it? (Make sure to make a backup first.)");

            // Customizing the alert buttons
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);

            // Show and wait for user's choice
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("2833637.png")); // Add your application icon

            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(noButton) == yesButton) {
                // User clicked "Yes," proceed with file copy
                Files.copy(originalFile.toPath(), vehicleBackup.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } else {
                // User clicked "No" or closed the dialog, do nothing or handle accordingly
            }
        } else {
            // File does not exist, proceed with file copy
            Files.copy(originalFile.toPath(), vehicleBackup.toPath());
            showSuccessMessage("File successfully imported.");
        }

    }

    public void backButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("importSelector.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showSuccessMessage(String message) {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText(null);
        successAlert.setContentText(message);

        // Show the alert
        successAlert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);

        // Show the alert
        errorAlert.showAndWait();
    }

    public static String[] readFirstRowCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            if ((line = br.readLine()) != null) {
                return line.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return null; // Return null if there was an issue reading the file or if it's empty
    }
    
    private void loadCSVData() throws CsvException {
        // Specify your CSV file path
        String csvFilePath = selectedFile.getPath();

        String[] firstRow = readFirstRowCSV(selectedFile.getPath());
        if(!firstRow[0].equals("carPlate")) {
            showErrorMessage("Invalid file type.");
            return;
        }

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            // Read all records at once
            List<String[]> allData = csvReader.readAll();

            // Assume the first row contains column headers
            String[] headers = allData.get(0);

            // Create ObservableList for TableView
            ObservableList<Vehicle> data = FXCollections.observableArrayList();

            // Start from index 1 to skip headers
            for (int i = 1; i < allData.size(); i++) {
                String[] row = allData.get(i);

                // Create an instance of Customer and populate its properties
                Vehicle vehiclesData = new Vehicle();

                // Assuming the order of columns is customerId, customerName, phoneNumber, postCode
                vehiclesData.setCarPlate(row[0]);
                vehiclesData.setCarModel(row[1]);
                vehiclesData.setAcquirePrice(row[2]);
                vehiclesData.setCarStatus(row[3]);
                vehiclesData.setSalesPrice(row[4]);
                // Add the populated data to the ObservableList
                data.add(vehiclesData);
            }

            // Set the data to the TableView
            tableView.setItems(data);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., file not found, CSV parsing errors)
        }
    }
    
}
