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
        
        Staff seed = makeTempStaff("S001", "Admin", "010000000", "admin", "1234");
        staffs.add(new ManagerStaff(seed, 2000));
    }

    private Staff makeTempStaff(String staffId, String fullName, String phone,
                                String username, String password) {
        return new Staff(staffId, fullName, phone, username, password) {
            @Override
            public boolean can(String action) { return false; }
        };
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
        Staff seed = makeTempStaff(staffId, fullName, phone, username, password);

        if (position.equals("Manager")) {
            staffs.add(new ManagerStaff(seed, 2000));
            setLastMessage("Manager created successfully.");
        } else if (position.equals("Cashier")) {
            staffs.add(new CashierStaff(seed, 1000));
            setLastMessage("Cashier created successfully.");
        } else {
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

    public void createOrder(String customerPhone, String itemId, int qty) {
        if (!requireStaffLogin() || !requirePermission(CREATE_ORDER)) return; 
 
        if (isBlank(customerPhone) || isBlank(itemId) || qty <= 0) {
            setLastMessage("Cannot create order: invalid input.");
            return;
        }
 
        // find customer by phone
        Customer customer = findCustomerByPhone(customerPhone);
        if (customer == null) {
            setLastMessage("Cannot create order: customer not found.");
            return;
        }
 
        Products item = findProductById(itemId);
        if (item == null) {
            setLastMessage("Cannot create order: product not found.");
            return;
        }
        if (!item.isAvailable()) {
            setLastMessage("Cannot create order: product is not available.");
            return;
        }
 
        // check sufficient stock (Products has reduceStock() which also validates)
        if (item.getStock() < qty) {
            setLastMessage("Cannot create order: insufficient stock.");
            return;
        }
 
        double total = item.getPrice() * qty;
 
        // check customer has enough balance
        if (customer.getBalance() < total) {
            setLastMessage("Cannot create order: insufficient balance.");
            return;
        }
 
        // deduct balance and reduce stock using existing methods
        customer.setBalance(customer.getBalance() - total);
        item.reduceStock(qty);  // uses Products.reduceStock() which already exists
 
        String orderId = "ORD" + (orders.size() + 1);
        orders.add(new Order(orderId, customer, item, qty, loggedInStaff));
        setLastMessage("Order created successfully: " + orderId);
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

        if (customer.getBalance() < total) {
            setLastMessage("Insufficient balance.");
            return;
        }

        // Deduct balance
        customer.setBalance(customer.getBalance() - total);

        // Create orders from cart items
        for (ProductsItem item : cartService.getItems()) {
            String orderId = "ORD" + (orders.size() + 1);
            orders.add(new Order(orderId, customer, item.getProduct(), item.getQuantity(), loggedInStaff));
        }

        cartService.clearCart();
        setLastMessage("Checkout successful!");
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
    //       PRINT METHODS      //
    // ======================== //
    public void printOrders() {
        if (!requireStaffLogin() || !requirePermission(VIEW_ORDER)) return;
        System.out.println("\n--- Orders (" + orders.size() + ") ---");
        if (orders.isEmpty()) { System.out.println("  No orders yet."); return; }
        for (int i = 0; i < orders.size(); i++) {
            System.out.println("  " + (i + 1) + ") " + orders.get(i));
        }
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
