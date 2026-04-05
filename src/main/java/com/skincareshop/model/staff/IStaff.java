package com.skincareshop.model.staff;

public interface IStaff {

    String getStaffId();
    String getFullName();
    String getUsername();
    String getPosition();
    boolean isActive();
    public abstract boolean can(String action);
    
}
