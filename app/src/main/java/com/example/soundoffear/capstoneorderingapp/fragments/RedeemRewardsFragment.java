package com.example.soundoffear.capstoneorderingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.adapters.RewardsAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnRewardSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.RewardModel;
import com.example.soundoffear.capstoneorderingapp.utilities.Constants;
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

public class RedeemRewardsFragment extends Fragment implements OnRewardSelectedListener {

    public static final String REWARD_DATA_TO_SEND = "Reward_data";

    @BindView(R.id.rewards_recyclerView)
    RecyclerView rewards_recyclerView;

    private List<RewardModel> rewardModelList;

    private int pointsInt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View redeemRewardView = inflater.inflate(R.layout.fragment_redeem_rewards, container, false);
        ButterKnife.bind(this, redeemRewardView);
        rewardModelList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_REWARDS_DIRECTORY);

        rewards_recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rewards_recyclerView.setLayoutManager(linearLayoutManager);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_USERS).child(userID).child(Constants.DATABASE_USER_POINTS);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String points = String.valueOf(dataSnapshot.getValue());
                points = new DecimalFormat("#").format(Double.parseDouble(points));
                pointsInt = Integer.parseInt(points);
                Log.d("Available Points", points);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> rewardsData = (Map<String, Object>) dataSnapshot.getValue();

                assert rewardsData != null;
                for(Map.Entry<String, Object> reward: rewardsData.entrySet()) {
                    RewardModel rewardModel = new RewardModel(reward.getKey(), String.valueOf(reward.getValue()));
                    int pointsRequired = Integer.parseInt(rewardModel.getRewardValue());
                    if(pointsRequired <= pointsInt) {
                        rewardModelList.add(rewardModel);
                    }
                }

                if(rewardModelList.size() > 0) {
                    RewardsAdapter_RV rewardsAdapter_rv = new RewardsAdapter_RV(getContext(), rewardModelList, RedeemRewardsFragment.this);
                    rewards_recyclerView.setAdapter(rewardsAdapter_rv);
                } else {
                    Toast.makeText(getContext(), "No rewards available at this time", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return redeemRewardView;
    }

    @Override
    public void onRewardSelected(RewardModel rewardModel) {
        Log.d("Points Value", rewardModel.getRewardName() + rewardModel.getRewardValue());
        Bundle bundleToSend = new Bundle();
        bundleToSend.putParcelable(REWARD_DATA_TO_SEND, rewardModel);
        RewardRedeemerFragment rewardRedeemerFragment = new RewardRedeemerFragment();
        rewardRedeemerFragment.setArguments(bundleToSend);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frameLayout, rewardRedeemerFragment)
                .addToBackStack(null)
                .commit();
    }
}
