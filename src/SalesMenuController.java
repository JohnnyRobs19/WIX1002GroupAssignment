/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


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


public class SalesMenuController implements Initializable {
    
    
    
    
   // change Scene to TableView
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
    public void LogOutSalesClicked(ActionEvent event) throws IOException {
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
        Parent SALESFXML = FXMLLoader.load(getClass().getResource("viewinfo2/SALESFXML.fxml"));
        Scene SALESFXMLScene = new Scene(SALESFXML);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(SALESFXMLScene);
        window.show();
    } catch (IOException e) {
        e.printStackTrace();
        
    }
}

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
