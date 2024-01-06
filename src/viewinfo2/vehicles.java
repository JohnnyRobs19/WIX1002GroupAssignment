/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewinfo2;

/**
 *
 * @author HP
 */

public class vehicles {
    private String carPlate;
    private String carModel;
    private String acquiredPrice;
    private String carStatus;
    private String salesPrice;

    // Default constructor (if needed)
    public vehicles() {
        // Default constructor implementation, if needed
    }

    // Constructor with five arguments
    public vehicles(String carPlate, String carModel, String acquiredPrice, String carStatus, String salesPrice) {
        this.carPlate = carPlate;
        this.carModel = carModel;
        this.acquiredPrice = acquiredPrice;
        this.carStatus = carStatus;
        this.salesPrice = salesPrice;
    }

    // Getters and setters for the fields (if needed)
    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getAcquiredPrice() {
        return acquiredPrice;
    }

    public void setAcquiredPrice(String acquiredPrice) {
        this.acquiredPrice = acquiredPrice;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public String getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
    }

    // Other methods...

    @Override
    public String toString() {
        return "Car{" +
                "carPlate='" + carPlate + '\'' +
                ", carModel='" + carModel + '\'' +
                ", acquiredPrice='" + acquiredPrice + '\'' +
                ", carStatus='" + carStatus + '\'' +
                ", salesPrice='" + salesPrice + '\'' +
                '}';
    }
}
