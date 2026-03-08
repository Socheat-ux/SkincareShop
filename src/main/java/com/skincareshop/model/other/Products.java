package com.skincareshop.model.other;

public class Products {
    private String name;
    private double price;
    private int stock;

    //constuctor
    public Products(String name, double price, int stock){
        setName(name);
        setPrice(price);
        setStock(stock);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product don't have a name yet!");
        }
        this.name = name;
    }

    public void setPrice(double price) {
        if(price < 0.9) {
            throw new IllegalArgumentException("Price cannot lower than 0.9!");
        }
        this.price = price;
    }

    public void setStock(int stock) {
        if(stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative!");
        }
        this.stock = stock;
    }

    public void reduceStock(int quantity){
        if(quantity > stock){
            throw new IllegalArgumentException("Not enough stock!");
        }
        stock -= quantity;
    }

    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getStock() {
        return stock;
    }
}
