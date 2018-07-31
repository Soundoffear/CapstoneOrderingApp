package com.example.soundoffear.capstoneorderingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.OrderPlacingActivity;
import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.contracts.BuildSandwichContract;
import com.example.soundoffear.capstoneorderingapp.contracts.DrinksOrderContract;
import com.example.soundoffear.capstoneorderingapp.databases.DrinksOrderDatabase;
import com.example.soundoffear.capstoneorderingapp.databases.FinalSandwichDataBase;
import com.example.soundoffear.capstoneorderingapp.models.OrderTypeModel;
import com.example.soundoffear.capstoneorderingapp.utilities.Constants;
import com.example.soundoffear.capstoneorderingapp.utilities.PointsSystemClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPageFragment extends Fragment {

    public static final String LIST_CARRIERS_STRING = "list_carriers_string";
    public static final String LIST_OF_ORDER_TYPES = "list_of_orders_types";

    @BindView(R.id.progressBar_MainPage)
    ProgressBar pointsProgressBar;
    @BindView(R.id.progressBar_insideText)
    TextView pointsTextView;
    @BindView(R.id.button_new_order)
    Button newOrder_button;

    private int maximumPoints = 10000;
    private String maximumPointsString = "/10000";

    public MainPageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainPageView = inflater.inflate(R.layout.fragment_main_page, container, false);
        ButterKnife.bind(this, mainPageView);

        final FinalSandwichDataBase finalSandwichDataBase = new FinalSandwichDataBase(getContext());
        final DrinksOrderDatabase drinksOrderDatabase = new DrinksOrderDatabase(getContext());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference1 = firebaseDatabase.getReference();

        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final List<OrderTypeModel> orderTypesList = new ArrayList<>();

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(Map.Entry<String, Object> data: ((Map<String, Object>) dataSnapshot.getValue()).entrySet()) {
                    if(!data.getKey().equals("users") && !data.getKey().equals("rewards") && !data.getKey().equals("coupons")) {
                        OrderTypeModel orderTypeModel = new OrderTypeModel(data.getKey());
                        orderTypesList.add(orderTypeModel);
                    }

                }

                Log.d("TEST", String.valueOf(orderTypesList.size()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference pointsDBRefrence = firebaseDatabase.getReference().child(Constants.DATABASE_USERS).child(userID);

        pointsDBRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> userData = (Map<String, Object>) dataSnapshot.getValue();
                assert userData != null;
                Log.d("TEST DATA", String.valueOf(userData.size()));

                for(Map.Entry<String, Object> pointsData: ((Map<String, Object>) dataSnapshot.getValue()).entrySet()) {
                    if(pointsData.getKey().equals(Constants.DATABASE_USER_POINTS)) {
                        Log.d("Check Points exists", "YES");
                        String pointsDisplay = new DecimalFormat("#").format(Double.parseDouble(String.valueOf(pointsData.getValue()))) + maximumPointsString;
                        pointsTextView.setText(pointsDisplay);
                        String points = new DecimalFormat("#").format(Double.parseDouble(String.valueOf(pointsData.getValue())));
                        double pointsDataBase = Double.parseDouble(points);
                        double progressDouble = (pointsDataBase / maximumPoints) * 100;
                        int progressValue = Integer.parseInt(new DecimalFormat("#").format(progressDouble));

                        Log.d("Progress Value", String.valueOf(progressValue) + " " + progressDouble + " " + pointsDataBase);

                        pointsProgressBar.setProgress(progressValue);
                    } else {
                        Log.d("Points not exists", pointsData.getKey());

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        newOrder_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalSandwichDataBase.deleteTable(BuildSandwichContract.BuildSandwichEntry.SANDWICH_TABLE_NAME);
                drinksOrderDatabase.deleteDrinkDatabase(DrinksOrderContract.DrinksOrderEntry.DRINKS_ORDER_TABLE_NAME);
                Intent newOrderIntent = new Intent(getContext(), OrderPlacingActivity.class);
                newOrderIntent.putParcelableArrayListExtra(LIST_OF_ORDER_TYPES, (ArrayList<OrderTypeModel>) orderTypesList);
                startActivity(newOrderIntent);
            }
        });

        return mainPageView;
    }
}
