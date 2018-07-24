package com.example.soundoffear.capstoneorderingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.UserDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryAddressEntryFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.user_data_user_name_input) TextView user_data_name;
    @BindView(R.id.user_data_user_surname_input) TextView user_data_surname;
    @BindView(R.id.user_data_user_phone_input) TextView user_data_phone;
    @BindView(R.id.user_data_user_email_input) TextView user_data_email;
    @BindView(R.id.user_data_user_delivery_address_street_input) TextView user_data_street;
    @BindView(R.id.user_data_user_delivery_address_number_input) TextView user_data_number;
    @BindView(R.id.user_data_user_delivery_address_city_input) TextView user_data_city;
    @BindView(R.id.user_data_save_btn)
    Button user_data_save_btn;
    @BindView(R.id.user_data_cancel_btn) Button user_data_cancel_btn;

    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View deliveryAddressView = inflater.inflate(R.layout.fragment_delivery_address, container, false);
        ButterKnife.bind(this, deliveryAddressView);



        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        user_data_save_btn.setOnClickListener(this);
        user_data_cancel_btn.setOnClickListener(this);

        return deliveryAddressView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.user_data_save_btn:
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String userID = firebaseUser.getUid();

                UserDataModel userDataModel = new UserDataModel(user_data_name.getText().toString(),
                        user_data_surname.getText().toString(),
                        user_data_phone.getText().toString(),
                        user_data_email.getText().toString(),
                        user_data_street.getText().toString(),
                        user_data_number.getText().toString(),
                        user_data_city.getText().toString());

                databaseReference.child("users").child(userID).child("user_data").setValue(userDataModel);

            case R.id.user_data_cancel_btn:

        }
    }
}
