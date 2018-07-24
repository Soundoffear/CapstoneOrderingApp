package com.example.soundoffear.capstoneorderingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDataModel implements Parcelable {

    private String userName;
    private String userSurname;
    private String userPhone;
    private String userEmail;
    private String userAddressStreet;
    private String userAddressNumber;
    private String userAddressCity;

    public UserDataModel(String userName, String userSurname, String userPhone, String userEmail, String userAddressStreet, String userAddressNumber, String userAddressCity) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userAddressStreet = userAddressStreet;
        this.userAddressNumber = userAddressNumber;
        this.userAddressCity = userAddressCity;
    }

    protected UserDataModel(Parcel in) {
        userName = in.readString();
        userSurname = in.readString();
        userPhone = in.readString();
        userEmail = in.readString();
        userAddressStreet = in.readString();
        userAddressNumber = in.readString();
        userAddressCity = in.readString();
    }

    public static final Creator<UserDataModel> CREATOR = new Creator<UserDataModel>() {
        @Override
        public UserDataModel createFromParcel(Parcel in) {
            return new UserDataModel(in);
        }

        @Override
        public UserDataModel[] newArray(int size) {
            return new UserDataModel[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserAddressStreet() {
        return userAddressStreet;
    }

    public String getUserAddressNumber() {
        return userAddressNumber;
    }

    public String getUserAddressCity() {
        return userAddressCity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userSurname);
        dest.writeString(userPhone);
        dest.writeString(userEmail);
        dest.writeString(userAddressStreet);
        dest.writeString(userAddressNumber);
        dest.writeString(userAddressCity);
    }
}
