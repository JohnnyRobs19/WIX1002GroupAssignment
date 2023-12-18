package viewinfo;

import java.time.LocalDateTime;

public class sales {
    private String salesId;
    private LocalDateTime dateAndTime;
    private String carPlate;
    private String customerId;
    private String employeeId;
    private String price;

    // Default constructor (if needed)
    public sales() {
        // Default constructor implementation, if needed
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isEmployeeSale(String selectedEmployeeId) {
        return this.employeeId.equals(selectedEmployeeId);
    }

    // Constructor with five arguments
    public sales(String salesId, LocalDateTime dateAndTime, String carPlate, String customerId, String employeeId) {
        this.salesId = salesId;
        this.dateAndTime = dateAndTime;
        this.carPlate = carPlate;
        this.customerId = customerId;
        this.employeeId = employeeId;
    }

    // Getters and setters for the fields (if needed)
    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    // Other methods...

    @Override
    public String toString() {
        return "Sales{" +
                "salesId='" + salesId + '\'' +
                ", dateAndTime=" + dateAndTime +
                ", carPlate='" + carPlate + '\'' +
                ", customerId='" + customerId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }
}

