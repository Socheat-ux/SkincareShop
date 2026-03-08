package com.projecttest.model;


public class Customer {
    private String customerId;
    private String fullName;
    private String phone;
    private String password;
    private double balance;

    public Customer(String customerId, String fullName, String phone,
                String password, double balance) {
        setCustomerId(customerId);
        setFullName(fullName);
        setPhone(phone);
        this.password = password;
        this.balance = balance;
    }                

    public String getCustomerId() { return customerId; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getPassword() { return password; }
    public double getBalance() { return balance; }

    public void setCustomerId(String customerId) {
        if (isBlank(customerId)) {
            this.customerId = "UNKNOWN";
        } 
        else this.customerId = customerId.trim();
    }

    public void setFullName(String fullName) {
        if (isBlank(fullName)) {
            this.fullName = "No Name";
        }
        else this.fullName = fullName.trim();
    }

   public void setPhone(String phone) {
        if (isBlank(phone)) {
            this.phone = "00000000";
        } else {
            this.phone = phone.trim();
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

  
    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", balance=" + balance +
                '}';
    }
}
