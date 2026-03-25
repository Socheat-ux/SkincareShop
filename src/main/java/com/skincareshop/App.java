package com.skincareshop;

import java.util.Scanner;

import com.skincareshop.model.SkincareShop;
import com.skincareshop.model.other.Products;
import com.skincareshop.model.other.ProductsItem;
import com.skincareshop.model.staff.Staff;
import com.skincareshop.service.CartService;

public class App 
{
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        SkincareShop shop = new SkincareShop("Blosoom", "Phnom Penh");

        // shop.createStaff(null, null, null, null, null, null);

        // System.out.println(shop);

        // Object o1 = new Object();
        // Staff s1 = new Staff("S001", "Admin", "010000000", "admin", "1234");
        
        // s1.equals(o1);

        //create staff
        // Staff s1 = new Staff("S001", "Admin", "010000000", "admin", "1234");
        // Staff s2 = new Staff("S002", "Barista", "010000000", "barista", "1234");
        // // System.out.println(s1);
        // // System.out.println(s2);

        // ManagerStaff m1 = new ManagerStaff(s1, 2000);
        
        // // m1.can("CREATE_CUSTOMER");
        // System.out.println(m1.can("CREATE_CUSTOMER"));
        // System.out.println(s1.can("CREATE_CUSTOMER"));
        
        // int choice;

        // do {

        //     if (!shop.isStaffLoggedIn()) {

        //         printMainMenu();

        //         System.out.print("Choose: ");
        //         choice = sc.nextInt();
        //         sc.nextLine();

        //         switch (choice) {

        //             case 1: {
        //                 System.out.print("Username: ");
        //                 String username = sc.nextLine();

        //                 System.out.print("Password: ");
        //                 String password = sc.nextLine();

        //                 shop.staffLogin(username, password);
        //                 System.out.println(shop.getLastMessage());
        //                 break;
        //             }

        //             case 2: {
        //                 shop.printMenuItems();
        //                 break;
        //             }

        //             case 0: {
        //                 System.out.println("Goodbye!");
        //                 break;
        //             }

        //             default:
        //                 System.out.println("Invalid choice.");
        //         }

        //     } else {

        //         printStaffMenu(shop);

        //         System.out.print("Choose: ");
        //         choice = sc.nextInt();
        //         sc.nextLine();

        //         switch (choice) {

        //             case 1: { // Create Staff
        //                 System.out.print("Staff ID: ");
        //                 String staffId = sc.nextLine();

        //                 System.out.print("Full Name: ");
        //                 String fullName = sc.nextLine();

        //                 System.out.print("Phone: ");
        //                 String phone = sc.nextLine();

        //                 System.out.print("Username: ");
        //                 String username = sc.nextLine();

        //                 System.out.print("Password: ");
        //                 String password = sc.nextLine();

        //                 System.out.print("Position: ");
        //                 String position = sc.nextLine();

        //                 shop.createStaff(staffId, fullName, phone, username, password, position);
        //                 System.out.println(shop.getLastMessage());
        //                 break;
        //             }

        //             case 2: { // Create Customer
        //                 System.out.print("Customer ID: ");
        //                 String customerId = sc.nextLine();

        //                 System.out.print("Full Name: ");
        //                 String fullName = sc.nextLine();

        //                 System.out.print("Phone: ");
        //                 String phone = sc.nextLine();

        //                 System.out.print("Password: ");
        //                 String password = sc.nextLine();

        //                 System.out.print("Initial Balance: ");
        //                 double balance = sc.nextDouble();
        //                 sc.nextLine();

        //                 shop.createCustomer(customerId, fullName, phone, password, balance);
        //                 System.out.println(shop.getLastMessage());
        //                 break;
        //             }

        //             case 3: { // Create Menu Item
        //                 System.out.print("Item ID: ");
        //                 String itemId = sc.nextLine();

        //                 System.out.print("Item Name: ");
        //                 String name = sc.nextLine();

        //                 System.out.print("Category: ");
        //                 String category = sc.nextLine();

        //                 System.out.print("Size (S/M/L/None): ");
        //                 String size = sc.nextLine();

        //                 System.out.print("Price: ");
        //                 double price = sc.nextDouble();
        //                 sc.nextLine();

        //                 System.out.print("Available? (1=Yes, 0=No): ");
        //                 int a = sc.nextInt();
        //                 sc.nextLine();

        //                 boolean available = (a == 1);

        //                 shop.createMenuItem(itemId, name, category, size, price, available);
        //                 System.out.println(shop.getLastMessage());
        //                 break;
        //             }

        //             case 4: { // Set Menu Item Availability
        //                 System.out.print("Item ID: ");
        //                 String itemId = sc.nextLine();

        //                 System.out.print("Available? (1=Yes, 0=No): ");
        //                 int a = sc.nextInt();
        //                 sc.nextLine();

        //                 boolean available = (a == 1);

        //                 shop.setMenuItemAvailability(itemId, available);
        //                 System.out.println(shop.getLastMessage());
        //                 break;
        //             }

        //             case 5: { // Create Order
        //                 System.out.print("Customer phone: ");
        //                 String phone = sc.nextLine();

        //                 System.out.print("Menu item ID: ");
        //                 String itemId = sc.nextLine();

        //                 System.out.print("Quantity: ");
        //                 int qty = sc.nextInt();
        //                 sc.nextLine();

        //                 shop.createOrder(phone, itemId, qty);
        //                 System.out.println(shop.getLastMessage());
        //                 break;
        //             }

        //             case 6: { // List Customers
        //                 shop.printCustomers();
        //                 break;
        //             }

        //             case 7: { // List Menu Items
        //                 shop.printMenuItems();
        //                 break;
        //             }

        //             case 8: { // List Orders
        //                 shop.printOrders();
        //                 break;
        //             }

        //             case 9: { // Logout
        //                 shop.staffLogout();
        //                 System.out.println(shop.getLastMessage());
        //                 break;
        //             }

        //             case 0: {
        //                 System.out.println("Goodbye!");
        //                 break;
        //             }

        //             default:
        //                 System.out.println("Invalid choice.");
        //         }
        //     }

        // } while (choice != 0);

        sc.close();
    }

    // ===== Menu printing in Main (easy to customize later) =====
    private static void printMainMenu() {
        System.out.println("\n=== MAIN MENU (Not Logged In) ===");
        System.out.println("1) Staff Login");
        System.out.println("2) View Menu Items");
        System.out.println("0) Exit");
    }

    private static void printStaffMenu(SkincareShop shop) {
        System.out.println("\n=== STAFF MENU (Logged In) ===");
        System.out.println("Logged in staff: " + shop.getLoggedInStaff());
        System.out.println("1) Create Staff");
        System.out.println("2) Create Customer");
        System.out.println("3) Create Menu Item");
        System.out.println("4) Set Menu Item Availability");
        System.out.println("5) Create Order");
        System.out.println("6) List Customers");
        System.out.println("7) List Menu Items");
        System.out.println("8) List Orders");
        System.out.println("9) Logout");
        System.out.println("0) Exit");
    }
}
