package com.example.soundoffear.capstoneorderingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soundoffear.capstoneorderingapp.adapters.DrinksAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.adapters.DrinksOrderSummaryAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.adapters.FinalSandwichAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.databases.DrinksOrderDatabase;
import com.example.soundoffear.capstoneorderingapp.databases.FinalSandwichDataBase;
import com.example.soundoffear.capstoneorderingapp.models.DrinksModel;
import com.example.soundoffear.capstoneorderingapp.models.FinalOrderModel;
import com.example.soundoffear.capstoneorderingapp.models.FinalSandwichModel;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderSummaryActivity extends AppCompatActivity {

    @BindView(R.id.order_summary_recyclerView)
    RecyclerView order_summary_recyclerView;
    @BindView(R.id.order_summary_drinks_recyclerView)
    RecyclerView order_summary_drinks_recyclerView;
    @BindView(R.id.order_summary_total_price_output)
    TextView order_summary_total_price_output;

    @BindView(R.id.order_summary_fabMenu) FloatingActionsMenu floatingActionsMenu;
    @BindView(R.id.order_summary_button_add_drink) FloatingActionButton fab_add_drink;
    @BindView(R.id.order_summary_button_add_sandwich) FloatingActionButton fab_add_sandwich;
    @BindView(R.id.order_summary_button_add_sides) FloatingActionButton fab_add_sides;

    @BindView(R.id.order_summary_send_order)
    Button order_summary_send_button;

    private String drinksString;
    private String subsString;

    private int countDrinks = 0;
    private int countSubs = 0;

    private double countTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        ButterKnife.bind(this);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userID = firebaseUser.getUid();


        FinalSandwichDataBase finalSandwichDataBase = new FinalSandwichDataBase(getApplicationContext());
        DrinksOrderDatabase drinksOrderDatabase = new DrinksOrderDatabase(getApplicationContext());

        final List<FinalSandwichModel> finalSandwichModelList = finalSandwichDataBase.getAllFinalSandwichData();
        final List<DrinksModel> drinksModelList = drinksOrderDatabase.getAllDrinksData();
        if(finalSandwichModelList.size() > 0) {
            order_summary_recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            order_summary_recyclerView.setLayoutManager(linearLayoutManager);
            FinalSandwichAdapter_RV finalSandwichAdapter_rv = new FinalSandwichAdapter_RV(this, finalSandwichModelList);
            order_summary_recyclerView.setAdapter(finalSandwichAdapter_rv);
            for(FinalSandwichModel finalSandwichModel: finalSandwichModelList) {
                countTotalPrice = countTotalPrice+Double.parseDouble(finalSandwichModel.getFinalPrice());
            }
        } else {
            order_summary_recyclerView.setVisibility(View.GONE);
        }

        if(drinksModelList.size() > 0) {
            order_summary_drinks_recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
            order_summary_drinks_recyclerView.setLayoutManager(linearLayoutManager1);
            DrinksOrderSummaryAdapter_RV drinksOrderSummaryAdapter_rv = new DrinksOrderSummaryAdapter_RV(this, drinksModelList);
            order_summary_drinks_recyclerView.setAdapter(drinksOrderSummaryAdapter_rv);
            for(DrinksModel drinksModel: drinksModelList) {
                countTotalPrice = countTotalPrice + Double.parseDouble(drinksModel.getPrice());
            }
        } else {
            order_summary_drinks_recyclerView.setVisibility(View.GONE);
        }

        order_summary_total_price_output.setText(new DecimalFormat("0.00").format(countTotalPrice));

        fab_add_drink.setIconDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_add_circle_outline_black_24dp));
        fab_add_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrinksAdapter_RV.isOrderingMoreDrinks =true;
                Intent intent = new Intent(OrderSummaryActivity.this, OrderDrinksActivity.class);
                startActivity(intent);
            }
        });

        fab_add_sandwich.setIconDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_add_circle_outline_black_24dp));
        fab_add_sandwich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderPlacingActivity.isOrderingAdditionalSandwich = true;
                Intent intent = new Intent(OrderSummaryActivity.this, OrderPlacingActivity.class);
                startActivity(intent);
            }
        });

        fab_add_sides.setIconDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_add_circle_outline_black_24dp));
        fab_add_sides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "TEST Sides", Toast.LENGTH_SHORT).show();
            }
        });

        order_summary_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SENDING DATA", "Sending data");
                String orderNumber = String.valueOf(new Date(System.currentTimeMillis()));
                if(drinksModelList.size() > 1) {
                    for(int i = 0; i < drinksModelList.size();i++) {
                        countDrinks++;
                        drinksString = "Drink"+countDrinks;
                        databaseReference.child("users").child(userID).child("orders").child(orderNumber).child(drinksString).setValue(drinksModelList.get(i));
                    }
                }

                if(finalSandwichModelList.size() > 0) {
                    for(int i = 0;i < finalSandwichModelList.size(); i++) {
                        countSubs++;
                        subsString = "Sub"+countSubs;
                        databaseReference.child("users").child(userID).child("orders").child(orderNumber).child(subsString).setValue(finalSandwichModelList.get(i));
                    }
                }
            }
        });
    }
}
