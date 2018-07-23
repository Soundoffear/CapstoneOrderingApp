package com.example.soundoffear.capstoneorderingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BreadModel implements Parcelable {

    private String breadName;
    private boolean isBreadSelected = false;

    public BreadModel(String breadName) {
        this.breadName = breadName;
    }

    protected BreadModel(Parcel in) {
        breadName = in.readString();
        isBreadSelected = in.readByte() != 0;
    }

    public static final Creator<BreadModel> CREATOR = new Creator<BreadModel>() {
        @Override
        public BreadModel createFromParcel(Parcel in) {
            return new BreadModel(in);
        }

        @Override
        public BreadModel[] newArray(int size) {
            return new BreadModel[size];
        }
    };

    public String getBreadName() {
        return breadName;
    }

    public void setBreadName(String breadName) {
        this.breadName = breadName;
    }

    public void setBread(boolean selectedBread) {
        this.isBreadSelected = selectedBread;
    }

    public boolean isSelected() {
        return isBreadSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(breadName);
        dest.writeByte((byte) (isBreadSelected ? 1 : 0));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        BreadModel breadModel = (BreadModel) obj;
        if(breadModel.getBreadName().equals(this.getBreadName())) {
            return true;
        }

        return false;
    }
}
