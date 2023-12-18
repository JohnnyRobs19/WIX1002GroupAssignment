/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

/**
 *
 * @author Theshueraj
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {          
            // Load the FXML file to create the UI layout
            Parent root = FXMLLoader.load(getClass().getResource("LogInFXML.fxml"));
            
            // Create a scene using the UI layout
            Scene scene = new Scene(root);
            
            // Set the title of the main stage
            primaryStage.setTitle("LeCars Sales Management System");
            
            // Set the scene for the primary stage
            primaryStage.setScene(scene);
            
            // Disable window resizing (including maximizing)
            primaryStage.setResizable(false);
            
            // Show the primary stage
            primaryStage.show();
        } catch (IOException ex) {
                ex.printStackTrace();

        }
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
