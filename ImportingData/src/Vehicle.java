/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author aiman
 */
public class Vehicle {
    
    private String carPlate;

    private String carModel;

    private String acquirePrice;

    private String carStatus;

    private String salesPrice;

    public void Vehicle(String carPlate, String carModel, String acquirePrice, String carStatus, String salesPrice) {
        this.carPlate = carPlate;
        this.carModel = carModel;
        this.acquirePrice = acquirePrice;
        this.carStatus = carStatus;
        this.salesPrice = salesPrice;
    }

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

    public String getAcquirePrice() {
        return acquirePrice;
    }

    public void setAcquirePrice(String acquirePrice) {
        this.acquirePrice = acquirePrice;
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
    
}
