import com.opencsv.exceptions.CsvException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.*;
import javafx.stage.Stage;

public class salaryCalculationController {
    
    @FXML
    private TextField employeeIDTextField;

    @FXML
    private Button calculateButton;

    @FXML
    private Button backButton;

    @FXML
    private Label employeeRank;

    @FXML
    private Label baseSalary;

    @FXML
    private Label totalCommisions;

    @FXML
    private Label baseAllowance;

    @FXML
    private Label totalSalary;

    private Stage stage;
    private Scene scene;
    private Parent root;

    static String salesFile = "backups/salesBackup.csv";
    static String vehicleFile = "backups/vehicleBackup.csv";
    static String employeeFile = "backups/employeeBackup.csv";

    public void calculateButtonPressed(javafx.event.ActionEvent event) {

        try {
            String employeeID = employeeIDTextField.getText();


            employeeRank.setText(getEmployeeRank(employeeID));
            baseSalary.setText(Double.toString(calculateBaseSalary(employeeID)));
            totalCommisions.setText(Double.toString(calculateTotalCommisions(employeeID)));
            baseAllowance.setText(Double.toString(calculateBaseAllowance(employeeID)));
            totalSalary.setText(Double.toString(calculateTotalSalary(employeeID)));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void backButtonPressed(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("importSelector.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showErrorMessage(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);

        // Show the alert
        errorAlert.showAndWait();
    }

    private String getEmployeeRank(String employeeID) throws Exception{
        int employeeStatus = getEmployeeStatus(employeeID, employeeFile);
        String employeeRank;

        if (employeeStatus == 0) {
            employeeRank = "Sales Employee";
        } else if (employeeStatus == 1) {
            employeeRank = "Manager";
        } else {
            employeeRank = null;
        }
        return employeeRank;
    }

    private double calculateBaseSalary(String employeeID) {
        int baseSalary;
        int status = getEmployeeStatus(employeeID, employeeFile);

        if (status == 0) {
            baseSalary = 1200;
        } else if (status == 1) {
            baseSalary = 2200;
        } else {
            baseSalary = Integer.parseInt(null);
        }
        return baseSalary;
    }

    private double calculateTotalCommisions(String employeeID) {
        List<Sales> salesInfo = readSalesCSV(salesFile);
        List<Vehicle> vehicleInfo = readVehicleCSV(vehicleFile);

        double commision = calculateCommission(employeeID, salesInfo, vehicleInfo);
        return commision;
    }

    private double calculateBaseAllowance(String employeeID) {
        int status = getEmployeeStatus(employeeID, employeeFile);
        double baseAllowance;

        if (status == 0) {
            baseAllowance = 250;
        } else if (status == 1) {
            baseAllowance = 350;
        } else {
            baseAllowance = Integer.parseInt(null);
        }
        return baseAllowance;
    }

    private double calculateTotalSalary(String employeeID) {
        double baseSalary = calculateBaseSalary(employeeID);
        double commisions = calculateTotalCommisions(employeeID);
        double baseAllowance = calculateBaseAllowance(employeeID);

        double totalSalary = baseSalary + commisions + baseAllowance;
        return totalSalary;
    }
    
    private static double calculateCommission(String employeeId, List<Sales> sales, List<Vehicle> vehicles) {
        double commissionRate = 0.01;
        double totalCommission = 0;

        for (Sales sale : sales) {
            if (employeeId.equals(sale.getEmployeeId())) {
                String carPlate = sale.getCarPlate();
                Vehicle soldVehicle = getVehicleByCarPlate(carPlate, vehicles);

                // Check if the car is found and its status is sold (carStatus = 0)
                if (soldVehicle != null && soldVehicle.getCarStatus().equals("0")) {
                    // Calculate commission based on the acquired price
                    int vehicleAcquirePrice = Integer.valueOf(soldVehicle.getAcquirePrice());
                    double commission = commissionRate * vehicleAcquirePrice;
                    totalCommission += commission;
                }
            }
        }

        return totalCommission;
    }
    
    private static Vehicle getVehicleByCarPlate(String carPlate, List<Vehicle> vehicles) {
        // Find the vehicle with the given car plate
        for (Vehicle vehicle : vehicles) {
            if (carPlate.equals(vehicle.getCarPlate())) {
                return vehicle;
            }
        }
        return null; // Car not found
    }
    
    public static List<Employee> readEmployeeCSV(String filePath) {
        List<Employee> employees = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            // Read the CSV file line by line
            List<String[]> records = csvReader.readAll();

            // Skip the header row
            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);

                // Create an Employee object from the CSV data
                Employee employeeData = new Employee();

                employeeData.setEmployeeName(record[0]);
                employeeData.setEmployeeID(record[1]);
                employeeData.setEmployeeStatus(record[2]);
                employeeData.setEmployeePassword(record[3]);

                // Add the employee to the list
                employees.add(employeeData);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return employees;
    }
    
    public static List<Vehicle> readVehicleCSV(String filePath) {
        List<Vehicle> vehicles = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            // Read the CSV file line by line
            List<String[]> records = csvReader.readAll();

            // Skip the header row
            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);

                // Create an Employee object from the CSV data
                Vehicle vehiclesData = new Vehicle();
                vehiclesData.setCarPlate(record[0]);
                vehiclesData.setCarModel(record[1]);
                vehiclesData.setAcquirePrice(record[2]);
                vehiclesData.setCarStatus(record[3]);
                vehiclesData.setSalesPrice(record[4]);

                // Add the employee to the list
                vehicles.add(vehiclesData);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return vehicles;
    }
    
    public static List<Sales> readSalesCSV(String filePath) {
        List<Sales> sales = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            // Read the CSV file line by line
            List<String[]> records = csvReader.readAll();

            // Skip the header row
            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);

                // Create an Employee object from the CSV data
                Sales salesData = new Sales();
                salesData.setSalesId(record[0]);
                salesData.setDateTime(record[1]);
                salesData.setCarPlate(record[2]);
                salesData.setCustId(record[3]);
                salesData.setEmployeeId(record[4]);

                // Add the employee to the list
                sales.add(salesData);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return sales;
    }
    
    public static int getEmployeeStatus(String employeeId, String filePath) {
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = csvReader.readAll();

            for (String[] record : records) {
                if (record.length == 4 && record[0].equals(employeeId)) {
                    return Integer.parseInt(record[2]);
                }
            }
        } catch (IOException | CsvException | NumberFormatException e) {
            e.printStackTrace();
        }

        // If the employeeId is not found, return a default value (you can change it as needed)
        return -1;
    }
    
}
