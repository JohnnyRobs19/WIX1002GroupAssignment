import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import viewinfo2.AppContext;

public class LoginController {

    @FXML
    private Button button_login;

    @FXML
    private Label label_wronglogin;

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    private Stage stage;

    @FXML
    private void buttonLogin(ActionEvent event) {
        checkLogin(event);
    }

    @FXML
    private void switchToSignUp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML.fxml"));
            Parent root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  @FXML
private void checkLogin(ActionEvent event) {
    final String employeeId = tf_username.getText();
    final String password = tf_password.getText();

    try (CSVReader csvReader = new CSVReader(new FileReader("src//backups//employee.csv"))) {
        List<String[]> records = csvReader.readAll();

        boolean loginSuccessful = false;
        int accessLevel = -1; // -1 indicates not found

        for (String[] record : records) {
            if (record[0].equals(employeeId) && record[3].equals(password)) {
                loginSuccessful = true;
                accessLevel = Integer.parseInt(record[2]); // Assuming employeeStatus is at index 2
                break;
            }
        }

        if (loginSuccessful) {
            AppContext.getInstance().setLoggedInUserId(employeeId);
AppContext.getInstance().setLoggedInUserId(employeeId);
     AppContext.getInstance().setAccessLevel(getAccessLevelString(accessLevel));

            // Ensure that all variables used inside the lambda are effectively final
            final int finalAccessLevel = accessLevel;

            Platform.runLater(() -> navigateToMainMenu(finalAccessLevel, event));
        } else {
            Platform.runLater(() -> label_wronglogin.setText("Wrong employeeId or password"));
        }
    } catch (IOException | CsvException | NumberFormatException e) {
        e.printStackTrace(); // Handle exceptions appropriately (e.g., log or display a user-friendly message)
    }
}
private String getAccessLevelString(int accessLevel) {
    // Your logic to map access levels to strings
    // For example, you might return "management" for access level 1 and "sales" for access level 0
    if (accessLevel == 1) {
        return "management";
    } else if (accessLevel == 0) {
        return "sales";
    } else {
        // Handle other access levels as needed
        return "unknown";
    }
}
private void navigateToMainMenu(int accessLevel, ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader();

        Parent root;
        if (accessLevel == AccessLevels.MANAGEMENT.getLevel()) {
            loader.setLocation(getClass().getResource("ManagementMenuFXML.fxml"));
        } else if (accessLevel == AccessLevels.SALES.getLevel()) {
            loader.setLocation(getClass().getResource("SalesMenuFXML.fxml"));
        } else {
            label_wronglogin.setText("Access level not found");
            return;
        }

        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace(); // Handle exceptions appropriately (e.g., log or display a user-friendly message)
    }
}

// Define constants or an enum for access levels
private enum AccessLevels {
    MANAGEMENT(1),
    SALES(0);

    private final int level;

    AccessLevels(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
}