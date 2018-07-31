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
import com.example.soundoffear.capstoneorderingapp.adapters.CateringAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnCateringItemSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.CateringModel;
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

public class CateringFragments extends Fragment implements OnCateringItemSelectedListener {

    @BindView(R.id.catering_recyclerView_2)
    RecyclerView catering_recyclerView;
    private List<CateringModel> cateringModelList;

    private CateringModel cateringModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_catering, container,false);
        ButterKnife.bind(this, view);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_CATERING);

        cateringModelList = new ArrayList<>();

        catering_recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        catering_recyclerView.setLayoutManager(linearLayoutManager);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> cateringData = (Map<String, String>) dataSnapshot.getValue();
                for(Map.Entry<String, String> catering: cateringData.entrySet()) {
                    CateringModel cateringModel = new CateringModel(catering.getKey(), String.valueOf(catering.getValue()));
                    cateringModelList.add(cateringModel);
                }

                CateringAdapter_RV cateringAdapter_rv = new CateringAdapter_RV(getContext(), cateringModelList, CateringFragments.this, false);
                catering_recyclerView.setAdapter(cateringAdapter_rv);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onCateringItemSelected(CateringModel cateringModel) {
        this.cateringModel = cateringModel;
    }

    public CateringModel getCateringModel() {
        return cateringModel;
    }
}
