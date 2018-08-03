package com.example.soundoffear.capstoneorderingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.RewardModel;
import com.example.soundoffear.capstoneorderingapp.utilities.PointsSystemClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardRedeemerFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.rr_imageView)
    ImageView rr_imageView;
    @BindView(R.id.rr_pin_enter)
    EditText rr_pin_enter;
    @BindView(R.id.rr_send_to_redeem)
    Button rr_send_to_redeem;

    private int pin_match_value = 22;
    private RewardModel receivedRewardModel;
    private String userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reward_redeemer, container, false);
        ButterKnife.bind(this, view);
        rr_send_to_redeem.setOnClickListener(this);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Bundle receivedRewardModelBundle = getArguments();
        assert receivedRewardModelBundle != null;
        receivedRewardModel = receivedRewardModelBundle.getParcelable(RedeemRewardsFragment.REWARD_DATA_TO_SEND);

        assert receivedRewardModel != null;
        String imageUrl = "Rewards/"+receivedRewardModel.getRewardName()+".png";

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance("gs://orderingapp-aa10b.appspot.com");

        StorageReference storageReference = firebaseStorage.getReference();

        storageReference.child(imageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("REWARDS SUCCESS", uri.toString());
                Picasso.get().load(uri).into(rr_imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Unable to load images", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rr_send_to_redeem) {
            if(TextUtils.isEmpty(rr_pin_enter.getText().toString())) {
                Toast.makeText(getContext(), "Please Enter PIN", Toast.LENGTH_SHORT).show();
            } else {
                double pinReceived = Double.parseDouble(rr_pin_enter.getText().toString());
                if(pinReceived == pin_match_value) {
                    PointsSystemClass.removePoints(receivedRewardModel.getRewardValue(), userID);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frameLayout, new RewardRedeemerFragment())
                            .commit();
                }
            }
        }
    }
}
