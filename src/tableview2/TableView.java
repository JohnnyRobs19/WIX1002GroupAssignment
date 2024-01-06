package tableview2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


/**
 *
 * @author seren
 */
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import static javafx.application.Application.launch;

public class TableView extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Load the FXML file using an absolute path
               Parent root = FXMLLoader.load(getClass().getResource("TableView.fxml"));
            
            // Create a scene using the UI layout
            Scene scene = new Scene(root);
            
            // Set up the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

