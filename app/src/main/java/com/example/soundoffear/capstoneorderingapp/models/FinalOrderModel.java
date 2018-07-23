package com.example.soundoffear.capstoneorderingapp.models;

public class FinalOrderModel {

    private String sandwiches;
    private String drinks;
    private String sides;

    public FinalOrderModel(String sandwiches, String drinks, String sides) {
        this.sandwiches = sandwiches;
        this.drinks = drinks;
        this.sides = sides;
    }

    public String getSandwiches() {
        return sandwiches;
    }

    public String getDrinks() {
        return drinks;
    }

    public String getSides() {
        return sides;
    }
}
