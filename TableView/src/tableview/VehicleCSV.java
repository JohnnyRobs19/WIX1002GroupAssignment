package tableview;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author seren
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VehicleCSV {
   

    private final StringProperty carPlate;
    private final StringProperty carModel;
    private final StringProperty acquirePrice;
    private final StringProperty carStatus;
    private final StringProperty salesPrice;

    public VehicleCSV(String carPlate, String carModel, String acquirePrice, String carStatus, String salesPrice) {
        this.carPlate = new SimpleStringProperty(carPlate);
        this.carModel = new SimpleStringProperty(carModel);
        this.acquirePrice = new SimpleStringProperty(acquirePrice);
        this.carStatus = new SimpleStringProperty(carStatus);
        this.salesPrice = new SimpleStringProperty(salesPrice);
    }

  

    public String getCarPlate() {
        return carPlate.get();
    }

    public StringProperty carPlateProperty() {
        return carPlate;
    }

    public String getCarModel() {
        return carModel.get();
    }

    public StringProperty carModelProperty() {
        return carModel;
    }

    public String getAcquirePrice() {
        return acquirePrice.get();
    }

    public StringProperty acquirePriceProperty() {
        return acquirePrice;
    }

    public String getCarStatus() {
        return carStatus.get();
    }

    public StringProperty carStatusProperty() {
        return carStatus;
    }

    public String getSalesPrice() {
        return salesPrice.get();
    }

    public StringProperty salesPriceProperty() {
        return salesPrice;
    }


    public static void main(String[] args) {
  
  //CSV = Comma-Separated Values 
  //   text file that uses a comma to separate values
  
  String file = "C:\\Users\\seren\\Downloads\\vehicle.csv";
  BufferedReader reader = null;
  String line = "";
  
  try {
   reader = new BufferedReader(new FileReader(file));
   while((line = reader.readLine()) != null) {
       
       String[] row = line.split(",");
       for(String index : row) {
     System.out.printf("%-10s", index);
    }
    System.out.println();
   }
  }
  catch(Exception e) {
   e.printStackTrace();
  }
    }
}

  
    

