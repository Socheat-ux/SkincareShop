package com.skincareshop.model.other;

public class Customer {

    private String customerId;
    private String fullName;
    private String phone;

    public Customer (String customerId, String fullName, String phone){
        setCustomerId(customerId);
        setFullName(fullName);
    }

    public String getCustomerId() { return customerId; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }

    public void setCustomerId(String customerId) {
        if (customerId.isBlank()) {
            this.customerId = "UNKNOWN";
        } 
        else this.customerId = customerId.trim();
    }

    public void setFullName(String fullName) {
        if (fullName.isBlank()) {
            this.fullName = "No Name";
        }
        else this.fullName = fullName.trim();
    }

  
    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
