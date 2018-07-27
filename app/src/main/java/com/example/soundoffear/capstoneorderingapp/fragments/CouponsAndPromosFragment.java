package com.example.soundoffear.capstoneorderingapp.fragments;

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

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.adapters.CouponsAndPromoAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.utilities.Constants;
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

public class CouponsAndPromosFragment extends Fragment {

    @BindView(R.id.coupons_and_promo_recyclerView)
    RecyclerView coupons_and_promo_recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View couponsView = inflater.inflate(R.layout.fragment_coupons_and_promos, container, false);
        ButterKnife.bind(this, couponsView);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_COUPONS_DIRECTORY);
        final List<String> couponsList = new ArrayList<>();

        coupons_and_promo_recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        coupons_and_promo_recyclerView.setLayoutManager(linearLayoutManager);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> couponsData = (Map<String, String>) dataSnapshot.getValue();

                Log.d("COUPONS SIZE", String.valueOf(couponsData.size()));

                for(Map.Entry<String, String> couponData: couponsData.entrySet()) {
                    couponsList.add(couponData.getKey());
                }

                CouponsAndPromoAdapter_RV couponsAndPromoAdapter_rv = new CouponsAndPromoAdapter_RV(getContext(), couponsList);
                coupons_and_promo_recyclerView.setAdapter(couponsAndPromoAdapter_rv);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return couponsView;

    }
}
