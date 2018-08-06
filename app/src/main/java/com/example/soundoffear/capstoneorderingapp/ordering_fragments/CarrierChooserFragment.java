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
import com.example.soundoffear.capstoneorderingapp.fragments.FavoritesFragment;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnCarrierSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.CarrierModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarrierChooserFragment extends Fragment implements OnCarrierSelectedListener {

    @BindView(R.id.carrier_recyclerView)
    RecyclerView carrier_recyclerView;

    private String selectedCarrier = null;
    private List<CarrierModel> typesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_carrier, container, false);
        ButterKnife.bind(this, view);

        carrier_recyclerView.hasFixedSize();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        carrier_recyclerView.setLayoutManager(gridLayoutManager);
        Bundle bundle = getArguments();
        assert bundle != null;

        if(FavoritesFragment.isAddingFav) {
            Log.d("TEST", "TESTING FAVS");
            typesList = new ArrayList<>();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("sandwiches");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Map<String, Object> carriersData = (Map<String, Object>) dataSnapshot.child("types").getValue();
                    assert carriersData != null;
                    for (Map.Entry<String, Object> carrier : carriersData.entrySet()) {
                        String carrierName = String.valueOf(carrier.getValue());
                        CarrierModel carrierModel = new CarrierModel(carrierName);
                        typesList.add(carrierModel);
                    }
                    SandwichCarrierAdapter_RV sandwichCarrierAdapter_rv = new SandwichCarrierAdapter_RV(getContext(),
                            typesList, CarrierChooserFragment.this, false, selectedCarrier);
                    carrier_recyclerView.setAdapter(sandwichCarrierAdapter_rv);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            typesList = bundle.getParcelableArrayList(OrderPlacingActivity.SANDWICH_CARRIERS);
            SandwichCarrierAdapter_RV sandwichCarrierAdapter_rv = new SandwichCarrierAdapter_RV(getContext(),
                    typesList, CarrierChooserFragment.this, false, selectedCarrier);
            carrier_recyclerView.setAdapter(sandwichCarrierAdapter_rv);
        }

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
