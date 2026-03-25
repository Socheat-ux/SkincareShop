package com.skincareshop;

import java.util.Scanner;

import com.skincareshop.model.SkincareShop;
import com.skincareshop.model.staff.ManagerStaff;
import com.skincareshop.model.staff.Staff;

public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        SkincareShop shop = new SkincareShop("Blossom", "Phnom Penh");
  
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

                        shop.createStaff(staffId, fullName, phone, username, password, position);
                        System.out.println(shop.getLastMessage());
                        break;
                    }

                    case 2: { // CREATE CUSTOMER
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

                        shop.createCustomer(customerId, fullName, phone, password, balance);
                        System.out.println(shop.getLastMessage());
                        break;
                    }

                    case 3: { // CREATE PRODUCT
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
                        break;
                    }

                    case 4: { // SET PRODUCT AVAILABILITY
                        System.out.print("Product ID: ");
                        String productId = sc.nextLine();

                        System.out.print("Available? (1=Yes, 0=No): ");
                        int a = sc.nextInt();
                        sc.nextLine();

                        boolean available = (a == 1);

                        shop.setProductAvailability(productId, available);
                        System.out.println(shop.getLastMessage());
                        break;
                    }

                    case 5: { // ADD TO CART
                        shop.printProductItems();

                        System.out.print("Product ID: ");
                        String productId = sc.nextLine();

                        System.out.print("Quantity: ");
                        int qty = sc.nextInt();
                        sc.nextLine();

                        shop.addToCart(productId, qty);
                        System.out.println(shop.getLastMessage());
                        break;
                    }

                    case 6: { // VIEW CART
                        shop.viewCart();
                        break;
                    }

                    case 7: { // CHECKOUT
                        System.out.print("Customer phone: ");
                        String phone = sc.nextLine();

                        shop.checkout(phone);
                        System.out.println(shop.getLastMessage());
                        break;
                    }

                    case 8: { // LIST CUSTOMERS
                        shop.printCustomers();
                        break;
                    }

                    case 9: { // LIST PRODUCTS
                        shop.printProductItems();
                        break;
                    }

                    case 10: { // LIST ORDERS
                        shop.printOrders();
                        break;
                    }

                    case 11: { // LOGOUT
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
        System.out.println("1) Create Staff");
        System.out.println("2) Create Customer");
        System.out.println("3) Create Product");
        System.out.println("4) Set Product Availability");
        System.out.println("5) Add to Cart");
        System.out.println("6) View Cart");
        System.out.println("7) Checkout");
        System.out.println("8) List Customers");
        System.out.println("9) List Products");
        System.out.println("10) List Orders");
        System.out.println("11) Logout");
        System.out.println("0) Exit");
    }
}