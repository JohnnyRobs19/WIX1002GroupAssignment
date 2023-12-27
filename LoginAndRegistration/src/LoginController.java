import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

/**
 * FXML Controller class
 *
 * @author Theshueraj
 */
public class LoginController implements Initializable {
    
     private Stage stage;
    private Scene scene;
    private Parent root; 
    
    @FXML
    private Button button_login;
    @FXML
    private Label label_wronglogin;
    @FXML
    private Label label_wronglogin1;
    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField tf_password;
    @FXML
    private Button button_signup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization code
    }

    @FXML
    private void buttonLogin(ActionEvent event) {
        try {
            checkLogin();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
    
    public void switchToSignUp(ActionEvent event){
     try{ // Load Login_FXML.fxml instead of FXML.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML.fxml"));
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
@FXML
private void checkLogin() throws IOException, CsvException {
    String employeeId = tf_username.getText();
    String password = tf_password.getText();

    try (CSVReader csvReader = new CSVReader(new FileReader("src//employee.csv"))) {
        List<String[]> records = csvReader.readAll();

        // Use an array to store the result because it can be effectively final
        final boolean[] loginSuccessful = {false};
        final int[] accessLevel = {-1}; // -1 indicates not found

        for (String[] record : records) {
            if (record[0].equals(employeeId) && record[3].equals(password)) {
                loginSuccessful[0] = true;
                accessLevel[0] = Integer.parseInt(record[2]); // Assuming employeeStatus is at index 2
                break;
            }
        }

        Platform.runLater(() -> {
            if (loginSuccessful[0]) {
                // Now, you can use the accessLevel[0] variable to determine the access level
                if (accessLevel[0] == 1) {
                    // Management access level
                    label_wronglogin.setText("Management access level");
                    // You can call the method to change the scene for management access level here
                } else if (accessLevel[0] == 0) {
                    // Sales access level
                    label_wronglogin.setText("Sales access level");
                    // You can call the method to change the scene for sales access level here
                } else {
                    // Handle the case when access level is not found
                    label_wronglogin.setText("Access level not found");
                }
            } else {
                label_wronglogin.setText("Wrong employeeId or password");
            }
        });

    } catch (IOException e) {
        e.printStackTrace();
    }
}
}