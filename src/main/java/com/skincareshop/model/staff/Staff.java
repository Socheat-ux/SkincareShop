package com.skincareshop.model.staff;

public abstract class Staff implements IStaff{

    private String staffId;
    private String fullName;
    private String phone;
    private String username;
    private String password;    
    private boolean active;
    private String position;


    @Override
    public abstract boolean can(String action);

    // constructor
    public Staff(String staffId, String fullName, String phone,
                 String username, String password, String position) {

        setStaffId(staffId);
        setFullName(fullName);
        setPhone(phone);
        setUsername(username);
        setPassword(password);
        setPosition(position);

        this.active = true;
    }

    protected String getPassword() {
        return password;
    }

    public String getStaffId() { return staffId; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getUsername() { return username; }
    public boolean isActive() { return active; }
    public String getPosition() { return position; }

    public boolean checkPassword(String input) {
        return password != null && password.equals(input);
    }

    public void setStaffId(String staffId) {
        if (isBlank(staffId)) {
            this.staffId = "UNKNOWN";
        }
        else this.staffId = staffId.trim();
    }

    public void setFullName(String fullName) {
        if (isBlank(fullName)) {
            this.fullName = "No Name";
        }
        else this.fullName = fullName.trim();
    }

    public void setPhone(String phone) {
        String p = (phone == null) ? "" : phone.trim();
        if (!isDigits(p) || p.length() < 8 || p.length() > 15) {
            this.phone = "00000000";
        }
        else this.phone = p;
    }

    public void setUsername(String username) {
        if (isBlank(username)) {
            this.username = "staff_" + this.staffId;
        } 
        else this.username = username.trim();
    }

    public void setPassword(String password) {
        String pw = (password == null) ? "" : password;
        if (pw.length() < 4) {
            this.password = "0000";
        }
        else this.password = pw;
    }

    public void setPosition(String position) {
        if (isBlank(position)) {
            this.position = "Staff";
        }
        else this.position = position.trim();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // ====== Helpers ======
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean isDigits(String s) {
        if (isBlank(s)) return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    // ====== toString ======
    @Override
    public String toString() {
        return String.format("%-17s | %-15s | %-15s | %-10s",
        fullName, phone, username, active ? "Active" : "Inactive");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Staff)) return false;
        Staff other = (Staff) obj;
        return this.phone.equals(other.phone);
    }
  
}
