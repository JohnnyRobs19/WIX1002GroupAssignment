/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Testing class serves as the main entry point for the LeCars Sales Management System application.
 * It extends the JavaFX Application class and provides the start method for initializing and displaying the GUI.
 *
 * @author zxsie
 */

// Place to run the interface
public class Testing extends Application {
    
    /**
     * The start method is called when the JavaFX application is launched.
     * It loads the FXML file, sets up the main stage, and displays the user interface.
     *
     * @param primaryStage The primary stage for the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file to create the UI layout
            Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
            
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
            // Handle exceptions that may occur during FXML file loading
            Logger.getLogger(Testing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The main method is the entry point for the Java application.
     * It launches the JavaFX application by calling the launch method.
     *
     * @param args The command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
