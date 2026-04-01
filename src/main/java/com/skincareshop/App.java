package com.skincareshop;

import java.util.Scanner;

import com.skincareshop.model.SkincareShop;

public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        SkincareShop shop = new SkincareShop("Blosoom", "Phnom Penh");

        shop.staffLogin("admin", "1234");

        shop.createStaff("S002", "Sokha", "012000001", "sokha", "1234", "Manager");
        shop.createStaff("S003", "Dara",  "012000002", "dara",  "1234", "Cashier");

        shop.createProductItem("P001", "Aloe Vera Gel",   "Moisturizer",  5.99, 20, true);
        shop.createProductItem("P002", "Rose Toner",      "Toner",        8.50, 15, true);
        shop.createProductItem("P003", "Vitamin C Serum", "Serum",       15.00, 10, true);
        shop.createProductItem("P004", "SPF 50 Sunscreen","Sunscreen",   12.00,  8, false);
        
        shop.staffLogout();

        int choice;

        do {
            if (shop.getLoggedInStaff() == null) {

                printMainMenu();

                System.out.print("Choose: ");
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1: { // LOGIN
                        System.out.print("Username: ");
                        String username = sc.nextLine();

                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        shop.staffLogin(username, password);
                        System.out.println(shop.getLastMessage());
                        break;
                    }

                    case 2: { // VIEW PRODUCTS
                        shop.printProductItems();
                        break;
                    }

                    case 0:
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } else {

                printStaffMenu(shop);

                System.out.print("Choose: ");
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1: { // CREATE STAFF
                        if (shop.getLoggedInStaff().can(SkincareShop.CREATE_STAFF)) {
                            System.out.print("Staff ID: ");
                            String staffId = sc.nextLine();

                            System.out.print("Full Name: ");
                            String fullName = sc.nextLine();

                            System.out.print("Phone: ");
                            String phone = sc.nextLine();

                            System.out.print("Username: ");
                            String username = sc.nextLine();

                            System.out.print("Password: ");
                            String password = sc.nextLine();

                            System.out.print("Position (Manager/Cashier): ");
                            String position = sc.nextLine();

                            try {
                                shop.createStaff(staffId, fullName, phone, username, password, position);
                                System.out.println(shop.getLastMessage());
                            } catch (IllegalArgumentException e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                        } else {
                            System.out.println("\nError: Only Manager or Admin has permission to create staff!");
                        }
                        break;
                    }

                    case 2: {
                        shop.printStaffs();
                        break;
                    }

                    case 3: { // CREATE CUSTOMER
                        System.out.print("Customer ID: ");
                        String customerId = sc.nextLine();

                        System.out.print("Full Name: ");
                        String fullName = sc.nextLine();

                        System.out.print("Phone: ");
                        String phone = sc.nextLine();

                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        System.out.print("Balance: ");
                        double balance = sc.nextDouble();
                        sc.nextLine();

                        try {
                            shop.createCustomer(customerId, fullName, phone, password, balance);
                            System.out.println(shop.getLastMessage());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    }

                    case 4: { // CREATE PRODUCT
                        if (shop.getLoggedInStaff().can(SkincareShop.CREATE_PRODUCT_ITEM)) {
                            System.out.print("Product ID: ");
                            String productId = sc.nextLine();

                            System.out.print("Name: ");
                            String name = sc.nextLine();

                            System.out.print("Category: ");
                            String category = sc.nextLine();

                            System.out.print("Price: ");
                            double price = sc.nextDouble();

                            System.out.print("Stock: ");
                            int stock = sc.nextInt();

                            System.out.print("Available? (1=Yes, 0=No): ");
                            int a = sc.nextInt();
                            sc.nextLine();

                            boolean available = (a == 1);

                            shop.createProductItem(productId, name, category, price, stock, available);
                            System.out.println(shop.getLastMessage());
                        } else {
                            System.out.println("\nError: Only Manager or Admin has permission to create product!");
                        }
                        break;
                    }

                    case 5: { // SET PRODUCT AVAILABILITY
                        if (!shop.getLoggedInStaff().can(SkincareShop.SET_PRODUCT_AVAILABLITY)) {
                            System.out.print("Product ID: ");
                            String productId = sc.nextLine();

                            System.out.print("Available? (1=Yes, 0=No): ");
                            int a = sc.nextInt();
                            sc.nextLine();

                            boolean available = (a == 1);

                            shop.setProductAvailability(productId, available);
                            System.out.println(shop.getLastMessage());
                        } else {
                            System.out.println("\nError: Only Manager or Admin has permission to set product availability!");
                        }
                        break;
                    }

                    case 6: { // ADD TO CART
                        shop.printProductItems();

                        System.out.print("Product ID: ");
                        String productId = sc.nextLine();

                        System.out.print("Quantity: ");
                        int qty = sc.nextInt();
                        sc.nextLine();

                        try {
                            shop.addToCart(productId, qty);
                            System.out.println(shop.getLastMessage());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                        
                    }

                    case 7: { // VIEW CART
                        shop.viewCart();

                        System.out.print("Enter item number to remove (0 to cancel): ");
                        int index = sc.nextInt();
                        sc.nextLine();

                        if (index == 0) {
                            System.out.println("Cancelled.");
                            break;  // exit without removing anything
                        }

                        try {
                            shop.removeFromCart(index - 1);
                            System.out.println(shop.getLastMessage());
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                       
                    }

                    case 8: { // UPDATE CART QUANTITY
                        shop.viewCart();
                        System.out.print("Enter item number to update (0 to cancel): ");
                        int index = sc.nextInt();
                        sc.nextLine();
 
                        if (index == 0) {
                            System.out.println("Cancelled.");
                            break;
                        }
 
                        System.out.print("New quantity: ");
                        int newQty = sc.nextInt();
                        sc.nextLine();
 
                        shop.updateCartQuantity(index - 1, newQty);
                        System.out.println(shop.getLastMessage());
                        break;
                    }

                    case 9: { // CHECKOUT
                        System.out.print("Customer phone: ");
                        String phone = sc.nextLine();

                        try {
                            shop.checkout(phone);
                            System.out.println(shop.getLastMessage());
                        } catch (IllegalStateException e) {
                            System.out.println("Payment failed: " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    }

                    case 10: { // LIST CUSTOMERS
                        System.out.print("Customer phone: ");
                        String phone = sc.nextLine();
                        System.out.print("Product ID: ");
                        String productId = sc.nextLine();
                        System.out.print("Quantity: ");
                        int qty = sc.nextInt();
                        sc.nextLine();

                        try {
                            shop.createOrder(phone, productId, qty);
                            System.out.println(shop.getLastMessage());
                        } catch (IllegalStateException e) {
                            System.out.println("Payment failed: " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    }

                    case 11: {
                        shop.printCustomers();
                        break;
                    }

                    case 12: { // LIST PRODUCTS
                        shop.printProductItems();
                        break;
                    }

                    case 13: { // LIST ORDERS
                        shop.printOrders();
                        break;
                    }

                    case 14: { // LOGOUT
                        shop.staffLogout();
                        System.out.println(shop.getLastMessage());
                        break;
                    }

                    case 0:
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } while (choice != 0);

        sc.close();
    }


    // ===== MENUS =====
    private static void printMainMenu() {
        System.out.println("\n=== MAIN MENU (Not Logged In) ===");
        System.out.println("1) Staff Login");
        System.out.println("2) View Products");
        System.out.println("0) Exit");
    }

    private static void printStaffMenu(SkincareShop shop) {
        System.out.println("\n=== STAFF MENU (Logged In) ===");
        System.out.println("Logged in: " + shop.getLoggedInStaff());
        System.out.println(" 1) Create Staff");
        System.out.println(" 2) View Staff");
        System.out.println(" 3) Create Customer");
        System.out.println(" 4) Create Product");
        System.out.println(" 5) Set Product Availability");
        System.out.println(" 6) Add to Cart");
        System.out.println(" 7) View Cart / Remove Item");
        System.out.println(" 8) Update Cart Quantity");   
        System.out.println(" 9) Checkout");
        System.out.println("10) Direct Order (no cart)");
        System.out.println("11) List Customers");
        System.out.println("12) List Products");
        System.out.println("13) List Orders");
        System.out.println("14) Logout");
        System.out.println(" 0) Exit");
    }
}