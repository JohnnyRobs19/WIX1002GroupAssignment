/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewinfo;

/**
 *
 * @author HP
 */
public class customer {
    private String customerId;
    private String customerName;
    private String phoneNumber;
    private String postCode;

    // Default constructor (if needed)
    public customer() {
        // Default constructor implementation, if needed
    }

    // Constructor with four arguments
    public customer(String customerId, String customerName, String phoneNumber, String postCode) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.postCode = postCode;
    }

    // Getters and setters for the fields (if needed)
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    // Other methods...

    @Override
    public String toString() {
        return "customer{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
