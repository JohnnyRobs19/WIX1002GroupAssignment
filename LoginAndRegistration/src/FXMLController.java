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
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLController implements Initializable {

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
    

    private static final String CSV_FILE_PATH = "src//employee.csv";
    
    public FXMLController() {
        // Note: It's not recommended to initialize @FXML fields here
        // Instead, use the initialize method.
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic here, if needed
    }
    
    public static void main(String[] args) {
        //instatiating the CSVWriter Class
    }

@FXML
private void userSignUp(ActionEvent event) {
    String enteredEmployeeName = employeeName.getText();
    String enteredPassword = password.getText();
    String enteredPassword2 = password2.getText();

    if (!enteredPassword.equals(enteredPassword2)) {
        wrongSignUp.setText("Your credentials are not consistent");
        employeeName.clear();
        password.clear();
        password2.clear();
    } else {
        try {
            // Read the last employeeId from the existing CSV file
            int lastEmployeeId = getLastEmployeeId();

            // Calculate the new employeeId
            int newEmployeeId = lastEmployeeId + 1;

            // Append the new employee data to the CSV file
            appendEmployeeData(newEmployeeId, enteredEmployeeName, enteredPassword);

            // Clear the form fields
            employeeName.clear();
            password.clear();
            password2.clear();
            correctSignUp.setText("You have successfully signed up.");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    @FXML
    private void userClear(ActionEvent event) {
        employeeName.clear();
        password.clear();
        password2.clear();
        wrongSignUp.setText("");
    }
    
 private int getLastEmployeeId() throws IOException {
    try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
        List<String[]> lines = reader.readAll();
        // If the list is not empty, return the last employeeId
        return lines.isEmpty() ? 0 : Integer.parseInt(lines.get(lines.size() - 1)[0]);
    }
}

private void appendEmployeeData(int employeeId, String employeeName, String password) throws IOException {
    try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE_PATH, true))) {
        String[] data = {String.valueOf(employeeId), employeeName, "0", password};
        writer.writeNext(data);
    }
}
}