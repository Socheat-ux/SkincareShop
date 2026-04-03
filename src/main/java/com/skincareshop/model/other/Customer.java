package com.skincareshop.model.other;

public class Customer {
    private String customerId;
    private String fullName;
    private String phone;
    private double balance;

    public Customer(String customerId, String fullName, String phone,
               double balance) {
        setCustomerId(customerId);
        setFullName(fullName);
        setPhone(phone);
        setBalance(balance);
    }                

    public String getCustomerId() { return customerId; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
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
        String p = isBlank(phone) ? "" : phone.trim();
        if (!isDigits(p) || p.length() < 8 || p.length() > 15) {
            throw new IllegalArgumentException("Wrong phone format! EX: 012-243-199");
        } else {
            this.phone = p;
        }
    }

    private boolean isDigits(String s) {
        if (isBlank(s)) return false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') return false;
        }
        return true;
    }

    public void setBalance(double balance) {
        this.balance = balance < 0 ? 0 : balance;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }


  
    @Override
    public String toString() {
        return String.format("%-9s | %-20s | %-15s | $%.2f",
                customerId, fullName, phone, balance);
    }
}