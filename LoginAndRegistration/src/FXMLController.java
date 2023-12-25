import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root; 
    
    @FXML
    private TextField employeeName;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField password2;
    @FXML
    private Label wrongSignUp;
    @FXML
    private Label correctSignUp;
    @FXML
    private Label MessageEmployeeId;

    // File path for the CSV file
    private static final String CSV_FILE_PATH = "src//employee.csv";

    // Default constructor
    public FXMLController() {
        // Note: It's not recommended to initialize @FXML fields here
        // Instead, use the initialize method.
    }

    // Initialization logic for the controller
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic here, if needed
    }

    // Main method (not used in this context)
    public static void main(String[] args) {
        // Instantiating the CSVWriter Class could be done here
    }
    
//    public void switchToSignUp(ActionEvent event) throws IOException{
//        root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
    
    public void switchToLogin(ActionEvent event){
     try{ // Load Login_FXML.fxml instead of FXML.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login_FXML.fxml"));
        root = loader.load();
        //wrongSignUp.setText("Are you working.");

        // Rest of your code for switching scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } catch (IOException e){
        e.printStackTrace();
    }
    }

    // Event handler for the Sign Up button
    @FXML
    private void userSignUp(ActionEvent event) {
        String enteredEmployeeName = employeeName.getText();
        String enteredPassword = password.getText();
        String enteredPassword2 = password2.getText();

        try {
            // Check if the entered employeeName already exists in the CSV file
            if (isEmployeeNameDuplicate(enteredEmployeeName)) {
                wrongSignUp.setText("You have already signed up.");
                clearFormFields();
            // If Password and Re-enter Password Field are not the same
            } else if (!enteredPassword.equals(enteredPassword2)) {
                wrongSignUp.setText("Your credentials are not consistent");
                clearFormFields();
            }
            else {
                // Read the last employeeId from the existing CSV file
                int lastEmployeeId = getLastEmployeeId();

                // Calculate the new employeeId
                int newEmployeeId = lastEmployeeId + 1;

                // Append the new employee data to the CSV file
                appendEmployeeData(newEmployeeId, enteredEmployeeName, enteredPassword);

                // Clear the form fields
                clearFormFields();
                correctSignUp.setText("You have successfully signed up.");
                MessageEmployeeId.setText("Your employee Id is E"+String.format("%04d", (newEmployeeId)));
                wrongSignUp.setText("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Event handler for the Clear button
    @FXML
    private void userClear(ActionEvent event) {
        clearFormFields();
        wrongSignUp.setText("");
    }

    // Function to determine the last EmployeeId from the CSV file
    private int getLastEmployeeId() throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            List<String[]> lines = reader.readAll();

            // If the list is not empty, find and return the correct numeric part of the last employeeId
            if (!lines.isEmpty()) {
                for (int i = lines.size() - 1; i >= 0; i--) {
                    String lastEmployeeId = lines.get(i)[0];
                    if (lastEmployeeId.startsWith("E")) {
                        return extractNumericId(lastEmployeeId);
                    }
                }
            }

            // If the list is empty or no valid employeeId is found, return 0
            return 0;
        }
    }

    // Function to extract the numeric part of EmployeeId (format: E0001)
    private int extractNumericId(String employeeId) {
        // Extract and return the numeric part of the employeeId
        return Integer.parseInt(employeeId.substring(1));
    }

    // Function to append Employee data to the CSV file
    private void appendEmployeeData(int lastEmployeeId, String employeeName, String password) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE_PATH, true))) {
            // Generate the new employeeId by incrementing the last four digits
            String newEmployeeId = "E" + String.format("%04d", (lastEmployeeId));

            // Create an array with employee data
            String[] data = {newEmployeeId, employeeName, "0", password};

            // Write data to the CSV file
            writer.writeNext(data);
        }
    }

    // Function to check if the entered EmployeeName already exists in the CSV file
    private boolean isEmployeeNameDuplicate(String enteredEmployeeName) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            List<String[]> lines = reader.readAll();

            for (String[] line : lines) {
                String existingEmployeeName = line[1]; // Assuming employeeName is at index 1
                if (enteredEmployeeName.trim().equalsIgnoreCase(existingEmployeeName.trim())) {
                    return true; // Duplicate found
                }
            }

            return false; // No duplicate found
        }
    }

    // Helper method to clear form fields
    private void clearFormFields() {
        employeeName.clear();
        password.clear();
        password2.clear();
    }
}
