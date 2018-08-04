package com.example.soundoffear.capstoneorderingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrinksFamilyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_drinks_family_view_holder)
    TextView item_drinks_family_view_holder;

    DrinksFamilyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
