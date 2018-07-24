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

import com.example.soundoffear.capstoneorderingapp.MainActivity;
import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.UserDataModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDataOutputFragment extends Fragment {

    @BindView(R.id.user_data_user_name_output)
    TextView user_name_output;
    @BindView(R.id.user_data_user_surname_output) TextView user_surname_output;
    @BindView(R.id.user_data_user_phone_output) TextView user_phone_output;
    @BindView(R.id.user_data_user_email_output) TextView user_email_output;
    @BindView(R.id.user_data_user_delivery_address_street_output) TextView user_street_output;
    @BindView(R.id.user_data_user_delivery_address_number_output) TextView user_number_output;
    @BindView(R.id.user_data_user_delivery_address_city_output) TextView user_city_output;

    @BindView(R.id.user_data_edit_btn)
    Button user_data_edit_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View userDataView = inflater.inflate(R.layout.fragment_user_data_output, container,false);
        ButterKnife.bind(this, userDataView);

        Bundle bundle = getArguments();
        assert bundle != null;
        UserDataModel userDataModel = bundle.getParcelable(MainActivity.USER_DATA_BUNDLE);

        assert userDataModel != null;
        user_name_output.setText(userDataModel.getUserName());
        user_surname_output.setText(userDataModel.getUserSurname());
        user_phone_output.setText(userDataModel.getUserPhone());
        user_email_output.setText(userDataModel.getUserEmail());
        user_street_output.setText(userDataModel.getUserAddressStreet());
        user_number_output.setText(userDataModel.getUserAddressNumber());
        user_city_output.setText(userDataModel.getUserAddressCity());

        user_data_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new DeliveryAddressEntryFragment()).commit();
            }
        });

        return userDataView;
    }
}
