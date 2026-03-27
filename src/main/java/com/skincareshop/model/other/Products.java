package com.skincareshop.model.other;

public class Products {
    private String productId;
    private String name;
    private String category;
    private double price;
    private int stock;
    private boolean available;

    //constuctor
    public Products(String productId, String name, String category, double price, int stock, boolean available){
        setProductId(productId);
        setName(name);
        this.category = category;
        setPrice(price);
        setStock(stock);
        this.available = available;

    }

    public void setProductId(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            this.productId = "UNKNOWN";
        } else {
            this.productId = productId.trim();
        }
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

    public void reduceStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        if (quantity > stock) {
            throw new IllegalArgumentException(
                "Not enough stock for '" + name + "'. Available: " + stock + ", requested: " + quantity
            );
        }
        stock -= quantity;
    }

    public void increaseStock(int quantity) {
        if (quantity > 0) {
            stock += quantity;
        }
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    //Getter
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public boolean isAvailable() { return available; }

    @Override
    public String toString() {
        return "Products{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", available=" + available +
                '}';
    }
}
