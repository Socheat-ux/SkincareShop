package com.skincareshop.model.staff;

import com.skincareshop.model.SkincareShop;

public class CashierStaff implements IStaff{
   
    private String staffId;
    private String fullName;
    private String username;
    private String password;   
    private String position;
    private boolean active;
 

    @Override
    public boolean hasPermission(String action) {
        if (action.equals(SkincareShop.CREATE_ORDER) || action.equals(SkincareShop.CREATE_CUSTOMER)
        || action.equals(SkincareShop.VIEW_CUSTOMER) || action.equals(SkincareShop.VIEW_CUSTOMER)){
            return true;
        }
        return false; 
    }


    public CashierStaff(String staffId, String fullName, String username,
         String password, String position) {

        setStaffId(staffId);
        setFullName(fullName);
        setUsername(username);
        setPassword(password);
        setPosition(position);

        this.active = true;
    }

    public String getStaffId() { return staffId; }
    public String getFullName() { return fullName; }
    public String getUsername() { return username; }
    public String getPosition() { return position; }
    public boolean isActive() { return active; }

    public boolean checkPassword(String input) {
        return password != null && password.equals(input);
    }

    // ====== Setters ======
    public void setStaffId(String staffId) {
        if (staffId.isBlank()) this.staffId = "UNKNOWN";
        else this.staffId = staffId.trim();
    }

    public void setFullName(String fullName) {
        if (fullName.isBlank()) this.fullName = "No Name";
        else this.fullName = fullName.trim();
    }

    public void setUsername(String username) {
        if (username.isBlank()) this.username = this.staffId + "_UNKNOWN";
        else this.username = username.trim();
    }

    public void setPassword(String password) {
        String pw = (password == null) ? "" : password;
        if (pw.length() < 4) this.password = "0000";
        else this.password = pw;
    }

    public void setPosition(String position) {
        if (position.isBlank()) this.position = "Staff";
        else this.position = position.trim();
    }

}
