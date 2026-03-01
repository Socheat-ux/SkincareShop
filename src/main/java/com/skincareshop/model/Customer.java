package com.skincareshop.model;

public class Customer {

    private String customerId;
    private String fullName;

    public Customer (String customerId, String fullName){
        setCustomerId(customerId);
        setFullName(fullName);
    }

    public String getCustomerId() { return customerId; }
    public String getFullName() { return fullName; }

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
