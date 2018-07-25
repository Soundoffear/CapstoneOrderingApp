package com.example.soundoffear.capstoneorderingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CarrierModel implements Parcelable {

    private String carrierName;

    private boolean isSelectedCarrier;

    public CarrierModel(String carrierName) {
        this.carrierName = carrierName;
    }

    protected CarrierModel(Parcel in) {
        carrierName = in.readString();
        isSelectedCarrier = in.readByte() != 0;
    }

    public static final Creator<CarrierModel> CREATOR = new Creator<CarrierModel>() {
        @Override
        public CarrierModel createFromParcel(Parcel in) {
            return new CarrierModel(in);
        }

        @Override
        public CarrierModel[] newArray(int size) {
            return new CarrierModel[size];
        }
    };

    public String getCarrierName() {
        return carrierName;
    }

    public boolean isSelectedCarrier() {
        return isSelectedCarrier;
    }

    public void setSelectedCarrier(boolean selectedCarrier) {
        isSelectedCarrier = selectedCarrier;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(carrierName);
        dest.writeByte((byte) (isSelectedCarrier ? 1 : 0));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        CarrierModel carrierModel = (CarrierModel) obj;
        return carrierModel.getCarrierName().equals(this.getCarrierName());
    }
}
