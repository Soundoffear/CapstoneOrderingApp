package com.example.soundoffear.capstoneorderingapp.models;

public class CouponsModel {

    private String name;
    private String price;
    private String url;

    public CouponsModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }
}
