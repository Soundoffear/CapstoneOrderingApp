package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.BreadModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SandwichBreadType_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.item_bread_type_tv)
    TextView bread_type_tv;

    @BindView(R.id.breadType_background)
    ConstraintLayout breadType_background;

    public static final int MULTI_SELECTION = 2;
    public static final int SINGLE_SELECTION = 1;

    private OnBreadSelectedListener onItemSelectedListener;
    BreadModel breadModel;
    private Context mContext;

    SandwichBreadType_ViewHolder(View itemView, OnBreadSelectedListener listener, Context context) {
        super(itemView);
        this.mContext = context;
        onItemSelectedListener = listener;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (breadModel.isSelected() && getItemViewType() == MULTI_SELECTION) {
            setSelected(false);
        } else {
            setSelected(true);
        }

        onItemSelectedListener.onBreadSelected(breadModel);
    }

    public void setSelected(boolean selected) {
        if (selected) {
            breadType_background.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            breadType_background.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        }
        breadModel.setBread(selected);
    }

    public interface OnBreadSelectedListener {
        void onBreadSelected(BreadModel breadModel);
    }

}



