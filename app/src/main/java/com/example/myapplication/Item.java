package com.example.myapplication;

public class Item {
    public String heading;
    public String imageUrl;

    public Item(String heading, String imageUrl) {
        this.heading = heading;
        this.imageUrl = imageUrl;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
