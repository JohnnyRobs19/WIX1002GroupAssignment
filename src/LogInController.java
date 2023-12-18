import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
public class LogInController {

    @FXML
    private Button button_login;
    
    @FXML
    private Button button_signup;
    
    @FXML
    private TextField tf_username;
    
    @FXML
    private PasswordField tf_password;
    
    @FXML
    private Label label_wronglogin;
    
    public static void main(String[] args) {
        // Instantiating the CSVWriter Class could be done here
    }
}
    

