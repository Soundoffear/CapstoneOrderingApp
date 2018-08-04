package com.example.soundoffear.capstoneorderingapp.fragments;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.OrderSummaryActivity;
import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.utilities.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderStatusFragment extends Fragment {

    @BindView(R.id.order_status_received)
    TextView order_status_recieved;
    @BindView(R.id.order_status_in_the_making)
    TextView order_status_in_the_making;
    @BindView(R.id.order_status_out_for_delivery)
    TextView order_status_out_for_delivery;
    @BindView(R.id.order_status_delivered)
    TextView order_status_delivered;
    @BindView(R.id.order_status_order_number)
    TextView order_status_order_number;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View orderStatusView = inflater.inflate(R.layout.fragment_order_status, container, false);
        ButterKnife.bind(this, orderStatusView);

        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final String[] lastOrder = new String[1];

        DatabaseReference dataReference = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_USERS).child(userID).child(Constants.DATABASE_LAST_ORDER);
        dataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lastOrder[0] = String.valueOf(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_USERS).child(userID).child(Constants.DATABASE_ORDERS);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> orderObject = (Map<String, Object>) dataSnapshot.getValue();
                for (Map.Entry<String, Object> order : orderObject.entrySet()) {
                    Log.d("TEST STATUS", lastOrder[0] + " " + order.getKey());
                    if (order.getKey().equals(lastOrder[0])) {
                        order_status_order_number.setText(order.getKey());
                        Log.d("ORDER IN", "ORDER HAS BEEN RECEIVED");
                        Map<String, Object> detailedObject = (Map<String, Object>) order.getValue();
                        for (Map.Entry<String, Object> details : detailedObject.entrySet()) {
                            if (details.getKey().equals(Constants.DATABASE_ORDER_STATUS)) {
                                Log.d("ORDER STATUS", String.valueOf(details.getValue()));
                                if(String.valueOf(details.getValue()).equals(Constants.DATABASE_ORDER_RECEIVED)) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        order_status_recieved.setBackground(getResources().getDrawable(R.drawable.border_layout_selected));
                                    }
                                } else if (String.valueOf(details.getValue()).equals(Constants.DATABASE_ORDER_IN_THE_MAKING)) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        order_status_recieved.setBackground(getResources().getDrawable(R.drawable.border_layout_selected));
                                        order_status_in_the_making.setBackground(getResources().getDrawable(R.drawable.border_layout_selected));
                                    }
                                } else if (String.valueOf(details.getValue()).equals(Constants.DATABASE_IN_DELIVERY)) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        order_status_recieved.setBackground(getResources().getDrawable(R.drawable.border_layout_selected));
                                        order_status_in_the_making.setBackground(getResources().getDrawable(R.drawable.border_layout_selected));
                                        order_status_out_for_delivery.setBackground(getResources().getDrawable(R.drawable.border_layout_selected));
                                    }
                                } else if (String.valueOf(details.getValue()).equals(Constants.DATABASE_DELIVERED)) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        order_status_recieved.setBackground(getResources().getDrawable(R.drawable.border_layout_selected));
                                        order_status_in_the_making.setBackground(getResources().getDrawable(R.drawable.border_layout_selected));
                                        order_status_out_for_delivery.setBackground(getResources().getDrawable(R.drawable.border_layout_selected));
                                        order_status_delivered.setBackground(getResources().getDrawable(R.drawable.border_layout_selected));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return orderStatusView;
    }
}
