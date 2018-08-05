package com.example.soundoffear.capstoneorderingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.adapters.OrdersHistoryAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.models.DrinksModel;
import com.example.soundoffear.capstoneorderingapp.models.FinalSandwichModel;
import com.example.soundoffear.capstoneorderingapp.models.HistoryModel;
import com.example.soundoffear.capstoneorderingapp.models.SandwichModel;
import com.example.soundoffear.capstoneorderingapp.utilities.Constants;
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

public class OrderHistoryFragment extends Fragment {

    @BindView(R.id.order_history_recyclerView)
    RecyclerView order_history_recyclerView;

    private HistoryModel historyModel;

    private List<HistoryModel> historyModelList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View orderHistoryView = inflater.inflate(R.layout.fragment_order_history, container, false);
        ButterKnife.bind(this, orderHistoryView);

        historyModelList = new ArrayList<>();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String userID = firebaseUser.getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users").child(userID).child("orders");

        order_history_recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        order_history_recyclerView.setLayoutManager(linearLayoutManager);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> ordersNumbers = (Map<String, Object>) dataSnapshot.getValue();

                if (ordersNumbers != null) {
                    for (Map.Entry<String, Object> order : ordersNumbers.entrySet()) {
                        historyModel = new HistoryModel(HistoryModel.LABEL_TYPE, order.getKey());
                        historyModelList.add(historyModel);
                        Log.d("TEST HISTORY", String.valueOf(order.getKey()));
                        if (!order.getKey().equals(Constants.DATABASE_ORDER_STATUS)) {
                            Log.d("TEST PROBLEM", String.valueOf(order.getValue() + " KEY:" + order.getKey()));
                            Map<String, Object> orderItems = (Map<String, Object>) order.getValue();
                            for (Map.Entry<String, Object> orderItem : orderItems.entrySet()) {
                                String item = String.valueOf(orderItem.getKey());
                                if (item.contains("Drink")) {
                                    Map<String, String> drinkItem = (Map<String, String>) orderItem.getValue();
                                    historyModel = new HistoryModel(HistoryModel.DRINK_TYPE, drinkItem.get("name"), drinkItem.get("price"), drinkItem.get("value"));
                                    historyModelList.add(historyModel);
                                }
                                if (item.contains("Sub")) {
                                    Map<String, String> subItems = (Map<String, String>) orderItem.getValue();
                                    historyModel = new HistoryModel(HistoryModel.SANDWICH_TYPE, subItems.get("sandwich"), subItems.get("finalPrice"), subItems.get("carrier"), subItems.get("bread"),
                                            subItems.get("paidAddOns"), subItems.get("sauces"), subItems.get("vegetables"));
                                    historyModelList.add(historyModel);
                                }
                                if (item.contains("Sides")) {
                                    Map<String, String> sideItems = (Map<String, String>) orderItem.getValue();
                                    historyModel = new HistoryModel(HistoryModel.SIDE_TYPE, sideItems.get("sideName"), sideItems.get("sidePrice"), sideItems.get("sideNumber"));
                                    historyModelList.add(historyModel);
                                }
                                if (item.contains("Catering")) {
                                    Map<String, String> cateringItems = (Map<String, String>) orderItem.getValue();
                                    historyModel = new HistoryModel(HistoryModel.CATERING_TYPE, cateringItems.get("cateringName"), cateringItems.get("cateringPrice"));
                                    historyModelList.add(historyModel);
                                }
                            }
                        }
                    }
                } else {
                    Snackbar.make(order_history_recyclerView, "There is no order history", Snackbar.LENGTH_SHORT).show();
                }
                for (int i = 0; i < historyModelList.size(); i++) {
                    Log.d("H M check", historyModelList.get(i).getName() + " | " + historyModelList.get(i).getType());
                }

                OrdersHistoryAdapter_RV ordersHistoryAdapter_rv = new OrdersHistoryAdapter_RV(getContext(), historyModelList);
                order_history_recyclerView.setAdapter(ordersHistoryAdapter_rv);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return orderHistoryView;
    }
}
