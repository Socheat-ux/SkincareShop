package com.skincareshop.model.other;

public class ProductsItem {
    private String name;
    private double price;
    private int quantity;

    //constuctor
    public ProductsItem(String name, double price, int quantity){
        setName(name);
        setPrice(price);
        setQuantity(quantity);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product don't have a name yet!");
        }
        this.name = name;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        if(price < 0.9) {
            throw new IllegalArgumentException("Price cannot lower than 0.9!");
        }
        this.price = price;
    }

    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice(){
        return quantity * getPrice();
    }


    @Override
    public String toString() {
        return getName() + " " + getQuantity() + " ($" + getTotalPrice() + ")";
    }
}

