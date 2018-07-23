package com.example.soundoffear.capstoneorderingapp.interfaces;

import com.example.soundoffear.capstoneorderingapp.models.PaidAddsModel;

public interface OnNumberOfPaidAddOnsListener {

    void onFinalNumberSelected(PaidAddsModel paidAddsModel, int numberOfSelected, String price);

}
