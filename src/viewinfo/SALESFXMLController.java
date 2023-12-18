package viewinfo;

import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * 
 * @author HP
 */
public class SALESFXMLController implements Initializable {
    
    private static String selectedEmployeeId;

    public void setEmployeeId(String employeeId) {
        this.selectedEmployeeId = employeeId;
        // Load data when the employee ID is set
    }

    @FXML
    public void salesBackButtonPushed(ActionEvent event) throws IOException, CsvException {
        navigateToScene("/viewinfo/LOGINTEMP.fxml",event);
    }

    @FXML
public void salesEmployeePushed(ActionEvent event) throws IOException, CsvException {
    navigateToScene("/viewinfo/EMPSALES.fxml", event);
}

    @FXML
    public void salesCustomerPushed(ActionEvent event) throws IOException, CsvException {
        navigateToScene("/viewinfo/EMPCUSTOMER.fxml",event);
    }

    @FXML
    public void salesVehiclesPushed(ActionEvent event) throws IOException, CsvException {
        navigateToScene("/viewinfo/EMPVEHICLES.fxml",event);
    }

    private void navigateToScene(String scenePath, ActionEvent event) throws IOException, CsvException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
        Parent managementViewParent = loader.load();

        // Get the controller associated with the loaded FXML
        Initializable controller = loader.getController();

        if (controller instanceof EMPSALESController) {
            ((EMPSALESController) controller).setEmployeeId(selectedEmployeeId);
        } else if (controller instanceof EMPCUSTOMERController) {
            ((EMPCUSTOMERController) controller).setEmployeeId(selectedEmployeeId);
        }

    
    Scene managementViewScene = new Scene(managementViewParent);

    // This line gets the Stage information
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

    window.setScene(managementViewScene);
    window.show();
}


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }
}

