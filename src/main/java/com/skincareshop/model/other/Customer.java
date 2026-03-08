package com.skincareshop.model.other;

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
        setPassword(password);
        setBalance(balance);
    }                

    public String getCustomerId() { return customerId; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public double getBalance() { return balance; }

    public boolean checkPassword(String input) {
        return password != null && password.equals(input);
    }

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
            this.phone = "00000000";
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

    public void setPassword(String password) {
        String pw = (password == null) ? "" : password;
        if (pw.length() < 4) this.password = "0000";
        else this.password = pw;
    }

    public void setBalance(double balance) {
        this.balance = balance < 0 ? 0 : balance;
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