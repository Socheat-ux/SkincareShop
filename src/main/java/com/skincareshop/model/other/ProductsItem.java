package com.skincareshop.model.other;

public class ProductsItem {
    Products product;
    private int quantity;

    //constuctor
    public ProductsItem(Products product, int quantity){
        setProduct(product);
        setQuantity(quantity);
    }

    public void setProduct(Products product){
        if(product == null){
            throw new IllegalArgumentException("Product cannot be null!");
        }
        this.product = product;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0!");
        }
        this.quantity = quantity;
    }

    public void increaseQuantity(int quantity) {
        setQuantity(this.quantity + quantity);
    }

    public Products getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice(){
        return quantity * product.getPrice();
    }


    @Override
    public String toString() {
        return product.getName() + " " + quantity + " ($" + getTotalPrice() + ")";
    }
}

