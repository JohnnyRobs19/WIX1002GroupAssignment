/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package viewinfo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author HP
 */
public class MAINVIEWController implements Initializable {




public void salesButtonPushed(ActionEvent event) throws IOException
    {
        Parent SALESFXML = FXMLLoader.load(getClass().getResource("/viewinfo/LOGINTEMP.fxml"));
        Scene SALESFXMLScene = new Scene(SALESFXML);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(SALESFXMLScene);
        window.show();

    }

public void managementButtonPushed(ActionEvent event) throws IOException
    {
        Parent MANAGEMENTFXML = FXMLLoader.load(getClass().getResource("/viewinfo/MANAGEMENTFXML.fxml"));
        Scene MANAGEMENTFXMLScene = new Scene(MANAGEMENTFXML);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(MANAGEMENTFXMLScene);
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
