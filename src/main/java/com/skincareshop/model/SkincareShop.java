package com.skincareshop.model;

import java.util.ArrayList;

import com.skincareshop.model.other.Customer;
import com.skincareshop.model.other.Products;
import com.skincareshop.model.other.ProductsItem;
import com.skincareshop.model.other.Order;
import com.skincareshop.model.staff.ManagerStaff;
import com.skincareshop.model.staff.CashierStaff;
import com.skincareshop.model.staff.Staff;
import com.skincareshop.service.CartService;

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
    private ArrayList<Order> orders;
    private ArrayList<Products> productItems;
    private Staff loggedInStaff;
    private CartService cartService;

    public SkincareShop(String shopName, String address) {
        setShopName(shopName);
        setAddress(address);

        staffs = new ArrayList<>();
        customers = new ArrayList<>();
        orders = new ArrayList<>();
        productItems = new ArrayList<>();
        cartService = new CartService();

        loggedInStaff = null;
        seedDefaultAdmin();

        lastMessage = "SkincareShop created. Default staff: admin / 1122";
    }
    
    public String getShopName() { return shopName; }
    public String getAddress() { return address; }
    public String getLastMessage() { return lastMessage; }
    public Staff getLoggedInStaff() { return loggedInStaff; }

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
        setLastMessage("\nLogin failed: Username not found!");
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
            staffs.add(new ManagerStaff(staffId, fullName, phone, username, password, 1500));
        } else if (position.equals("Cashier")) {
            staffs.add(new CashierStaff(staffId, fullName, phone, username, password, 500));
        }
    }

    //=================================//
    //This function for create customer (Hanlde by Staff)//
    //=================================//
    public void createCustomer(String customerId, String fullName, String phone, 
                                double balance ) {
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
        customers.add(new Customer(customerId, fullName, phone, balance));
        setLastMessage("Customer created successfully.");
    }

    //================================//
    //This function for create product (Handle by Staff)//
    //================================//
    public void createProductItem(String productId, String name, String category,
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

    //Add to cart
    public void addToCart(String productId, int qty) {
        if (!requireStaffLogin()) return;

        Products product = findProductById(productId);

        if (product == null) {
            setLastMessage("Product not found.");
            return;
        }

        try {
            cartService.addProductsItem(product, qty);
            setLastMessage("Product added to cart.");
        } catch (Exception e) {
            setLastMessage(e.getMessage());
        }
    }

    public void viewCart() {
        cartService.printInfo();
    }

    //Remove fro Cart
    public void removeFromCart(int index) {
        if (!requireStaffLogin()) return;
        try {
            cartService.removeProductsItem(index);
            setLastMessage("Item removed from cart.");
        } catch (IndexOutOfBoundsException e) {
            setLastMessage("Invalid item number. Please choose a number from the list.");
        }
    }

    // For Checkout
    public void checkout(String customerPhone) {
        if (!requireStaffLogin() || !requirePermission(CREATE_ORDER)) return;

        if (cartService.size() == 0) {
            setLastMessage("Cart is empty.");
            return;
        }

        Customer customer = findCustomerByPhone(customerPhone);
        if (customer == null) {
            setLastMessage("Customer not found.");
            return;
        }

        double total = cartService.getTotalPrice();

        // checkout() — insufficient balance
        if (customer.getBalance() < total) {
            throw new IllegalStateException(
                "Insufficient balance. Need $" + String.format("%.2f", total) +
                ", customer has $" + String.format("%.2f", customer.getBalance())
            );
        }

        // Deduct balance
        customer.setBalance(customer.getBalance() - total);

        // Create orders from cart items
        for (ProductsItem item : cartService.getItems()) {
            String orderId = "ORD" + (orders.size() + 1);
            orders.add(new Order(orderId, customer, item.getProduct(), item.getQuantity(), loggedInStaff));
        }

        cartService.clearCart();
        setLastMessage("Checkout successful! " + customer.getFullName() +
                       " charged $" + String.format("%.2f", total) + ".");
    }


    // =======================================================
    // CHECKOUT BY INDEX — thin wrapper around checkout()
    // Translates a customer list index into a phone number,
    // then delegates all logic to checkout() above.
    //========================================================
    public void checkoutByIndex(int index) {
        if (!requireStaffLogin() || !requirePermission(CREATE_ORDER)) return;
 
        if (index < 0 || index >= customers.size()) {
            throw new IllegalArgumentException(
                "Invalid customer number. Please choose between 1 and " + customers.size() + "."
            );
        }
 
        // Translate index → phone, then hand off to checkout()
        checkout(customers.get(index).getPhone());
    }
    
    //Update cart quantity
    public void updateCartQuantity(int index, int newQty) {
        if (!requireStaffLogin()) return;
        try {
            cartService.updateQuantityProductsItem(index, newQty);
            setLastMessage("Cart item updated.");
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            setLastMessage("Could not update: " + e.getMessage());
        }
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

    // ======================== //
    //       PRINT ALL STAFF    //
    // ======================== //

    public void printStaffs() {
        if (!requireStaffLogin()) return;

        System.out.println("\n--- Staffs (" + staffs.size() + ") ---\n");
        System.out.println(String.format("%-1s %-18s | %-15s | %-15s | %-10s | %-10s | %s",
        "#", "Name", "Phone number", "Username", "Status", "Position", "Salary"));
        System.out.println("-".repeat(94));

        if (staffs.isEmpty()) { System.out.println("  No staffs."); return; }
        for (int i = 0; i < staffs.size(); i++) {
            System.out.println((i + 1) + ") " + staffs.get(i));
        }
    }

    // ======================== //
    //       PRINT ORDERS       //
    // ======================== //
    public void printOrders() {
        if (!requireStaffLogin() || !requirePermission(VIEW_ORDER)) return;

        System.out.println("\n" + "=".repeat(41) + " Orders (" + orders.size() + ") " + "=".repeat(41));
        System.out.println(String.format("%-1s %-6s | %-16s | %-20s | %-10s | %-10s | %s",
        "#", "Order ID", "Customer Phone", "Item Name", "Quantity", "Total Price", "Created By", "Paid"));
        System.out.println("-".repeat(94));      

        if (orders.isEmpty()) { System.out.println("  No orders yet."); return; }
        for (int i = 0; i < orders.size(); i++) { 
            System.out.println((i + 1) + ") " + orders.get(i));
        }
        System.out.println("\n");
    }

    // ======================== //
    //       PRINT CUSTOMER     //
    // ======================== //
    public void printCustomers() {
        if (!requireStaffLogin() || !requirePermission(VIEW_ORDER)) return;

        System.out.println("\n" + "=".repeat(25) + " Customers (" + customers.size() + ") " + "=".repeat(26));

        System.out.println(String.format("%-1s %-10s | %-20s | %-15s | %s",
        "#", "ID", "Name", "Phone", "Balance"));
        System.out.println("-".repeat(66));

        if (customers.size() == 0) System.out.println("No customers.");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ") " + customers.get(i));
        }
        System.out.println("\n");
    }

    // ======================== //
    //       PRINT PRODUCTS     //
    // ======================== //

    public void printProductItems() {
        System.out.println("\n--- Products (" + productItems.size() + ") ---\n");

        System.out.println(String.format("%-1s %-7s | %-20s | %-12s | %-7s | %-10s | %s",
        "#", "ID", "Name", "Category", "Price", "Stock", "Status"));
        System.out.println("-".repeat(85));

        if (productItems.size() == 0) System.out.println("No products.");
        for (int i = 0; i < productItems.size(); i++) {
            System.out.println((i + 1) + ") " + productItems.get(i));
        }
    }

    private Customer findCustomerByPhone(String phone) {
            if (isBlank(phone)) return null;
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getPhone().equals(phone.trim())) {
                    return customers.get(i);
                }
            }
            return null;
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
