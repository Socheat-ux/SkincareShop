package com.skincareshop.service;

import java.util.ArrayList;

import com.skincareshop.model.other.Products;
import com.skincareshop.model.other.ProductsItem;

public class CartService {
    private ArrayList<ProductsItem> cart;
    
    public CartService() {
        cart =  new ArrayList<>();
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

    public void printInfo(){
        if(cart.size() == 0) {
            System.out.println("No order yet!");
        }
        System.out.println("-------------Print Info-------------");
        for (ProductsItem productsItem : cart) {
            System.out.println(productsItem);
        }
        System.out.println("------------------------------------");
        System.out.println("\tGrand Total: $" + getTotalPrice());

    }

}
