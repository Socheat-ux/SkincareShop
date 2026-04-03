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

        shop.createCustomer("C001", "John Doe", "012345678", 100.00);

        shop.createProductItem("P001", "Aloe Vera Gel",   "Moisturizer",  5.99, 20, true);
        shop.createProductItem("P002", "Rose Toner",      "Toner",        8.50, 15, true);
        shop.createProductItem("P003", "Vitamin C Serum", "Serum",       15.00, 10, true);
        shop.createProductItem("P004", "SPF 50 Sunscreen","Sunscreen",   12.00,  8, false);
        
        shop.staffLogout();

        int choice = -1;

        try {
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
                            System.out.println("Exit program Successfully!");
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

                            System.out.print("Balance: ");
                            double balance = sc.nextDouble();
                            sc.nextLine();

                            try {
                                shop.createCustomer(customerId, fullName, phone, balance);
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
                            if (shop.getLoggedInStaff().can(SkincareShop.SET_PRODUCT_AVAILABLITY)) {
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

                            System.out.print("Enter item number to remove (0 to Exit): ");
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
                            shop.viewCart();
                            System.out.println();
                            shop.printCustomers();
    
                            System.out.print("Select customer number: ");
                            int customerIndex = sc.nextInt();
                            sc.nextLine();
    
                            try {
                                shop.checkoutByIndex(customerIndex - 1);
                                System.out.println(shop.getLastMessage());
                            } catch (IllegalStateException e) {
                                System.out.println("Payment failed: " + e.getMessage());
                            } catch (IllegalArgumentException e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                            break;
                        }

                        case 10: { // list customer
                            shop.printCustomers();
                            break;
                        }

                        case 11: { // LIST PRODUCTS
                            shop.printProductItems();
                            break;
                        }

                        case 12: { // LIST ORDERS
                            shop.printOrders();
                            break;
                        }

                        case 13: { // LOGOUT
                            shop.staffLogout();
                            System.out.println(shop.getLastMessage());
                            break;
                        }

                        case 0:
                            System.out.println("Exit program Successfully!");
                            break;

                        default:
                            System.out.println("Invalid choice.");
                    }
                }

            } while (choice != 0);
        }
        finally {
            sc.close();
        }
        
    }


    // ===== MENUS =====
    private static void printMainMenu() {
        System.out.println("\n========< MAIN MENU (Not Logged In) >========\n");
        System.out.println("1) Staff Login");
        System.out.println("2) View Products");
        System.out.println("0) Exit");
    }

    private static void printStaffMenu(SkincareShop shop) {
        System.out.println("\n" + "=".repeat(30) + "< STAFF MENU (Logged In) >" + "=".repeat(30));
        System.out.println("Login As: " + shop.getLoggedInStaff().getUsername() + " (" + shop.getLoggedInStaff().getPosition() + ")\n");

        String format = "%-3s) %-25s | %-3s) %-25s | %-3s %-25s\n";
        System.out.printf(format, "1", "Create Staff",  "6", "Add to Cart", "11  )", "List Products");
        System.out.printf(format, "2", "View Staff",    "7", "View Cart / Remove Item", "12  )", "List Orders");
        System.out.printf(format, "3", "Create Customer","8",  "Update Cart Quantity", "13  )", "Logout");
        System.out.printf(format, "4", "Create Product", "9", "Checkout", "0   )", "Exit");
        System.out.printf(format, "5", "Set Product Availability", "10", "List Customers", "", "");
    }
}