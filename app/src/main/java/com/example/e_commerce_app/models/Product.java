package com.example.e_commerce_app.models;

public class Product {
    private String id;
    private String title;
    private String description;
    private double price;
    private String imageURL;

    public Product(String title, String description, double price, String imageURL) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
    }

    public void setId(String id) { this.id = id; }

    public String getId() { return  this.id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
