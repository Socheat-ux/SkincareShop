package com.skincareshop.model;

public class ProductsItem {
    private Products product;
    private int quantity;

    public ProductsItem(Products product, int quantity){
        this.product = product;
        setQuantity(quantity);
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }
        this.quantity = quantity;
    }

    public Products getProduct() {
        return new Products(product.getName(), product.getPrice());
    }

    public int getQuantity() {
        return quantity;
    }


    public double getTotalPrice(){
        return quantity * product.getPrice();
    }



    @Override
    public String toString() {
        return product.getName() + " " + getQuantity() + " ($" + getTotalPrice() + ")";
    }
}

