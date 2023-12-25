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

        for (String[] record : records) {
            if (record[0].equals(employeeId) && record[3].equals(password)) {
                loginSuccessful[0] = true;
                break;
            }
        }

        Platform.runLater(() -> {
            if (loginSuccessful[0]) {
                label_wronglogin.setText("Success!");
                // You can call the method to change the scene here if the login is successful
            } else {
                label_wronglogin.setText("Wrong username or password!");
            }
        });

    } catch (IOException e) {
        e.printStackTrace();
    }
}
}

    

