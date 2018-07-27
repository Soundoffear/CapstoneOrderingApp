package com.example.soundoffear.capstoneorderingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.adapters.RewardsAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.models.RewardModel;
import com.example.soundoffear.capstoneorderingapp.utilities.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RedeemRewardsFragment extends Fragment {

    @BindView(R.id.rewards_recyclerView)
    RecyclerView rewards_recyclerView;

    List<RewardModel> rewardModelList;

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

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> rewardsData = (Map<String, Object>) dataSnapshot.getValue();

                assert rewardsData != null;
                for(Map.Entry<String, Object> reward: rewardsData.entrySet()) {
                    RewardModel rewardModel = new RewardModel(reward.getKey(), String.valueOf(reward.getValue()));
                    rewardModelList.add(rewardModel);
                }

                RewardsAdapter_RV rewardsAdapter_rv = new RewardsAdapter_RV(getContext(), rewardModelList);
                rewards_recyclerView.setAdapter(rewardsAdapter_rv);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return redeemRewardView;
    }
}
