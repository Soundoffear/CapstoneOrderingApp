package com.example.soundoffear.capstoneorderingapp.ordering_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.OrderPlacingActivity;
import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.adapters.SandwichPaidAddsAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnNumberOfPaidAddOnsListener;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnPaidAddsSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.PaidAddsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaidAddsFragment extends Fragment implements OnPaidAddsSelectedListener, OnNumberOfPaidAddOnsListener {

    @BindView(R.id.paid_adds_recyclerView)
    RecyclerView paid_adds_recyclerView;

    List<String> paidAddOnsList;
    private String finalPaidString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_paidadds, container, false);
        ButterKnife.bind(this, view);

        paidAddOnsList = new ArrayList<>();

        Bundle bundle = getArguments();
        assert bundle != null;
        List<PaidAddsModel> paidAddsModelList = bundle.getParcelableArrayList(OrderPlacingActivity.SANDWICH_PAID_ADDS_BUNDLE);
        String carrierChosen = bundle.getString(OrderPlacingActivity.SANDWICH_CARRIER_CHOSEN);

        paid_adds_recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        paid_adds_recyclerView.setLayoutManager(linearLayoutManager);
        SandwichPaidAddsAdapter_RV sandwichPaidAddsAdapter_rv = new SandwichPaidAddsAdapter_RV(getContext(),
                paidAddsModelList,
                true,
                this,
                carrierChosen,
                this);
        paid_adds_recyclerView.setAdapter(sandwichPaidAddsAdapter_rv);
        return view;
    }

    @Override
    public void onSelectedPaidAdd(PaidAddsModel paidAddsModel, int value) {
        Log.d("TEST", paidAddsModel.getPaidAddName());
    }

    @Override
    public void onFinalNumberSelected(PaidAddsModel paidAddsModel, int numberOfSelected, String finalPaidString) {
        StringBuilder builtString = new StringBuilder();
        builtString.append(paidAddsModel.getPaidAddName()).append("_").append(numberOfSelected).append("_").append(finalPaidString);
        finalPaidString = builtString.toString();
        for (int i = 0; i < paidAddOnsList.size(); i++) {
            String[] splitString = paidAddOnsList.get(i).split("_");
            if (paidAddsModel.getPaidAddName().equals(splitString[0])) {
                paidAddOnsList.remove(paidAddOnsList.get(i));
            }
        }
        if (numberOfSelected > 0) {
            paidAddOnsList.add(finalPaidString);
        }
        for(int i = 0; i < paidAddOnsList.size(); i++) {
            Log.d("TEST TEST", paidAddOnsList.get(i));
        }
    }

    public String getPaidAddsData() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < paidAddOnsList.size(); i++) {
            if(i < paidAddOnsList.size() - 1) {
                stringBuilder.append(paidAddOnsList.get(i)).append("-");
            } else {
                stringBuilder.append(paidAddOnsList.get(i));
            }
        }
        return stringBuilder.toString();
    }
}
