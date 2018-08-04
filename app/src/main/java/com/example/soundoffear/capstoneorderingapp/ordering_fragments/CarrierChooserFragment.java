package com.example.soundoffear.capstoneorderingapp.ordering_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.OrderPlacingActivity;
import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.adapters.SandwichCarrierAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnCarrierSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.CarrierModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarrierChooserFragment extends Fragment implements OnCarrierSelectedListener {

    @BindView(R.id.carrier_recyclerView)
    RecyclerView carrier_recyclerView;

    private String selectedCarrier = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_carrier, container, false);
        ButterKnife.bind(this, view);

        carrier_recyclerView.hasFixedSize();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        carrier_recyclerView.setLayoutManager(gridLayoutManager);

        Bundle bundle = getArguments();

        Log.d("CARRIER 0000", "++++++" + String.valueOf(selectedCarrier));

        assert bundle != null;
        final List<CarrierModel> typesList = bundle.getParcelableArrayList(OrderPlacingActivity.SANDWICH_CARRIERS);

        SandwichCarrierAdapter_RV sandwichCarrierAdapter_rv = new SandwichCarrierAdapter_RV(getContext(),
                typesList, CarrierChooserFragment.this, false, selectedCarrier);
        carrier_recyclerView.setAdapter(sandwichCarrierAdapter_rv);

        return view;
    }

    @Override
    public void onSelectedCarrier(CarrierModel carrierModel) {
        selectedCarrier = carrierModel.getCarrierName();
    }

    public String getSelectedCarrier() {
        return selectedCarrier;
    }

    public void setSelectedCarrier(String selectedCarrier) {
        this.selectedCarrier = selectedCarrier;
    }
}
