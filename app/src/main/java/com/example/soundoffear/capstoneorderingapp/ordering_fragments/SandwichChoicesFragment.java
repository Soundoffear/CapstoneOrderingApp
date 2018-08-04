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
import com.example.soundoffear.capstoneorderingapp.adapters.SandwichSelectedAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnSandwichSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.SandwichModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SandwichChoicesFragment extends Fragment implements OnSandwichSelectedListener {

    @BindView(R.id.order_sandwich_recyclerView)
    RecyclerView order_sandwich_recyclerView;

    public SandwichModel sandwichModelSelected = null;

    //private SandwichModel sandwichSelected = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View sandwichRecyclerView = inflater.inflate(R.layout.order_fragments_sandwich_choices, container, false);
        ButterKnife.bind(this, sandwichRecyclerView);

        Bundle dataBundle = getArguments();
        assert dataBundle != null;
        List<SandwichModel> sandwichModelList = dataBundle.getParcelableArrayList(OrderPlacingActivity.SANDWICH_CHOICES);
        String carrierChosen = dataBundle.getString(OrderPlacingActivity.SANDWICH_CARRIER_CHOSEN);

        if(sandwichModelSelected != null) {
            Log.d("TEST S0001", "+++++" + String.valueOf(sandwichModelSelected.getSandwichName()));
        }
        order_sandwich_recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        order_sandwich_recyclerView.setLayoutManager(linearLayoutManager);

        if(sandwichModelSelected != null) {
            SandwichSelectedAdapter_RV sandwichSelectedAdapter_rv = new SandwichSelectedAdapter_RV(getContext(),
                    sandwichModelList,
                    this,
                    false,
                    carrierChosen,
                    sandwichModelSelected.getSandwichName());
            order_sandwich_recyclerView.setAdapter(sandwichSelectedAdapter_rv);
        } else {
            SandwichSelectedAdapter_RV sandwichSelectedAdapter_rv = new SandwichSelectedAdapter_RV(getContext(),
                    sandwichModelList,
                    this,
                    false,
                    carrierChosen,
                    null);
            order_sandwich_recyclerView.setAdapter(sandwichSelectedAdapter_rv);
        }

        return sandwichRecyclerView;
    }

    @Override
    public void onSelectedSandwich(SandwichModel sandwichModel) {
        sandwichModelSelected = sandwichModel;
    }

    public SandwichModel getSandwichModelSelected() {
        return sandwichModelSelected;
    }

    public void setSandwichModelSelected(SandwichModel sandwichModelSelected) {
        this.sandwichModelSelected = sandwichModelSelected;
    }
}
