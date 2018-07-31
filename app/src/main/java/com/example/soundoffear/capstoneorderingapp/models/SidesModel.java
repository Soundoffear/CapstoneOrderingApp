package com.example.soundoffear.capstoneorderingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SidesModel implements Parcelable {

    private String sideName;
    private String sidePrice;

    private boolean isSideSelected;

    public SidesModel(String sideName, String sidePrice) {
        this.sideName = sideName;
        this.sidePrice = sidePrice;
    }

    protected SidesModel(Parcel in) {
        sideName = in.readString();
        sidePrice = in.readString();
        isSideSelected = in.readByte() != 0;
    }

    public static final Creator<SidesModel> CREATOR = new Creator<SidesModel>() {
        @Override
        public SidesModel createFromParcel(Parcel in) {
            return new SidesModel(in);
        }

        @Override
        public SidesModel[] newArray(int size) {
            return new SidesModel[size];
        }
    };

    public String getSideName() {
        return sideName;
    }

    public String getSidePrice() {
        return sidePrice;
    }

    public boolean isSideSelected() {
        return isSideSelected;
    }

    public void setSideSelected(boolean sideSelected) {
        isSideSelected = sideSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sideName);
        dest.writeString(sidePrice);
        dest.writeByte((byte) (isSideSelected ? 1 : 0));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        SidesModel sidesModel = (SidesModel) obj;
        if(sidesModel.getSideName().equals(this.getSideName())) {
            return true;
        }
        return false;
    }
}
