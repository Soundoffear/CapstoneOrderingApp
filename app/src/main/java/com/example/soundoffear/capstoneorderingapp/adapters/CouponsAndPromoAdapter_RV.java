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

import com.example.soundoffear.capstoneorderingapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CouponsAndPromoAdapter_RV extends RecyclerView.Adapter<CouponsAndPromoAdapter_RV.CouponsAndPromoViewHolder> {

    Context cContext;
    List<String> couponsList;

    private String url = "gs://orderingapp-aa10b.appspot.com/Coupons/";
    private String extension = ".png";

    public CouponsAndPromoAdapter_RV(Context cContext, List<String> couponsList) {
        this.cContext = cContext;
        this.couponsList = couponsList;
    }

    @NonNull
    @Override
    public CouponsAndPromoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View couponsView = LayoutInflater.from(cContext).inflate(R.layout.item_coupons_and_promo, parent, false);

        return new CouponsAndPromoViewHolder(couponsView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CouponsAndPromoViewHolder holder, int position) {
        final String finalURL = "Coupons/" + couponsList.get(position)+extension;

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance("gs://orderingapp-aa10b.appspot.com");

        StorageReference storageReference = firebaseStorage.getReference();

        storageReference.child(finalURL).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("ON SUCCESS", String.valueOf(uri));
                Picasso.get().load(uri).into(holder.coupons_and_promo_imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return couponsList.size();
    }

    class CouponsAndPromoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.coupons_and_promo_imageView)
        ImageView coupons_and_promo_imageView;

        CouponsAndPromoViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
