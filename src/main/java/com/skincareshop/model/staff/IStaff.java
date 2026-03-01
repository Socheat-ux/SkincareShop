package com.skincareshop.model.staff;

public interface IStaff {

    String getStaffId();
    String getFullName();
    String getUsername();
    boolean isActive();
    boolean checkPassword(String password);
    boolean hasPermission(String action);

}
