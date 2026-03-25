package com.skincareshop;

import java.util.Scanner;

import com.skincareshop.model.SkincareShop;
import com.skincareshop.model.other.Products;
import com.skincareshop.model.other.ProductsItem;
import com.skincareshop.model.staff.Staff;
import com.skincareshop.service.CartService;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       SKINCARE SHOP - FULL DEMO      ║");
        System.out.println("╚══════════════════════════════════════╝");
 
        SkincareShop shop = new SkincareShop("Glow Shop", "Phnom Penh");
        System.out.println("[INIT] " + shop.getLastMessage());
 
        // ─────────────────────────────────────────
        // 1. LOGIN as admin (Manager)
        // ─────────────────────────────────────────
        System.out.println("\n── 1. LOGIN ──");
        shop.staffLogin("admin", "1234");
        System.out.println(shop.getLastMessage());
 
        // ─────────────────────────────────────────
        // 2. CREATE STAFF
        // ─────────────────────────────────────────
        System.out.println("\n── 2. CREATE STAFF ──");
        shop.createStaff("S002", "Sopheap", "012345678", "sopheap", "pass1234", "Cashier");
        System.out.println(shop.getLastMessage());
 
        shop.createStaff("S003", "Dara", "016789012", "dara", "pass5678", "Manager");
        System.out.println(shop.getLastMessage());
 
        // ─────────────────────────────────────────
        // 3. ANONYMOUS INNER CLASS — guest viewer
        // ─────────────────────────────────────────
        System.out.println("\n── 3. ANONYMOUS INNER CLASS (guest viewer) ──");
        shop.addGuestViewer();
        System.out.println(shop.getLastMessage());
        // The guest's can() is defined inline with no separate class file
 
        // ─────────────────────────────────────────
        // 4. CREATE CUSTOMERS
        // ─────────────────────────────────────────
        System.out.println("\n── 4. CREATE CUSTOMERS ──");
        shop.createCustomer("C001", "Maly", "017111111", "maly123", 100.00);
        System.out.println(shop.getLastMessage());
 
        shop.createCustomer("C002", "Bopha", "018222222", "bopha456", 50.00);
        System.out.println(shop.getLastMessage());
 
        // ─────────────────────────────────────────
        // 5. CREATE PRODUCTS
        // ─────────────────────────────────────────
        System.out.println("\n── 5. CREATE PRODUCTS ──");
        shop.createProductItem("P001", "Aloe Vera Gel",    "Moisturizer", 5.99,  20, true);
        System.out.println(shop.getLastMessage());
 
        shop.createProductItem("P002", "Rose Toner",       "Toner",       8.50,  15, true);
        System.out.println(shop.getLastMessage());
 
        shop.createProductItem("P003", "Vitamin C Serum",  "Serum",       15.00, 10, true);
        System.out.println(shop.getLastMessage());
 
        shop.createProductItem("P004", "SPF 50 Sunscreen", "Sunscreen",   12.00, 8,  false);
        System.out.println(shop.getLastMessage());
 
        // ─────────────────────────────────────────
        // 6. FUNCTIONAL INTERFACE + LAMBDA — filter products
        // ─────────────────────────────────────────
        System.out.println("\n── 6. FUNCTIONAL INTERFACE + LAMBDA ──");
 
        System.out.println("  → Available products only:");
        shop.printFilteredProducts(p -> p.isAvailable());
 
        System.out.println("  → Products under $10:");
        shop.printFilteredProducts(p -> p.getPrice() < 10);
 
        System.out.println("  → Serums:");
        shop.printFilteredProducts(p -> p.getCategory().equals("Serum"));
 
        // ─────────────────────────────────────────
        // 7. CALCULATE PROMOTION (method overloading)
        // ─────────────────────────────────────────
        System.out.println("\n── 7. PROMOTIONS ──");
        shop.calculatePromotion("P003", 10.0);         // 10% off
        System.out.println(shop.getLastMessage());
 
        shop.calculatePromotion("P003", 2.0, true);    // $2 fixed discount
        System.out.println(shop.getLastMessage());
 
        shop.calculatePromotion("P003", 10.0, 3);      // 10% off, buy 3
        System.out.println(shop.getLastMessage());
 
        // ─────────────────────────────────────────
        // 8. CREATE ORDER — manager places order
        // ─────────────────────────────────────────
        System.out.println("\n── 8. CREATE ORDER (as Manager) ──");
        CartService cart = new CartService();
        cart.addProductItem(getProduct(shop, "P001"), 2);  // 2x Aloe Vera
        cart.addProductItem(getProduct(shop, "P003"), 1);  // 1x Vitamin C Serum
        cart.printInfo();
 
        shop.createOrder("C001", cart);
        System.out.println(shop.getLastMessage());
 
        // ─────────────────────────────────────────
        // 9. LOGOUT & LOGIN as cashier, place another order
        // ─────────────────────────────────────────
        System.out.println("\n── 9. CREATE ORDER (as Cashier) ──");
        shop.staffLogout();
        shop.staffLogin("sopheap", "pass1234");
        System.out.println(shop.getLastMessage());
 
        CartService cart2 = new CartService();
        cart2.addProductsItem(getProduct(shop, "P002"), 3); // 3x Rose Toner
        shop.createOrder("C002", cart2);
        System.out.println(shop.getLastMessage());
 
        // ─────────────────────────────────────────
        // 10. VIEW ORDERS (cashier can view)
        // ─────────────────────────────────────────
        System.out.println("\n── 10. VIEW ORDERS ──");
        shop.printOrders();
 
        // ─────────────────────────────────────────
        // 11. CASHIER CANNOT CREATE STAFF (permission denied)
        // ─────────────────────────────────────────
        System.out.println("\n── 11. PERMISSION DENIED TEST ──");
        shop.createStaff("S099", "Hacker", "011000000", "hacker", "1234", "Cashier");
        System.out.println(shop.getLastMessage());  

        // ─────────────────────────────────────────
        // 12. PRINT ALL DATA
        // ─────────────────────────────────────────
        System.out.println("\n── 12. FINAL STATE ──");
        shop.staffLogout();
        shop.staffLogin("admin", "1234");
 
        shop.printCustomers();
        shop.printProductItems();
        shop.printOrders();
 
        System.out.println("\n[SHOP] " + shop);
    }
 
    // Helper: get a product from the shop by ID
    private static com.skincareshop.model.other.Products getProduct(SkincareShop shop, String id) {
        return shop.findProductById(id);
    }
    
}
