package importData2;

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
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import viewinfo2.AppContext;

public class salaryCalculationController {
    
    @FXML
    private TextField employeeIDTextField;

    @FXML
    private Button calculateButton;

    @FXML
    private Button backButton;

    @FXML
    private Label name;
    
    @FXML
    private Label employeeRank;

    @FXML
    private Label baseSalary;
    
    @FXML
    private Label salesAmount;

    @FXML
    private Label totalCommisions;

    @FXML
    private Label baseAllowance;
    
    @FXML
    private Label totalNumCars;
    
    @FXML
    private Label totalBonus;

    @FXML
    private Label totalSalary;

    private Stage stage;
    private Scene scene;
    private Parent root;

    static String salesFile = "src//backups//salesBackup.csv";
    static String vehicleFile = "src//backups//vehicleBackup.csv";
    static String employeeFile = "src//backups//employeeBackup.csv";
public void calculateButtonPressed(javafx.event.ActionEvent event) {
    try {
        String employeeID = employeeIDTextField.getText();

        // Check if the employee exists
        int employeeStatus = getEmployeeStatus(employeeID, employeeFile);

        if (employeeStatus != -1) {
            // Employee exists, update the UI with employee information
//            name.setText("0");
            name.setText(getName(employeeID));
            employeeRank.setText(getEmployeeRank(employeeID));
            salesAmount.setText(String.format("%.2f", calculateTotalSalesG(employeeID)));
            baseSalary.setText(String.format("%.2f", calculateBaseSalary(employeeID)));
            totalCommisions.setText(String.format("%.2f", calculateTotalCommisions(employeeID)));
            baseAllowance.setText(String.format("%.2f", calculateBaseAllowance(employeeID)));
            totalSalary.setText(String.format("%.2f", calculateTotalSalary(employeeID)));
            totalNumCars.setText(Integer.toString(calculateTotalCarSoldG(employeeID)));
            totalBonus.setText(String.format("%.2f", calculateBonus(employeeID)));
        } else {
            // Employee does not exist, show an error message
            showErrorMessage("Employee not found.");
            // Clear or hide the employee information (optional)
            clearEmployeeInfo();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

// Rest of the code...

// Method to check if the employee exists
private boolean isEmployeeExists(String employeeID) {
    List<Employee> employees = readEmployeeCSV(employeeFile);
    for (Employee employee : employees) {
        if (employeeID.equals(employee.getEmployeeID())) {
            return true;
        }
    }
    return false;
}

// Method to clear or hide the employee information
private void clearEmployeeInfo() {
    employeeRank.setText("");
    baseSalary.setText("");
    totalCommisions.setText("");
    baseAllowance.setText("");
    totalSalary.setText("");
}


  @FXML
public void backButtonPressed(ActionEvent event) throws IOException, CsvException {
    try {
        // Determine the user's access level
        String accessLevel = AppContext.getAccessLevel(); // Assuming you have a method to get the access level

        String fxmlPath = null;

        // Determine which FXML page to load based on the access level
        if ("management".equals(accessLevel)) {
            fxmlPath = "/ManagementMenuFXML.fxml";
        } else if ("sales".equals(accessLevel)) {
            fxmlPath = "/SalesMenuFXML.fxml";
        } else {
            // Handle other access levels or scenarios as needed
        }

        // Load the appropriate FXML page
        if (fxmlPath != null) {
            Parent menuParent = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene menuScene = new Scene(menuParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(menuScene);
            window.show();
        }
    } catch (UnsupportedOperationException e) {
        // Handle the UnsupportedOperationException
        System.err.println("UnsupportedOperationException: " + e.getMessage());
        // You can log the exception or show an error message to the user
    }
}

    private void showErrorMessage(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);

        // Show the alert
        errorAlert.showAndWait();
    }
    
    public String getName(String employeeID) throws IOException, CsvException {
        // Read the employee CSV file
        List<Employee> employees = readEmployeeCSV(employeeFile);
//        System.out.println(employeeFile);
        
        // Loop through each employee
        for (Employee employee : employees) {
            // Check if the current employee's ID matches the provided employeeID
            if (employeeID.equals(employee.getEmployeeID())) {
                // If a match is found, return the employee's name
                return employee.getEmployeeName();
            }
        }
        return "Error do";
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
    
    private double calculateTotalSalesG(String employeeID) {
        List<Sales> salesInfo = readSalesCSV(salesFile);
        List<Vehicle> vehicleInfo = readVehicleCSV(vehicleFile);

        double totalSales = calculateTotalSales(employeeID, salesInfo, vehicleInfo);
        return totalSales;
    }
    
        private int calculateTotalCarSoldG(String employeeID) {
        List<Sales> salesInfo = readSalesCSV(salesFile);
        List<Vehicle> vehicleInfo = readVehicleCSV(vehicleFile);

        int totalCarSold = calculateTotalCarSold(employeeID, salesInfo, vehicleInfo);
        return totalCarSold;
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
        double bonus=calculateBonus(employeeID);

        double totalSalary = baseSalary + commisions + baseAllowance+bonus;
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
                    // Calculate commission based on the sales price
                    int vehicleSalesPrice = Integer.valueOf(soldVehicle.getSalesPrice());
                    double commission = commissionRate * vehicleSalesPrice;
                    totalCommission += commission;
                }
            }
        }
        return totalCommission;
    }
    
    private static int calculateTotalCarSold(String employeeId, List<Sales> sales, List<Vehicle> vehicles) {
        int totalCarSold = 0;

        for (Sales sale : sales) {
            if (employeeId.equals(sale.getEmployeeId())) {
                String carPlate = sale.getCarPlate();
                Vehicle soldVehicle = getVehicleByCarPlate(carPlate, vehicles);

                // Check if the car is found and its status is sold (carStatus = 0)
                if (soldVehicle != null && soldVehicle.getCarStatus().equals("0")) {
                    // Calculate commission based on the sales price
                    totalCarSold=totalCarSold+1;
                }
            }
        }
        return totalCarSold;
    }
    
    private static double calculateTotalSales(String employeeId, List<Sales> sales, List<Vehicle> vehicles) {
        double totalSales = 0;

        for (Sales sale : sales) {
            if (employeeId.equals(sale.getEmployeeId())) {
                String carPlate = sale.getCarPlate();
                Vehicle soldVehicle = getVehicleByCarPlate(carPlate, vehicles);

                // Check if the car is found and its status is sold (carStatus = 0)
                if (soldVehicle != null && soldVehicle.getCarStatus().equals("0")) {
                    // Calculate commission based on the sales price
                    int vehicleSalesPrice = Integer.valueOf(soldVehicle.getSalesPrice());
                    totalSales=totalSales+vehicleSalesPrice;
                }
            }
        }
        return totalSales;
    }
    
    private double calculateBonus(String employeeID) {
	List<Sales> salesInfo = readSalesCSV(salesFile);
        List<Vehicle> vehicleInfo = readVehicleCSV(vehicleFile);
        int status = getEmployeeStatus(employeeID, employeeFile);
	int totalNoCarSold=calculateTotalCarSold(employeeID, salesInfo, vehicleInfo);
	double sales=calculateTotalSales(employeeID, salesInfo, vehicleInfo);

        double bonus=0;

        if (status == 0){
            if (totalNoCarSold>15 || sales>1000000){
		bonus=500;
	    }
        } else if (status == 1){
            if (sales>2500000){
                bonus=0.0135*sales;
            }else if (sales>1600000){
                bonus=0.0125*sales;
            }else if (sales>800000){
                bonus=0.0115*sales;
            }else{
                bonus=0.01*sales;
            }
    }
    return bonus;
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

                employeeData.setEmployeeName(record[1]);
                employeeData.setEmployeeID(record[0]);
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
