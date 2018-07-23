package com.example.soundoffear.capstoneorderingapp.models;

public class HistoryModel {

    public static final int LABEL_TYPE = 0;
    public static final int SANDWICH_TYPE = 1;
    public static final int DRINK_TYPE = 2;

    //general
    private int type;
    private String name;
    private String price;
    //drink specific
    private String quantity;
    //sandwich specific
    private String carrier;
    private String bread;
    private String paidAddons;
    private String sauces;
    private String vegetables;

    //drinks constructor
    public HistoryModel(int type, String name, String price, String quantity) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public HistoryModel(int type, String name, String price, String carrier, String bread, String paidAddons, String sauces, String vegetables) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.carrier = carrier;
        this.bread = bread;
        this.paidAddons = paidAddons;
        this.sauces = sauces;
        this.vegetables = vegetables;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getCarrier() {
        return carrier;
    }

    public String getBread() {
        return bread;
    }

    public String getPaidAddons() {
        return paidAddons;
    }

    public String getSauces() {
        return sauces;
    }

    public String getVegetables() {
        return vegetables;
    }
}
