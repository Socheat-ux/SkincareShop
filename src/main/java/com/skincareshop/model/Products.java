package com.skincareshop.model;

public class Products {
    private String name;
    private double price;

    //constuctor
    public Products(String name, double price){
        setName(name);
        setPrice(price);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product don't have a name yet!");
        }
        this.name = name;
    }

    public void setPrice(double price) {
        if(price < 0.9) {
            throw new IllegalAccessException("Price cannot lower than 0.9!");
        }
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
}
