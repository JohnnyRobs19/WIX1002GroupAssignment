/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewinfo2;

/**
 *
 * @author HP
 */
public class employee {
    private String employeeId;
    private String employeeName;
    private String employeeStatus;
    private String employeePassword;

    // Default constructor (if needed)
    public employee() {
        // Default constructor implementation, if needed
    }

    // Constructor with four arguments
    public employee(String employeeId, String employeeName, String employeeStatus, String employeePassword) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeStatus = employeeStatus;
        this.employeePassword = employeePassword;
    }
 


    // Getters and setters for the fields (if needed)
    public String getEmployeeID() {
        return employeeId;
    }

    public void setEmployeeID(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    // Other methods...

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeStatus='" + employeeStatus + '\'' +
                ", employeePassword='" + employeePassword + '\'' +
                '}';
    }
}
