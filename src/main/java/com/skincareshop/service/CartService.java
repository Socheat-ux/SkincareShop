package com.skincareshop.service;

import java.util.ArrayList;

import com.skincareshop.model.other.Products;
import com.skincareshop.model.other.ProductsItem;

public class CartService {
    private ArrayList<ProductsItem> cart;
    
    public CartService() {
        cart =  new ArrayList<>();
    }

    public ArrayList<ProductsItem> getItems() {
        return new ArrayList<>(cart);  
    }
 
    public int size() {
        return cart.size();
    }
 
    public void clearCart() {
        cart.clear();
    }

    public void addProductsItem(Products product, int quantity){
        for (ProductsItem item : cart) {
            if (item.getProduct().equals(product)) {
                item.increaseQuantity(quantity);
                return;
            }
        }

        if(quantity > product.getStock()){
            throw new IllegalArgumentException("Not enough stock available!");
        }
        product.reduceStock(quantity);

        cart.add(new ProductsItem(product, quantity));
    }

     public double getTotalPrice(){
        double total = 0;
        for (ProductsItem productsItem : cart) {
            total += productsItem.getTotalPrice();
        }
        return total;
    }

    public void removeProductsItem(int index){
        if (index < 0 || index >= cart.size()){
            throw new IllegalArgumentException();
        }
        cart.remove(index);
      
    }

    public void updateQuantityProductsItem(int index, int quantity){
        // Validation
        if (index < 0 || index >= cart.size()){
            throw new IllegalArgumentException();
        }
        cart.get(index).setQuantity(quantity);
    }

    public void printInfo() {
        if (cart.size() == 0) {
            System.out.println("Cart is empty.");
            return;  
        }
        System.out.println("-----Order-----");
        for (ProductsItem item : cart) {
            System.out.println("  " + item);
        }
        System.out.println("------------------------------");
        System.out.printf("  Grand Total: $%.2f%n", getTotalPrice());
    }

}
