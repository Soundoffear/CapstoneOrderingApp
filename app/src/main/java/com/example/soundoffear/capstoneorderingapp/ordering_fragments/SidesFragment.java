package com.example.soundoffear.capstoneorderingapp.ordering_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.adapters.SidesAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnSidesSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.SidesModel;
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

public class SidesFragment extends Fragment implements OnSidesSelectedListener {

    @BindView(R.id.sides_recyclerView)
    RecyclerView sides_recyclerView;

    private List<SidesModel> sidesModelList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewSides = inflater.inflate(R.layout.fragment_order_sides, container, false);
        ButterKnife.bind(this, viewSides);

        sidesModelList = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("sides").child("cookies");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> sidesMap = (Map<String, String>) dataSnapshot.getValue();
                for(Map.Entry<String, String> side: sidesMap.entrySet()) {
                    SidesModel sidesModel = new SidesModel(side.getKey(), String.valueOf(side.getValue()));
                    sidesModelList.add(sidesModel);

                    sides_recyclerView.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    sides_recyclerView.setLayoutManager(linearLayoutManager);
                    SidesAdapter_RV sidesAdapter_rv = new SidesAdapter_RV(sidesModelList, getContext(), true, SidesFragment.this);
                    sides_recyclerView.setAdapter(sidesAdapter_rv);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return viewSides;
    }

    @Override
    public void onSidesSelected(SidesModel sidesModel, int value) {

    }
}
