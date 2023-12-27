package tableview;

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

public class TableView extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Load the FXML file using an absolute path
            Parent root = FXMLLoader.load(new File("C:\\Users\\seren\\OneDrive\\Documents\\NetBeansProjects\\TableView\\src\\tableview\\TableView.fxml").toURI().toURL());

            // Set up the scene
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

