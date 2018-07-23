package com.example.soundoffear.capstoneorderingapp.models;

public class DrinksModel {

    public static final int FAMILY_TYPE = 0;
    public static final int DRINK_TYPE = 1;

    private int type;
    private String name;
    private String price;
    private String value;

    private boolean isSelected;

    public DrinksModel(int type, String name, String price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public DrinksModel(int type, String name, String price, String value) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.value = value;
    }

    public String getValue() {
        return value;
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

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
