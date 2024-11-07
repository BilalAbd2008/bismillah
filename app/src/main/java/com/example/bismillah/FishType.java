package com.example.bismillah;

public class FishType {
    private int id;
    private String name;
    private String category;
    private int imageResId;

    public FishType(int id, String name, String category, int imageResId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.imageResId = imageResId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getImageResId() {
        return imageResId;
    }
}