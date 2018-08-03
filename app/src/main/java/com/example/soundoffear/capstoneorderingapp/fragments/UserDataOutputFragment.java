package com.example.soundoffear.capstoneorderingapp.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

    @BindView(R.id.user_data_output_root_view)
    ConstraintLayout user_data_output_root_view;

    @BindView(R.id.user_data_user_name_output)
    TextView user_name_output;
    @BindView(R.id.user_data_user_surname_output)
    TextView user_surname_output;
    @BindView(R.id.user_data_user_phone_output)
    TextView user_phone_output;
    @BindView(R.id.user_data_user_email_output)
    TextView user_email_output;
    @BindView(R.id.user_data_user_delivery_address_street_output)
    TextView user_street_output;
    @BindView(R.id.user_data_user_delivery_address_number_output)
    TextView user_number_output;
    @BindView(R.id.user_data_user_delivery_address_city_output)
    TextView user_city_output;

    @BindView(R.id.user_data_edit_btn)
    Button user_data_edit_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View userDataView = inflater.inflate(R.layout.fragment_user_data_output, container, false);
        ButterKnife.bind(this, userDataView);

        Bundle bundle = getArguments();
        assert bundle != null;
        UserDataModel userDataModel = bundle.getParcelable(MainActivity.USER_DATA_BUNDLE);
        ConstraintSet constraintSet = new ConstraintSet();
        assert userDataModel != null;
        if(!TextUtils.isEmpty(userDataModel.getUserAddressStreet2())) {
            View newAddressView = inflater.inflate(R.layout.fragment_user_data_additional_address_output, container, false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                newAddressView.setId(View.generateViewId());
                user_data_output_root_view.addView(newAddressView);
                constraintSet.clone(user_data_output_root_view);
                constraintSet.connect(newAddressView.getId(), ConstraintSet.TOP, user_city_output.getId(), ConstraintSet.BOTTOM, 0);
                constraintSet.applyTo(user_data_output_root_view);

                user_name_output.setText(userDataModel.getUserName());
                user_surname_output.setText(userDataModel.getUserSurname());
                user_phone_output.setText(userDataModel.getUserPhone());
                user_email_output.setText(userDataModel.getUserEmail());
                user_street_output.setText(userDataModel.getUserAddressStreet());
                user_number_output.setText(userDataModel.getUserAddressNumber());
                user_city_output.setText(userDataModel.getUserAddressCity());
                ((TextView) userDataView.findViewById(R.id.user_data_user_delivery_address_street_output_2)).setText(userDataModel.getUserAddressStreet2());
                ((TextView) userDataView.findViewById(R.id.user_data_user_delivery_address_number_output_2)).setText(userDataModel.getUserAddressNumber2());
                ((TextView) userDataView.findViewById(R.id.user_data_user_delivery_address_city_output_2)).setText(userDataModel.getUserAddressCity2());

            }
        } else {
            user_name_output.setText(userDataModel.getUserName());
            user_surname_output.setText(userDataModel.getUserSurname());
            user_phone_output.setText(userDataModel.getUserPhone());
            user_email_output.setText(userDataModel.getUserEmail());
            user_street_output.setText(userDataModel.getUserAddressStreet());
            user_number_output.setText(userDataModel.getUserAddressNumber());
            user_city_output.setText(userDataModel.getUserAddressCity());
        }

        user_data_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new DeliveryAddressEntryFragment()).addToBackStack(null).commit();
            }
        });

        return userDataView;
    }
}
