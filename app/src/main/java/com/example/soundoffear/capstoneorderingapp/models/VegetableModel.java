package com.example.soundoffear.capstoneorderingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class VegetableModel implements Parcelable {

    private String vegetable;
    private boolean isSelected = false;

    public VegetableModel(String vegetable) {
        this.vegetable = vegetable;
    }

    protected VegetableModel(Parcel in) {
        vegetable = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<VegetableModel> CREATOR = new Creator<VegetableModel>() {
        @Override
        public VegetableModel createFromParcel(Parcel in) {
            return new VegetableModel(in);
        }

        @Override
        public VegetableModel[] newArray(int size) {
            return new VegetableModel[size];
        }
    };

    public String getVegetable() {
        return vegetable;
    }

    public void setVegetable(String vegetable) {
        this.vegetable = vegetable;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vegetable);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }
}
