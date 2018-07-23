package com.example.soundoffear.capstoneorderingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.DrinksModel;
import com.example.soundoffear.capstoneorderingapp.models.FinalSandwichModel;
import com.example.soundoffear.capstoneorderingapp.models.SandwichModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderHistoryFragment extends Fragment {

    @BindView(R.id.order_history_recyclerView)
    RecyclerView order_history_recyclerView;

    DrinksModel orderedDrink;
    FinalSandwichModel orderedSandwich;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View orderHistoryView = inflater.inflate(R.layout.fragment_order_history, container, false);
        ButterKnife.bind(this, orderHistoryView);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String userID = firebaseUser.getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users").child(userID).child("orders");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> ordersNumbers = (Map<String, Object>) dataSnapshot.getValue();

                for(Map.Entry<String, Object> order: ordersNumbers.entrySet()) {
                    Log.d("HISTORY TEST", order.getKey());
                    Map<String, Object> orderItems = (Map<String, Object>) order.getValue();
                    for(Map.Entry<String, Object> orderItem: orderItems.entrySet()) {
                        String item = orderItem.getKey();
                        Log.d("TEST ITEMS", item);
                        if(item.contains("Drink")) {
                            Map<String, String> drinkItem = (Map<String, String>) orderItem.getValue();
                            for(Map.Entry<String, String> drinkDetails: drinkItem.entrySet()) {
                                Log.d("DRINK DETAILS", drinkDetails.getKey() + " " + String.valueOf(drinkDetails.getValue()));
                            }
                        }
                        if(item.contains("Sub")) {

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return orderHistoryView;
    }
}
