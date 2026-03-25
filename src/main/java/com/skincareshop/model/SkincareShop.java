package com.skincareshop.model;

import java.util.ArrayList;

import com.skincareshop.model.other.Customer;
import com.skincareshop.model.other.Products;
import com.skincareshop.model.staff.ManagerStaff;
import com.skincareshop.model.staff.CashierStaff;
import com.skincareshop.model.staff.Staff;

public class SkincareShop {

    public static final String CREATE_STAFF = "CREATE_STAFF";
    public static final String CREATE_CUSTOMER = "CREATE_CUSTOMER";
    public static final String CREATE_PRODUCT_ITEM = "CREATE_PRODUCT_ITEM";
    public static final String SET_PRODUCT_AVAILABLITY = "SET_PRODUCT_AVAILABLITY";
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
        staffs.add(new ManagerStaff("S001", "Admin", "010000000", "admin", "1234", 2000));
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
    private boolean requirePermission(String action) {
        if (!loggedInStaff.can(action)) {
            setLastMessage("Permission Denied!");
            return false;
        }
        return true;
    }

    //=============================//
    //This function for staff login//
    //=============================//
    public void staffLogin(String username, String password) {
        if (isBlank(username) || password == null) {
            setLastMessage("Login failed! Missing username/password!");
            return;
        }
        for (int i = 0; i < staffs.size(); i++) {
            Staff staff = staffs.get(i);
            if (staff.getUsername().equalsIgnoreCase(username.trim())) {
                if (!staff.isActive()) {
                    setLastMessage("Staff is inactive!");
                    return;
                }
                if (!staff.checkPassword(password)) {
                    setLastMessage("Loging failed! Wrong password!");
                    return;
                }
                loggedInStaff = staff;
                setLastMessage("Login success! Welcome " + staff.getFullName());
                return;
            }
        }
        setLastMessage("Login failed: Usrname not found!");
    }

    //==============================//
    //This function for staff logout//
    //==============================//
    public void staffLogout() {
        loggedInStaff = null;
        setLastMessage("Logout successfully!");
    }

    //==============================//
    //This function for create staff//
    //==============================//
    public void createStaff(String staffId, String fullName, String phone,
                            String username, String password, String position) {
        if  (!requireStaffLogin() || !requirePermission(CREATE_STAFF))  return;

        if (isBlank(staffId) || isBlank(username)) {
            setLastMessage("Cannot create staff: staffId/username is empty!");
            return;
        }
        //use this to prevent duplicate username 
        for (int i = 0; i < staffs.size(); i++) {
            if (staffs.get(i).getUsername().equalsIgnoreCase(username)) {
                setLastMessage("User name already exist!");
                return;
            }
        }
        //check position
        if (position.equals("Manager")) {
            staffs.add(new ManagerStaff(staffId, fullName, phone, username, password, 2000));
            setLastMessage("Manager created successfully.");
        }
        else if (position.equals("Cashier")) {
            staffs.add(new CashierStaff(staffId, fullName, phone, username, password, 1000));
            setLastMessage("Cashier created successfully.");
        }
        else {
            setLastMessage("Unknown position!");
        }
    }

    //=================================//
    //This function for create customer (Hanlde by Staff)//
    //=================================//
    public void createCustomer(String customerId, String fullName, String phone, 
                                String password, double balance ) {
        if (!requireStaffLogin() || !requirePermission(CREATE_CUSTOMER)) return;

        if (isBlank(customerId) || isBlank(phone)) {
            setLastMessage("Cannot create customer: customerId/phone is empty!");
            return;
        }
        for (int i = 0; i < customers.size(); i++) {
            //prevent duplicate customerId
            if (customers.get(i).getCustomerId().equalsIgnoreCase(customerId.trim())) {
                setLastMessage("Cannot create customer: customerId already exists.");
                return; 
            }
            if (customers.get(i).getPhone().equals(phone.trim())) {
                setLastMessage("Cannot create customer: phone already exists.");
                return;
            }
        }
        customers.add(new Customer(customerId, fullName, phone, password, balance));
        setLastMessage("Customer created successfully.");
    }

    //================================//
    //This function for create product (Handle by Staff)//
    //================================//
    public void createProdcutItem(String productId, String name, String category,
                                    double price, int stock, boolean available) {
        if (!requireStaffLogin() || !requirePermission(CREATE_PRODUCT_ITEM)) return;

        if (isBlank(productId)) {
            setLastMessage("Cannot create product: productId is empty.");
            return;
        }
        //prevent duplicate productId
        for (int i = 0; i < productItems.size(); i++) {
            if (productItems.get(i).getProductId().equalsIgnoreCase(productId.trim())) {
                setLastMessage("Cannot create product: productId already exists.");
                return;
            }
        }
        productItems.add(new Products(productId, name, category, price, stock, available));
        setLastMessage("Product created successfully.");
    }

    public void createOrder(String customerPhone, String itemId, int qty) {

        if (!requireStaffLogin()) return;

        if (isBlank(customerPhone) || isBlank(itemId) || qty <= 0) {
            setLastMessage("Cannot create order: invalid input.");
            return;
        }

        Products item = findProductById(itemId);
        if (item == null) {
            setLastMessage("Cannot create order: menu item not found.");
            return;
        }
        if (!item.isAvailable()) {
            setLastMessage("Cannot create order: menu item is not available.");
            return;
        }

        // if (!customer.deductBalance(total)) {
        //     setLastMessage("Cannot create order: insufficient balance.");
        //     return;
        // }

        // String orderId = "ORD" + (orders.size() + 1);
        // orders.add(new Order(orderId, customer, item, qty, loggedInStaff));

        // setLastMessage("Order created successfully: " + orderId);
    }
    
    // SET AVAILABILITY
    public void setProductAvailability(String productId, boolean available) {
        if (!requireStaffLogin()) return;

        Products product = findProductById(productId);
        if (product == null) {
            setLastMessage("Product not found.");
            return;
        }
        product.setAvailable(available);
        setLastMessage("Product availability updated.");
    }

    // HELPER
    private Products findProductById(String productId) {
        if (isBlank(productId)) return null;
        for (int i = 0; i < productItems.size(); i++) {
            if (productItems.get(i).getProductId().equalsIgnoreCase(productId.trim())) {
                return productItems.get(i);
            }
        }
        return null;
    }

    public void printCustomers() {
        if (!requireStaffLogin() || !requirePermission(VIEW_ORDER)) return;

        System.out.println("\n--- Customers (" + customers.size() + ") ---");
        if (customers.size() == 0) System.out.println("No customers.");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ") " + customers.get(i));
        }
    }

    public void printProductItems() {
        if (!requireStaffLogin()) return;

        System.out.println("\n--- Products (" + productItems.size() + ") ---");
        if (productItems.size() == 0) System.out.println("No products.");
        for (int i = 0; i < productItems.size(); i++) {
            System.out.println((i + 1) + ") " + productItems.get(i));
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }


    @Override
    public String toString() {
        return "SkincareShop{" +
                "\nshopName='" + shopName + '\'' +
                "\naddress='" + address + '\'' +
                "\nstaffs=" + staffs +
                "\ncustomers=" + customers +
                "\nproductItems=" + productItems +
                "\nloggedInStaff=" + loggedInStaff +
                '}';
    }
}
