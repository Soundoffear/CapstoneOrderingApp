package com.example.soundoffear.capstoneorderingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CateringModel implements Parcelable {

    private String cateringName;
    private String cateringPrice;

    private boolean isSelectedCatering;

    public CateringModel(String cateringName, String cateringPrice) {
        this.cateringName = cateringName;
        this.cateringPrice = cateringPrice;
    }

    protected CateringModel(Parcel in) {
        cateringName = in.readString();
        cateringPrice = in.readString();
        isSelectedCatering = in.readByte() != 0;
    }

    public static final Creator<CateringModel> CREATOR = new Creator<CateringModel>() {
        @Override
        public CateringModel createFromParcel(Parcel in) {
            return new CateringModel(in);
        }

        @Override
        public CateringModel[] newArray(int size) {
            return new CateringModel[size];
        }
    };

    public String getCateringName() {
        return cateringName;
    }

    public String getCateringPrice() {
        return cateringPrice;
    }

    public boolean isSelectedCatering() {
        return isSelectedCatering;
    }

    public void setSelectedCatering(boolean selectedCatering) {
        isSelectedCatering = selectedCatering;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cateringName);
        dest.writeString(cateringPrice);
        dest.writeByte((byte) (isSelectedCatering ? 1 : 0));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        CateringModel model = (CateringModel) obj;
        if(model.getCateringName().equals(this.getCateringName())) {
            return true;
        }
        return false;
    }
}
