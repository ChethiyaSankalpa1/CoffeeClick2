package com.example.coffeeclick;

import java.io.Serializable;
public class Coffee implements Serializable {
    private String name;
    private int price;
    private int imageResId;
    private String description;
    private String category;
    private boolean isPopular;
    private float rating;
    private int quantity; // track quantity selected

    // Original constructor for backward compatibility
    public Coffee(String name, int price, int imageResId, String description) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.description = description;
        this.category = "Hot";
        this.isPopular = false;
        this.rating = 4.5f;
        this.quantity = 0;
    }

    // New constructor with all fields
    public Coffee(String name, int price, int imageResId, String description, 
                  String category, boolean isPopular, float rating) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.description = description;
        this.category = category;
        this.isPopular = isPopular;
        this.rating = rating;
        this.quantity = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isPopular() {
        return isPopular;
    }

    public void setPopular(boolean popular) {
        isPopular = popular;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
