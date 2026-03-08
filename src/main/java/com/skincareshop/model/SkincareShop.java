package com.skincareshop.model;

import java.util.ArrayList;

import com.skincareshop.model.other.Customer;
import com.skincareshop.model.other.Products;
import com.skincareshop.model.staff.IStaff;
import com.skincareshop.model.staff.ManagerStaff;
import com.skincareshop.model.staff.Staff;

public class SkincareShop {

    public static final String CREATE_STAFF = "CREATE_STAFF";
    public static final String CREATE_CUSTOMER = "CREATE_CUSTOMER";
    public static final String CREATE_MENU_ITEM = "CREATE_PRODUCT_ITEM";
    public static final String SET_MENU_AVAILABLITY = "SET_PRODUCT_AVAILABLITY";
    public static final String CREATE_ORDER = "CREATE_ORDER";
    public static final String VIEW_CUSTOMER = "VIEW_CUSTOMER";
    public static final String VIEW_ORDER = "VIEW_ORDER";

    private String shopName;
    private String address;
    private String lastMessage;
    
    private ArrayList<Staff> staffs;
    private ArrayList<Customer> customers;
    private ArrayList<Products> productItems;
    private Staff loggedInStaff;

    public SkincareShop(String shopName, String address) {
        setShopName(shopName);
        setAddress(address);

        staffs = new ArrayList<>();
        customers = new ArrayList<>();
        productItems = new ArrayList<>();

        loggedInStaff = null;
        seedDefaultAdmin();

        lastMessage = "SkincareShop created. Default staff: admin / 1122";
    }
    
    public String getShopName() { return shopName; }
    public String getAddress() { return address; }
    public String getLastMessage() { return lastMessage; }

    public void setShopName(String shopName) {
        if (isBlank(shopName)) {
            this.shopName = "SkincareShop";
        }
        else this.shopName = shopName.trim();     
    }

    public void setAddress(String address) {
        if (isBlank(address)) {
            this.address = "Phnom Penh";
        }
        else this.address = address.trim();
    }

    private void setLastMessage(String msg) { lastMessage = msg; }

    private void seedDefaultAdmin() {
        Staff s1 = new Staff("S001", "Admin", "010000000", "admin", "1234");
        ManagerStaff admin = new ManagerStaff(s1, 2000);
        staffs.add(admin);
    }

    //Function for require staff to login
    private boolean requireStaffLogin() {
        if (loggedInStaff == null) {
            setLastMessage("Action Denied! Staff must login first!");
            return false;
        }
        if (!loggedInStaff.isActive()) {
            loggedInStaff = null;
            setLastMessage("Staff is inactive!");
            return false;
        }
        return true;
    }

    //This function checks if the currently logged-in staff has permission to do a specific action.
    
    
}
