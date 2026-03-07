package com.skincareshop;

import com.skincareshop.model.other.ProductsItem;
import com.skincareshop.service.CartService;

public class App 
{
    public static void main( String[] args )
    {

        ProductsItem i1 = new ProductsItem("Serum", 7.8, 2);
        ProductsItem i2 = new ProductsItem("Cream", 12, 5);
        ProductsItem i3 = new ProductsItem("Sunscreen", 8.5, 10);   

        CartService cart = new CartService();
        cart.addProductsItem("Serum", 7.8, 2);
        cart.addProductsItem("Cream", 12, 4);
        cart.addProductsItem("Sunscreen", 8.5, 1);

        // Remove
        cart.removeProductsItem(0);
        cart.printInfo();

        // Update
        cart.updateQuantityProductsItem(0, 1);
        cart.printInfo();
    }
}
