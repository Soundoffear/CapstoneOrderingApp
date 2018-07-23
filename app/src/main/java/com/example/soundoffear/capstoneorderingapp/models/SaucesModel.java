package com.example.soundoffear.capstoneorderingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SaucesModel implements Parcelable {

    private String sauceName;
    private boolean isSelected;

    public SaucesModel(String sauceName) {
        this.sauceName = sauceName;
    }

    protected SaucesModel(Parcel in) {
        sauceName = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<SaucesModel> CREATOR = new Creator<SaucesModel>() {
        @Override
        public SaucesModel createFromParcel(Parcel in) {
            return new SaucesModel(in);
        }

        @Override
        public SaucesModel[] newArray(int size) {
            return new SaucesModel[size];
        }
    };

    public String getSauceName() {
        return sauceName;
    }

    public void setSauceName(String sauceName) {
        this.sauceName = sauceName;
    }

    public boolean isSelected() {
        return isSelected;
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
        dest.writeString(sauceName);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }
}
