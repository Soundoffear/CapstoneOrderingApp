package com.example.soundoffear.capstoneorderingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PaidAddsModel implements Parcelable {

    private String paidAddName;
    private String paidAddPrice;

    private boolean isSelected;

    public PaidAddsModel(String paidAddName, String paidAddPrice) {
        this.paidAddName = paidAddName;
        this.paidAddPrice = paidAddPrice;
    }

    protected PaidAddsModel(Parcel in) {
        paidAddName = in.readString();
        paidAddPrice = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<PaidAddsModel> CREATOR = new Creator<PaidAddsModel>() {
        @Override
        public PaidAddsModel createFromParcel(Parcel in) {
            return new PaidAddsModel(in);
        }

        @Override
        public PaidAddsModel[] newArray(int size) {
            return new PaidAddsModel[size];
        }
    };

    public String getPaidAddName() {
        return paidAddName;
    }

    public String getPaidAddPrice() {
        return paidAddPrice;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setPaidAddName(String paidAddName) {
        this.paidAddName = paidAddName;
    }

    public void setPaidAddPrice(String paidAddPrice) {
        this.paidAddPrice = paidAddPrice;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(paidAddName);
        dest.writeString(paidAddPrice);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        PaidAddsModel paidAddsModel = (PaidAddsModel) obj;
        if(paidAddsModel.getPaidAddName().equals(this.getPaidAddName())) {
            return true;
        }

        return false;
    }
}
