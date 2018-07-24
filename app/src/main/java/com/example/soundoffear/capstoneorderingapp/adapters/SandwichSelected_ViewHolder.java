package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnSandwichSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.SandwichModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SandwichSelected_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final int MULTISELECTION = 2;
    public static final int SINGLESELECTION = 1;

    private OnSandwichSelectedListener onSandwichSelectedListener;
    SandwichModel sandwichModel;
    Context mContext;

    @BindView(R.id.sandwich_item_checkbox)
    TextView sandwich_checkbox;
    @BindView(R.id.sandwich_item_description_tv)
    TextView sandwich_description_tv;
    @BindView(R.id.sandwich_item_price_currency_tv)
    TextView sandwich_price_currency_tv;
    @BindView(R.id.sandwich_item_price_tv)
    TextView sandwich_price_tv;
    @BindView(R.id.sandwich_item_background)
    ConstraintLayout sandwich_item_background;

    SandwichSelected_ViewHolder(View view, OnSandwichSelectedListener listener, Context context) {
        super(view);
        this.mContext = context;
        ButterKnife.bind(this, view);
        this.onSandwichSelectedListener = listener;
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(sandwichModel.isSelected() && getItemViewType() == MULTISELECTION) {
            setSelected(false);
        } else {
            setSelected(true);
        }

        onSandwichSelectedListener.onSelectedSandwich(sandwichModel);
    }

    public void setSelected(boolean isSelected) {
        if(isSelected) {
            sandwich_item_background.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            sandwich_item_background.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        }
        sandwichModel.setSelected(isSelected);
    }
}
