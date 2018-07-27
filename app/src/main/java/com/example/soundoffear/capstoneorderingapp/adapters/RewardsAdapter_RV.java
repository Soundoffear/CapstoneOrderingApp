package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.RewardModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardsAdapter_RV extends RecyclerView.Adapter<RewardsAdapter_RV.RewardsViewHolder> {

    private Context rContext;
    private List<RewardModel> rewardModelList;

    public RewardsAdapter_RV(Context rContext, List<RewardModel> rewardModelList) {
        this.rContext = rContext;
        this.rewardModelList = rewardModelList;
    }

    @NonNull
    @Override
    public RewardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(rContext).inflate(R.layout.item_rewards, parent, false);

        return new RewardsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RewardsViewHolder holder, int position) {
        RewardModel rewardModel = rewardModelList.get(position);

        String imageUrl = "Rewards/"+rewardModel.getRewardName()+".png";

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance("gs://orderingapp-aa10b.appspot.com");

        StorageReference storageReference = firebaseStorage.getReference();

        storageReference.child(imageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("REWARDS SUCCESS", uri.toString());
                Picasso.get().load(uri).into(holder.rewards_imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(rContext, "Unable to load images", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    class RewardsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.rewards_imageView)
        ImageView rewards_imageView;

        RewardsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
