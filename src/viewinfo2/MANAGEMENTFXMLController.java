package viewinfo2;

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
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MANAGEMENTFXMLController implements Initializable {


    @FXML
 public void managementBackButtonPushed(ActionEvent event) throws IOException
    {
        Parent ManagementMenuFXML = FXMLLoader.load(getClass().getResource("/ManagementMenuFXML.fxml"));

        Scene ManagementMenuFXMLScene = new Scene(ManagementMenuFXML);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(ManagementMenuFXMLScene);
        window.show();
    }
    @FXML
 public void managementCustomerPushed(ActionEvent event) throws IOException
    {
        Parent managementViewParent = FXMLLoader.load(getClass().getResource("/viewinfo2/CUSTOMER.fxml"));
        Scene managementViewScene = new Scene(managementViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(managementViewScene);
        window.show();
    }
    @FXML
 public void managementVehiclePushed(ActionEvent event) throws IOException
    {
        Parent managementViewParent = FXMLLoader.load(getClass().getResource("/viewinfo2/VEHICLES.fxml"));
        Scene managementViewScene = new Scene(managementViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(managementViewScene);
        window.show();
    }
 
    @FXML
 public void managementEmployeePushed(ActionEvent event) throws IOException
    {
        Parent managementViewParent = FXMLLoader.load(getClass().getResource("/viewinfo2/EMPLOYEE.fxml"));
        Scene managementViewScene = new Scene(managementViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(managementViewScene);
        window.show();
    }
    @FXML
 public void managementSalesPushed(ActionEvent event) throws IOException
    {
        Parent managementViewParent = FXMLLoader.load(getClass().getResource("/viewinfo2/SALES.fxml"));
        Scene managementViewScene = new Scene(managementViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
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
