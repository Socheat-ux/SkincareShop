package com.skincareshop;

import com.skincareshop.model.other.Products;
import com.skincareshop.model.other.ProductsItem;
import com.skincareshop.service.CartService;

public class App 
{
    public static void main( String[] args )
    {

        Products serum = new Products("Serum", 10.5);
        ProductsItem item = new ProductsItem(serum, 2);
        System.out.println(item);
        System.out.println("\n");


        CartService cart = new CartService();
        Products cream = new Products("Cream", 12);
        Products sunscreen = new Products("Sunscreen", 8.5);
        
        cart.addProductsItem(cream, 2);
        cart.addProductsItem(sunscreen, 1);
        cart.printInfo();

        // Remove
        cart.removeProductsItem(0);
        cart.printInfo();

        // Update
        cart.updateQuantityProductsItem(0, 1);
        cart.printInfo();
    }
}
