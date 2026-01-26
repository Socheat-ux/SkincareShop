package com.skincareshop.model;

public class Products {
    private String name;
    private boolean inStock;
    private double price;

    //constuctor
    public Products(String name, boolean inStock, double price) {
        this.name = name;
        this.inStock = inStock;
        setPrice(price);
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price < 0.9) {
            throw new IllegalAccessException("Price cannot lower than 0.9!");
        }
        this.price = price;
    }
}
