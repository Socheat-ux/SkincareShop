package com.skincareshop.service;

import java.util.ArrayList;

import com.skincareshop.model.other.Products;
import com.skincareshop.model.other.ProductsItem;

public class CartService {

    private ArrayList<ProductsItem> cart;

    public CartService() {
        cart = new ArrayList<>();
    }

    // =========================
    // GETTERS
    // =========================
    public ArrayList<ProductsItem> getItems() {
        return new ArrayList<>(cart); // return copy (safe)
    }

    public int size() {
        return cart.size();
    }

    public void clearCart() {
        cart.clear();
    }

    // =========================
    // ADD TO CART
    // =========================
    public void addProductsItem(Products product, int quantity) {

        if (product == null) {
            throw new IllegalArgumentException("Product is null!");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0!");
        }

        // check if already exists in cart
        for (ProductsItem item : cart) {

            if (item.getProduct().equals(product)) {

                int newQty = item.getQuantity() + quantity;

                if (newQty > product.getStock()) {
                    throw new IllegalArgumentException("Not enough stock available!");
                }

                item.increaseQuantity(quantity);
                product.reduceStock(quantity); // reduce stock
                return;
            }
        }

        // new item
        if (quantity > product.getStock()) {
            throw new IllegalArgumentException("Not enough stock available!");
        }

        product.reduceStock(quantity); // reduce stock
        cart.add(new ProductsItem(product, quantity));
    }


    // =========================
    // REMOVE ITEM
    // =========================
    public void removeProductsItem(int index) {

        if (index < 0 || index >= cart.size()) {
            throw new IllegalArgumentException("Invalid index!");
        }

        ProductsItem item = cart.get(index);

        // restore stock
        item.getProduct().increaseStock(item.getQuantity());

        cart.remove(index);
    }

    // =========================
    // UPDATE QUANTITY
    // =========================
    public void updateQuantityProductsItem(int index, int newQuantity) {

        if (index < 0 || index >= cart.size()) {
            throw new IllegalArgumentException("Invalid index!");
        }

        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0!");
        }

        ProductsItem item = cart.get(index);
        Products product = item.getProduct();

        int oldQuantity = item.getQuantity();

        if (newQuantity > oldQuantity) {

            int diff = newQuantity - oldQuantity;

            if (diff > product.getStock()) {
                throw new IllegalArgumentException("Not enough stock available!");
            }

            product.reduceStock(diff);

        } else {

            int diff = oldQuantity - newQuantity;
            product.increaseStock(diff);
        }

        item.setQuantity(newQuantity);
    }

    // =========================
    // TOTAL PRICE
    // =========================
    public double getTotalPrice() {

        double total = 0;

        for (ProductsItem item : cart) {
            total += item.getTotalPrice();
        }

        return total;
    }
    

    // =========================
    // PRINT CART
    // =========================
    public void printInfo() {

        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("\n----- CART -----");

        int i = 1;
        for (ProductsItem item : cart) {
            System.out.println(i + ") " + item);
            i++;
        }

        System.out.println("------------------------------");
        System.out.printf("Grand Total: $%.2f%n", getTotalPrice());
    }
}