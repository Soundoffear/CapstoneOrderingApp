package com.example.soundoffear.capstoneorderingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SandwichModel implements Parcelable {

    private String sandwichName;
    private String sandwichPrice;
    private String sandwichDescription;
    private boolean isSelected;

    public SandwichModel(String sandwichName, String sandwichPrice, String sandwichDescription) {
        this.sandwichName = sandwichName;
        this.sandwichPrice = sandwichPrice;
        this.sandwichDescription = sandwichDescription;
    }

    protected SandwichModel(Parcel in) {
        sandwichName = in.readString();
        sandwichPrice = in.readString();
        sandwichDescription = in.readString();
    }

    public static final Creator<SandwichModel> CREATOR = new Creator<SandwichModel>() {
        @Override
        public SandwichModel createFromParcel(Parcel in) {
            return new SandwichModel(in);
        }

        @Override
        public SandwichModel[] newArray(int size) {
            return new SandwichModel[size];
        }
    };

    public String getSandwichName() {
        return sandwichName;
    }

    public String getSandwichPrice() {
        return sandwichPrice;
    }

    public String getSandwichDescription() {
        return sandwichDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sandwichName);
        dest.writeString(this.sandwichPrice);
        dest.writeString(this.sandwichDescription);
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        SandwichModel sandwichModel = (SandwichModel) obj;
        if(sandwichModel.getSandwichName().equals(this.getSandwichName())) {
            return true;
        }

        return false;
    }
}
