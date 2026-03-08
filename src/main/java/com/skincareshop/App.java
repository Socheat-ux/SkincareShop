package com.skincareshop;

import com.skincareshop.model.other.Products;
import com.skincareshop.model.other.ProductsItem;
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
    }
}
