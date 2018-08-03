package com.example.soundoffear.capstoneorderingapp.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.UserDataModel;
import com.example.soundoffear.capstoneorderingapp.utilities.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryAddressEntryFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.user_data_user_name_input)
    TextView user_data_name;
    @BindView(R.id.user_data_user_surname_input)
    TextView user_data_surname;
    @BindView(R.id.user_data_user_phone_input)
    TextView user_data_phone;
    @BindView(R.id.user_data_user_email_input)
    TextView user_data_email;
    @BindView(R.id.user_data_user_delivery_address_street_input)
    TextView user_data_street;
    @BindView(R.id.user_data_user_delivery_address_number_input)
    TextView user_data_number;
    @BindView(R.id.user_data_user_delivery_address_city_input)
    TextView user_data_city;
    @BindView(R.id.user_data_save_btn)
    Button user_data_save_btn;
    @BindView(R.id.user_data_cancel_btn)
    Button user_data_cancel_btn;

    @BindView(R.id.user_data_user_add_data_button)
    Button user_data_user_add_data_button;

    @BindView(R.id.user_data_root_view)
    ConstraintLayout user_data_root_view;

    DatabaseReference databaseReference;

    private View deliveryAddressView;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        deliveryAddressView = inflater.inflate(R.layout.fragment_delivery_address, container, false);
        ButterKnife.bind(this, deliveryAddressView);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        user_data_save_btn.setOnClickListener(this);
        user_data_cancel_btn.setOnClickListener(this);

        final ConstraintSet constraintSet = new ConstraintSet();

        user_data_user_add_data_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View newAddress = inflater.inflate(R.layout.fragment_user_data_additional_address, container, false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    newAddress.setId(View.generateViewId());
                    user_data_root_view.addView(newAddress);
                    constraintSet.clone(user_data_root_view);
                    constraintSet.connect(newAddress.getId(), ConstraintSet.TOP, user_data_city.getId(), ConstraintSet.BOTTOM, 0);
                    constraintSet.connect(user_data_user_add_data_button.getId(), ConstraintSet.TOP, newAddress.getId(), ConstraintSet.BOTTOM, 0);
                    constraintSet.applyTo(user_data_root_view);
                    user_data_user_add_data_button.setVisibility(View.GONE);
                }

            }
        });
        return deliveryAddressView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.user_data_save_btn:
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String userID = firebaseUser.getUid();
                if(deliveryAddressView.findViewById(R.id.user_data_user_delivery_address_city_input_2) != null) {
                    String delivery_address_street_2 = ((EditText) deliveryAddressView.findViewById(R.id.user_data_user_delivery_address_street_input_2)).getText().toString();
                    String delivery_address_number_2 = ((EditText) deliveryAddressView.findViewById(R.id.user_data_user_delivery_address_number_input_2)).getText().toString();
                    String delivery_address_city_2 = ((EditText) deliveryAddressView.findViewById(R.id.user_data_user_delivery_address_city_input_2)).getText().toString();
                    UserDataModel userDataModel = new UserDataModel(user_data_name.getText().toString(),
                            user_data_surname.getText().toString(),
                            user_data_phone.getText().toString(),
                            user_data_email.getText().toString(),
                            user_data_street.getText().toString(),
                            user_data_number.getText().toString(),
                            user_data_city.getText().toString(),
                            delivery_address_street_2,
                            delivery_address_number_2,
                            delivery_address_city_2);
                    databaseReference.child(Constants.DATABASE_USERS).child(userID).child("user_data").setValue(userDataModel);
                } else {
                    UserDataModel userDataModel = new UserDataModel(user_data_name.getText().toString(),
                            user_data_surname.getText().toString(),
                            user_data_phone.getText().toString(),
                            user_data_email.getText().toString(),
                            user_data_street.getText().toString(),
                            user_data_number.getText().toString(),
                            user_data_city.getText().toString());
                    databaseReference.child(Constants.DATABASE_USERS).child(userID).child("user_data").setValue(userDataModel);
                }

            case R.id.user_data_cancel_btn:

        }
    }
}
