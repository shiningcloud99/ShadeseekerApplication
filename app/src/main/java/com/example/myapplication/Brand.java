package com.example.myapplication;

public class Brand {
    private String id;
    private String name;
    private String description;
    private String imageUrl;

    public Brand() {
        // Default constructor required for calls to DataSnapshot.getValue(Brand.class)
    }

    public Brand(String id, String name, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
