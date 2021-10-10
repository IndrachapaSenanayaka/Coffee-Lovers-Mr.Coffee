package com.example.coffee_lovers_mrcoffee.data.models;

public class Promotion {

    private  String name;
    private  Float price;
    private  String startDate;
    private String endDate;
    private String description;
    private String imageUrl;
    private String imageID;

    public Promotion() {
    }

    public Promotion(String name, Float price, String startDate, String endDate, String description, String imageUrl, String imageID) {
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.imageUrl = imageUrl;
        this.imageID = imageID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageID() {
        return imageID;
    }
}
