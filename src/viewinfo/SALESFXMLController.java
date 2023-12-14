
package viewinfo;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/*

/**
 * FXML Controller class
 *
 * @author HP
 */
public class SALESFXMLController implements Initializable {
    public void initData()
    {
       
    }


 public void salesBackButtonPushed(ActionEvent event) throws IOException
    {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/viewinfo/MAINVIEW.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(mainViewScene);
        window.show();
    }
 public void salesEmployeePushed(ActionEvent event) throws IOException
    {
        Parent managementViewParent = FXMLLoader.load(getClass().getResource("EMPSALES.fxml"));
        Scene managementViewScene = new Scene(managementViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(managementViewScene);
        window.show();
    }
 public void salesCustomerPushed(ActionEvent event) throws IOException
    {
        Parent managementViewParent = FXMLLoader.load(getClass().getResource("/viewinfo/EMPCUSTOMER.fxml"));
        Scene managementViewScene = new Scene(managementViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(managementViewScene);
        window.show();
    }
 public void salesVehiclesPushed(ActionEvent event) throws IOException
    {
        Parent managementViewParent = FXMLLoader.load(getClass().getResource("/viewinfo/EMPVEHICLES.fxml"));
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
