package importData2;

public class Customer {

    private String customerID;
    private String customerName;
    private String customerHP;
    private String customerPostcode;

    public void Customer(String customerID, String customerName, String customerHP, String customerPostcode) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerHP = customerHP;
        this.customerPostcode = customerPostcode;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerId) {
        this.customerID = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerHP() {
        return customerHP;
    }

    public void setCustomerHP(String phoneNumber) {
        this.customerHP = phoneNumber;
    }

    public String getCustomerPostcode() {
        return customerPostcode;
    }

    public void setCustomerPostcode(String postCode) {
        this.customerPostcode = postCode;
    }
}
