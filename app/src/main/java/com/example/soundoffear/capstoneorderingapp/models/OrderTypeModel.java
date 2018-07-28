package com.example.soundoffear.capstoneorderingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderTypeModel implements Parcelable {

    private String orderType;

    private boolean isSelected;

    public OrderTypeModel(String orderType) {
        this.orderType = orderType;
    }

    protected OrderTypeModel(Parcel in) {
        orderType = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<OrderTypeModel> CREATOR = new Creator<OrderTypeModel>() {
        @Override
        public OrderTypeModel createFromParcel(Parcel in) {
            return new OrderTypeModel(in);
        }

        @Override
        public OrderTypeModel[] newArray(int size) {
            return new OrderTypeModel[size];
        }
    };

    public String getOrderType() {
        return orderType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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
        dest.writeString(orderType);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        OrderTypeModel orderTypeModel = (OrderTypeModel) obj;
        return orderTypeModel.getOrderType().equals(this.getOrderType());
    }
}
