// File: TrainingModel.java
package com.example.bismillah;

public class TrainingModel {
    private String title;
    private int price;
    private int imageResId;

    public TrainingModel(String title, int price, int imageResId) {
        this.title = title;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}
