package com.example.soundoffear.capstoneorderingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RewardModel implements Parcelable {

    private String rewardName;
    private String rewardValue;

    public RewardModel(String rewardName, String rewardValue) {
        this.rewardName = rewardName;
        this.rewardValue = rewardValue;
    }

    protected RewardModel(Parcel in) {
        rewardName = in.readString();
        rewardValue = in.readString();
    }

    public static final Creator<RewardModel> CREATOR = new Creator<RewardModel>() {
        @Override
        public RewardModel createFromParcel(Parcel in) {
            return new RewardModel(in);
        }

        @Override
        public RewardModel[] newArray(int size) {
            return new RewardModel[size];
        }
    };

    public String getRewardName() {
        return rewardName;
    }

    public String getRewardValue() {
        return rewardValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rewardName);
        dest.writeString(rewardValue);
    }
}
