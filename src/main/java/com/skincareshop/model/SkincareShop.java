package com.skincareshop.model;

import java.util.ArrayList;

import com.skincareshop.model.staff.IStaff;
import com.skincareshop.model.staff.ManagerStaff;

public class SkincareShop {

    public static final String CREATE_STAFF = "CREATE_STAFF";
    public static final String CREATE_CUSTOMER = "CREATE_CUSTOMER";
    public static final String CREATE_MENU_ITEM = "CREATE_MENU_ITEM";
    public static final String SET_MENU_AVAILABLITY = "SET_MENU_AVAILABLITY";
    public static final String CREATE_ORDER = "CREATE_ORDER";
    public static final String VIEW_CUSTOMER = "VIEW_CUSTOMER";
    public static final String VIEW_ORDER = "VIEW_ORDER";

    private String shopName;
    private String address;
    
    private ArrayList<IStaff> staffs;

    public SkincareShop(String shopName, String address) {
        staffs = new ArrayList<>();

        seedDefaultAdmin();
    }
    
    public String getShopName() { return shopName; }
    public String getAddress() { return address; }

    public void setShopName(String shopName) {
        if (shopName.isBlank()) {
            this.shopName = "SkincareShop";
        }
        else this.shopName = shopName.trim();     
    }

    public void setAddress(String address) {
        if (address.isBlank()) {
            this.address = "Phnom Penh";
        }
        else this.address = address.trim();
    }

    private void seedDefaultAdmin() {
        IStaff admin = new ManagerStaff("S001", "Admin", "admin", "1234", "Manager");
        staffs.add(admin);
    }

}
