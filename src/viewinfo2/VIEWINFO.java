/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package viewinfo2;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 *
 * @author HP
 */
public class VIEWINFO extends Application {
    
    @Override
    public void start(Stage Stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MAINVIEW.fxml"));
            
            Scene scene = new Scene(root);
            
            Stage.setTitle("CMS");
            Stage.setScene(scene);
            Stage.show();
        } catch (IOException ex) {
            Logger.getLogger(VIEWINFO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}