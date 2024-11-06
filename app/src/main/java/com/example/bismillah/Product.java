package com.example.bismillah;

import java.io.Serializable;

public class Product implements Serializable {
    private int imageResourceId;
    private String name;
    private String price;
    private String description;

    public Product(int imageResourceId, String name, String price, String description) {
        this.imageResourceId = imageResourceId;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}

