package com.example.soundoffear.capstoneorderingapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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

public class SettingsFragment extends Fragment {

    private static final String SAVED_SPINNER_POSITION = "saved_spinner_position";
    private static final String SHARED_STRING = "shared_prefs";

    @BindView(R.id.settings_address_spinner)
    Spinner settings_address_spinner;
    @BindView(R.id.settings_small_warrning)
    TextView settings_small_warning;

    @BindView(R.id.user_data_user_delivery_address_street_output_2)
    TextView user_data_street;
    @BindView(R.id.user_data_user_delivery_address_number_output_2)
    TextView user_data_number;
    @BindView(R.id.user_data_user_delivery_address_city_output_2)
    TextView user_data_city;
    @BindView(R.id.user_data_user_delivery_address_label_2)
    TextView user_data_delivery_address_label_2;

    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View settingsView = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, settingsView);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        sharedPreferences = getContext().getSharedPreferences(SHARED_STRING, Context.MODE_PRIVATE);

        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_USERS).child(userID).child("user_data");

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Map<String, Object> user_data = (Map<String, Object>) dataSnapshot.getValue();

                if (!TextUtils.isEmpty(user_data.get("userAddressStreet2").toString())) {
                    settings_small_warning.setVisibility(View.GONE);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.addresses));
                    settings_address_spinner.setAdapter(arrayAdapter);
                    final int savedPosition = sharedPreferences.getInt(SAVED_SPINNER_POSITION, 0);

                    settings_address_spinner.setSelection(savedPosition);

                    if (savedPosition == 0) {
                        user_data_delivery_address_label_2.setText(R.string.delivery_address);
                        user_data_street.setText(user_data.get("userAddressStreet").toString());
                        user_data_number.setText(user_data.get("userAddressNumber").toString());
                        user_data_city.setText(user_data.get("userAddressCity").toString());
                    } else if (savedPosition == 1) {
                        user_data_delivery_address_label_2.setText(R.string.delivery_address_2);
                        user_data_street.setText(user_data.get("userAddressStreet2").toString());
                        user_data_number.setText(user_data.get("userAddressNumber2").toString());
                        user_data_city.setText(user_data.get("userAddressCity2").toString());
                    }


                    settings_address_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                user_data_delivery_address_label_2.setText(R.string.delivery_address);
                                user_data_street.setText(user_data.get("userAddressStreet").toString());
                                user_data_number.setText(user_data.get("userAddressNumber").toString());
                                user_data_city.setText(user_data.get("userAddressCity").toString());
                                sharedPreferences.edit().putInt(SAVED_SPINNER_POSITION, position).apply();
                            } else if (position == 1) {
                                user_data_delivery_address_label_2.setText(R.string.delivery_address_2);
                                user_data_street.setText(user_data.get("userAddressStreet2").toString());
                                user_data_number.setText(user_data.get("userAddressNumber2").toString());
                                user_data_city.setText(user_data.get("userAddressCity2").toString());
                                sharedPreferences.edit().putInt(SAVED_SPINNER_POSITION, position).apply();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else {
                    settings_address_spinner.setVisibility(View.GONE);

                    user_data_street.setText(user_data.get("userAddressStreet").toString());
                    user_data_number.setText(user_data.get("userAddressNumber").toString());
                    user_data_city.setText(user_data.get("userAddressCity").toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return settingsView;
    }
}
