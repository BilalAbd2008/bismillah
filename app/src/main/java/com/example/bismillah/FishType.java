package com.example.bismillah;

public class FishType {
    private int imageResId;
    private String name;
    private String category;

    public FishType(int imageResId, String name, String category) {
        this.imageResId = imageResId;
        this.name = name;
        this.category = category;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
