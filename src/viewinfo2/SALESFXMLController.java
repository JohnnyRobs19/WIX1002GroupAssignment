package viewinfo2;

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
    
    public interface EmployeeIdSetter {
    void setEmployeeId(String employeeId);
}

    public void salesBackButtonPushed(ActionEvent event) throws IOException, CsvException {
        navigateToScene("/SalesMenuFXML.fxml", event);
    }

    @FXML
    public void salesEmployeePushed(ActionEvent event) throws IOException, CsvException {
        navigateToScene("/viewinfo2/EMPSALES.fxml", event);
    }

    @FXML
    public void salesCustomerPushed(ActionEvent event) throws IOException, CsvException {
        navigateToScene("/viewinfo2/EMPCUSTOMER.fxml", event);
    }

    @FXML
    public void salesVehiclesPushed(ActionEvent event) throws IOException, CsvException {
        navigateToScene("/viewinfo2/EMPVEHICLES.fxml", event);
    }

   private void navigateToScene(String scenePath, ActionEvent event) throws IOException, CsvException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
        Parent managementViewParent = loader.load();

        // Get the controller associated with the loaded FXML
        Initializable controller = loader.getController();

        if (controller instanceof EmployeeIdSetter) {
            // Check if the controller implements EmployeeIdSetter interface
            AppContext appContext = new AppContext(); // Create an instance of AppContext
            ((EmployeeIdSetter) controller).setEmployeeId(appContext.getLoggedInUserId());
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

