package importData2;

import com.opencsv.exceptions.CsvException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import java.io.IOException;

import javafx.stage.*;
import javafx.scene.*;
import viewinfo2.AppContext;

public class importSelectorController {

    @FXML
    private Button goToCustomerImport;

    @FXML
    private Button goToSalesImport;

    @FXML
    private Button goToEmployeeImport;

    @FXML
    private Button goToVehicleImport;

    @FXML
    private Button goToMain;

   

    private Stage stage;
    private Scene scene;
    private Parent root;
@FXML
public void backToMainButtonPushed(ActionEvent event) throws IOException, CsvException {
    try {
        // Determine the user's access level
        String accessLevel = AppContext.getAccessLevel(); // Assuming you have a method to get the access level

        String fxmlPath = null;

        // Determine which FXML page to load based on the access level
        if ("management".equals(accessLevel)) {
            fxmlPath = "/ManagementMenuFXML.fxml";
        } else if ("sales".equals(accessLevel)) {
            fxmlPath = "/SalesMenuFXML.fxml";
        } else {
            // Handle other access levels or scenarios as needed
        }

        // Load the appropriate FXML page
        if (fxmlPath != null) {
            Parent menuParent = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene menuScene = new Scene(menuParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(menuScene);
            window.show();
        }
    } catch (UnsupportedOperationException e) {
        // Handle the UnsupportedOperationException
        System.err.println("UnsupportedOperationException: " + e.getMessage());
        // You can log the exception or show an error message to the user
    }
}
    public void goToCustomerImportPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("customer.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToSalesImportPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sales.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToEmployeeImportPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("employee.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToVehicleImportPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vehicle.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

   
}
