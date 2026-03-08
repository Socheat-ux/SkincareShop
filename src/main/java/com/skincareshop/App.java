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
        Products serum = new Products("Serum", 12.50, 10);
        Products moisturizer = new Products("Moisturizer", 6.69, 12);
        Products cleanser = new Products("Cleanser", 8.50, 8);
        System.err.println(serum);

        CartService cart = new CartService();
        cart.addProductsItem(serum, 2);
        cart.addProductsItem(moisturizer, 1);
        cart.addProductsItem(cleanser, 3);

        // Remove
        cart.removeProductsItem(0);
        cart.printInfo();

        // Update
        cart.updateQuantityProductsItem(0, 1);
        cart.printInfo();

        Scanner sc = new Scanner(System.in);

        SkincareShop shop = new SkincareShop("Blossom", "Phnom Penh");

        shop.createStaff("S001", "Admin", "010000000", "admin", "1004","Manager");
        shop.createStaff("S002", "Cashier", "010000000", "Cashier", "1009", "Cashier");

        
        System.out.println(shop);
       
        // create staff
        Staff s1 = new Staff("S001", "Admin", "010000000", "admin", "1234");
        Staff s2 = new Staff("S002", "Barista", "010000000", "barista", "1234");
        System.out.println(s1);
        System.out.println(s2);


        sc.close();
    
    }
}
