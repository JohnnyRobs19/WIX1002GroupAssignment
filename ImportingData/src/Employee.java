/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author aiman
 */
public class Employee {
    
    private String employeeID;

    private String employeeName;

    private String employeeStatus;

    private String employeePassword;



    public void Employee(String employeeID, String employeeName, String employeeStatus, String employeePassword) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeeStatus = employeeStatus;
        this.employeePassword = employeePassword;
    }

    public String getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
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
    
}
