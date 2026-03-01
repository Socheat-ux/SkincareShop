package com.skincareshop.model.staff;

public interface IStaff {

    String getStaffId();
    String getFullName();
    String getUsername();
    String getPosition();
    boolean isActive();
    boolean checkPassword(String password);
    boolean hasPermission(String action);

}
