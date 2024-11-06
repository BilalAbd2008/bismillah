package com.example.bismillah;

public class FishMartItem {
    private int imageResId;
    private String name;
    private String price;
    private String description;

    public FishMartItem(int imageResId, String name, String price, String description) {
        this.imageResId = imageResId;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // Getters
    public int getImageResId() { return imageResId; }
    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getDescription() { return description; }
}
