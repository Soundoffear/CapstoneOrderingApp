package com.example.soundoffear.capstoneorderingapp.models;

public class FinalSandwichModel {

    private String carrier;
    private String sandwich;
    private String bread;
    private String vegetables;
    private String sauces;
    private String paidAddOns;
    private String finalPrice;

    public FinalSandwichModel(String carrier, String sandwich, String bread, String vegetables, String sauces, String paidAddOns, String finalPrice) {
        this.carrier = carrier;
        this.sandwich = sandwich;
        this.bread = bread;
        this.vegetables = vegetables;
        this.sauces = sauces;
        this.paidAddOns = paidAddOns;
        this.finalPrice = finalPrice;
    }

    public String getCarrier() {
        return carrier;
    }

    public String getSandwich() {
        return sandwich;
    }

    public String getBread() {
        return bread;
    }

    public String getVegetables() {
        return vegetables;
    }

    public String getSauces() {
        return sauces;
    }

    public String getPaidAddOns() {
        return paidAddOns;
    }

    public String getFinalPrice() {
        return finalPrice;
    }
}
