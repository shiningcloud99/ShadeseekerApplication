package com.example.myapplication;

public class ItemModel {
    String brandname, productname, shade;
    int productimage;

    public ItemModel(String brandname, String productname, String shade, int productimage) {
        this.brandname = brandname;
        this.productname = productname;
        this.shade = shade;
        this.productimage = productimage;
    }

    public String getBrandname() {
        return brandname;
    }

    public String getProductname() {
        return productname;
    }

    public String getShade() {
        return shade;
    }

    public int getProductimage() {
        return productimage;
    }
}
