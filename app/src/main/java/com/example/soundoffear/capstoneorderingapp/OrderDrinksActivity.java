package com.example.soundoffear.capstoneorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.soundoffear.capstoneorderingapp.adapters.DrinksAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.databases.DrinksOrderDatabase;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnDrinksSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.DrinksModel;
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

public class OrderDrinksActivity extends AppCompatActivity implements OnDrinksSelectedListener {

    @BindView(R.id.order_drinks_recyclerView)
    RecyclerView order_drinks_recyclerView;
    @BindView(R.id.order_drinks_cancel_order)
    Button order_drinks_cancel_order;
    @BindView(R.id.order_drinks_send_order)
    Button order_drinks_send_order;
    @BindView(R.id.order_drinks_toolbar)
    Toolbar order_drinks_toolbar;

    List<DrinksModel> drinks_list;
    String finalStringPerDrink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_drinks);
        ButterKnife.bind(this);

        final DrinksOrderDatabase drinksOrderDatabase = new DrinksOrderDatabase(getApplicationContext());

        setSupportActionBar(order_drinks_toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Drink Orders");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        drinks_list = new ArrayList<>();

        final List<DrinksModel> drinkTypes = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        order_drinks_recyclerView.setHasFixedSize(true);
        order_drinks_recyclerView.setLayoutManager(linearLayoutManager);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> drinks = (Map<String, Object>) dataSnapshot.child("drinks").getValue();

                assert drinks != null;
                //first will get all families of drinks available
                for(Map.Entry<String, Object> drinkType: drinks.entrySet()) {
                    DrinksModel drinksModel = new DrinksModel(DrinksModel.FAMILY_TYPE, drinkType.getKey(), null);
                    drinkTypes.add(drinksModel);
                    Log.d("TEST", drinkType.getKey());
                    if(!drinkType.getKey().equals("softdrinks")) {
                        // this loop will get all drinks besides softdrinks and their prices
                        for (Map.Entry<String, Double> drinkT : ((Map<String, Double>) drinkType.getValue()).entrySet()) {
                            DrinksModel drinksModel1 = new DrinksModel(DrinksModel.DRINK_TYPE, drinkT.getKey(), String.valueOf(drinkT.getValue()));
                            drinkTypes.add(drinksModel1);
                            Log.d("Test Inside", drinkT.getKey() + " " + drinkT.getValue());
                        }
                    } else {
                        // this loop will get us families of families inside softdrinks
                        for(Map.Entry<String, Object> drinkSoft: ((Map<String, Object>) drinkType.getValue()).entrySet()) {
                            DrinksModel drinksModelSoftDrinkFamily = new DrinksModel(DrinksModel.FAMILY_TYPE, drinkSoft.getKey(), null);
                            drinkTypes.add(drinksModelSoftDrinkFamily);
                            Log.d("Family of Family", drinkSoft.getKey());
                            // this will loop inside softdrinks families
                            for(Map.Entry<String, Double> softDrinks: ((Map<String, Double>) drinkSoft.getValue()).entrySet()) {
                                DrinksModel softDrinksModel = new DrinksModel(DrinksModel.DRINK_TYPE, softDrinks.getKey(), String.valueOf(softDrinks.getValue()));
                                drinkTypes.add(softDrinksModel);
                                Log.d("Softdrinks", softDrinks.getKey() + " " + softDrinks.getValue());
                            }
                        }
                    }
                }

                Log.d("TEST Drinks", " " + drinkTypes.size());

                DrinksAdapter_RV drinksAdapter_rv = new DrinksAdapter_RV(getApplicationContext(), drinkTypes, OrderDrinksActivity.this, true);
                order_drinks_recyclerView.setAdapter(drinksAdapter_rv);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        order_drinks_send_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < drinks_list.size(); i++) {
                    drinksOrderDatabase.insertDrinksDatatoDatabase(drinks_list.get(i));
                }
                Intent intent = new Intent(OrderDrinksActivity.this, OrderSummaryActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDrinksSelected(DrinksModel drinksModel, int value) {

        Log.d("DRINK_SEND", drinksModel.getName() + " " + drinksModel.getPrice() + " " + value);
        for(int i = 0; i < drinks_list.size(); i++) {
            if(drinks_list.get(i).getName().equals(drinksModel.getName())) {
                drinks_list.remove(drinks_list.get(i));
            }
        }
        if(value > 0) {
            DrinksModel drinksM = new DrinksModel(drinksModel.getType(), drinksModel.getName(), drinksModel.getPrice(), String.valueOf(value));
            drinks_list.add(drinksM);
        }
        for(int i = 0; i < drinks_list.size();i++) {
            Log.d("DRINKS TEST", drinks_list.get(i).getName() + " " + drinks_list.get(i).getPrice() + " " + drinks_list.get(i).getValue());
        }
    }

}
