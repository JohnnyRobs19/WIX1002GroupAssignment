package importData2;

public class Sales {

    private String salesId;

    private String dateTime;

    private String carPlate;

    private String custId;

    private String employeeId;

    public void Sales (String salesId, String dateTime, String carPlate,String custId, String employeeId) {
        this.salesId = salesId;
        this.dateTime = dateTime;
        this.carPlate = carPlate;
        this.custId = custId;
        this.employeeId = employeeId;
    }

    public String getSalesId() {
        return salesId;
    }
    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCarPlate() {
        return carPlate;
    }
    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getCustId() {
        return custId;
    }
    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


}
