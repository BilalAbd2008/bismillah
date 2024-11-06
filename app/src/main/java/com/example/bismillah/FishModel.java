package com.example.bismillah;

public class FishModel {
    private String name;
    private int image;
    private String category;
    private boolean trending;

    // Constructor
    public FishModel(String name, int image, String category, boolean trending) {
        this.name = name;
        this.image = image;
        this.category = category;
        this.trending = trending;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for image resource
    public int getImage() {
        return image;
    }

    // Getter for category
    public String getCategory() {
        return category;
    }

    // Getter for trending status
    public boolean isTrending() {
        return trending;
    }
}
