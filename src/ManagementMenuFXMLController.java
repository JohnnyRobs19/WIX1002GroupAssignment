
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ManagementMenuFXMLController {
    
    
    @FXML
    private Button viewDataButton;

   
@FXML
    public void changeScreenButtonPushed2(ActionEvent event) throws IOException {
        Parent viewTableViewParent = FXMLLoader.load(getClass().getResource("tableview2/TableView.fxml"));
        Scene viewTableViewScene = new Scene(viewTableViewParent);

        // This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(viewTableViewScene);
        window.show();
    }
    
    @FXML
    public void importDataButtonPushed(ActionEvent event) throws IOException {
        Parent importSelector = FXMLLoader.load(getClass().getResource("importData2/importSelector.fxml"));
        Scene importSelectorScene = new Scene(importSelector);

        // This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(importSelectorScene);
        window.show();
    }
    
    
    @FXML
    public void salaryCalcButtonPushed(ActionEvent event) throws IOException {
        Parent salaryCalculation = FXMLLoader.load(getClass().getResource("importData2/salaryCalculation.fxml"));
        Scene salaryCalculationScene = new Scene(salaryCalculation);

        // This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(salaryCalculationScene);
        window.show();
    }
  
    @FXML
    public void LogOutClicked(ActionEvent event) throws IOException {
        Parent viewTableViewParent = FXMLLoader.load(getClass().getResource("Login_FXML.fxml"));
        Scene viewTableViewScene = new Scene(viewTableViewParent);

        // This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(viewTableViewScene);
        window.show();
    }
    
  @FXML
public void viewButtonClicked(ActionEvent event) {
     // Add this line
    try {
        Parent MANAGEMENTFXML = FXMLLoader.load(getClass().getResource("viewinfo2/MANAGEMENTFXML.fxml"));
        Scene MANAGEMENTFXMLScene = new Scene(MANAGEMENTFXML);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(MANAGEMENTFXMLScene);
        window.show();
    } catch (IOException e) {
        e.printStackTrace();
        
    }
}
}

