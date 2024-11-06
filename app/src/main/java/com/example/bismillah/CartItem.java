package com.example.bismillah;

public class CartItem {
    private int imageResourceId;
    private String name;
    private int price; // Price in currency, e.g., Rupiah
    private int quantity;

    public CartItem(int imageResourceId, String name, int price, int quantity) {
        this.imageResourceId = imageResourceId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void decrementQuantity() {
        if (this.quantity > 1) {
            this.quantity--;
        }
    }
}
