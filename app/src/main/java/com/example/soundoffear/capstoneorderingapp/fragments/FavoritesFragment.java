package com.example.soundoffear.capstoneorderingapp.fragments;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import com.example.soundoffear.capstoneorderingapp.OrderPlacingActivity;
import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.adapters.FavoritesAdapter_RecyclerView;
import com.example.soundoffear.capstoneorderingapp.contracts.BuildSandwichContract;
import com.example.soundoffear.capstoneorderingapp.contracts.CateringOrderContract;
import com.example.soundoffear.capstoneorderingapp.contracts.DrinksOrderContract;
import com.example.soundoffear.capstoneorderingapp.contracts.SidesOrderContract;
import com.example.soundoffear.capstoneorderingapp.databases.CateringOrderDatabase;
import com.example.soundoffear.capstoneorderingapp.databases.DrinksOrderDatabase;
import com.example.soundoffear.capstoneorderingapp.databases.FinalSandwichDataBase;
import com.example.soundoffear.capstoneorderingapp.databases.SidesOrderDatabase;
import com.example.soundoffear.capstoneorderingapp.models.FinalSandwichModel;
import com.example.soundoffear.capstoneorderingapp.widget.FavoritesWidget;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class FavoritesFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.favorites_recyclerView)
    RecyclerView favorites_recyclerView;
    @BindView(R.id.favorites_add)
    FloatingActionButton favorites_add;

    public static boolean isAddingFav = false;

    private List<FinalSandwichModel> finalSandwichModelList;
    FinalSandwichDataBase finalSandwichDataBase;
    DrinksOrderDatabase drinksOrderDatabase;
    SidesOrderDatabase sidesOrderDatabase;
    CateringOrderDatabase cateringOrderDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View favoritesView = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, favoritesView);
        finalSandwichModelList = new ArrayList<>();

        finalSandwichDataBase = new FinalSandwichDataBase(getContext());
        drinksOrderDatabase = new DrinksOrderDatabase(getContext());
        sidesOrderDatabase = new SidesOrderDatabase(getContext());
        cateringOrderDatabase = new CateringOrderDatabase(getContext());

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String userID = firebaseUser.getUid();

        favorites_recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        favorites_recyclerView.setLayoutManager(linearLayoutManager);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("favorites");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map<String, Object> favoriteSandwiches = (Map<String, Object>) dataSnapshot.getValue();
                if(favoriteSandwiches == null) {
                    Snackbar.make(favorites_recyclerView, "No Favorites Has Been Added", Snackbar.LENGTH_LONG).show();
                } else {
                    for(Map.Entry<String, Object> sandwichesFavs: favoriteSandwiches.entrySet()) {
                        Map<String, Object> sandwich = (Map<String, Object>) sandwichesFavs.getValue();
                        FinalSandwichModel finalSandwichModel = new FinalSandwichModel(sandwichesFavs.getKey(), sandwich.get("carrier").toString(), sandwich.get("sandwich").toString(),
                                sandwich.get("bread").toString(), sandwich.get("vegetables").toString(), sandwich.get("sauces").toString(), sandwich.get("paidAddOns").toString(),
                                sandwich.get("finalPrice").toString());
                        finalSandwichModelList.add(finalSandwichModel);
                    }
                    FavoritesAdapter_RecyclerView favoritesAdapter_recyclerView = new FavoritesAdapter_RecyclerView(getContext(), finalSandwichModelList);
                    favorites_recyclerView.setAdapter(favoritesAdapter_recyclerView);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        favorites_add.setOnClickListener(this);

        return favoritesView;
    }

    @Override
    public void onClick(View v) {
        isAddingFav = true;
        finalSandwichDataBase.deleteTable(BuildSandwichContract.BuildSandwichEntry.SANDWICH_TABLE_NAME);
        drinksOrderDatabase.deleteDrinkDatabase(DrinksOrderContract.DrinksOrderEntry.DRINKS_ORDER_TABLE_NAME);
        sidesOrderDatabase.deleteSidesTable(SidesOrderContract.SidesContractEntry.SIDES_TABLE_NAME);
        cateringOrderDatabase.deleteDatabase(CateringOrderContract.CateringOrderEntry.CATERING_TABLE_NAME);
        Intent intentToAddToFavs = new Intent(getActivity(), OrderPlacingActivity.class);
        startActivity(intentToAddToFavs);
    }
}
