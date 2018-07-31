package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnCateringItemSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.CateringModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CateringViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.catering_name_tv)
    TextView catering_name_tv;
    @BindView(R.id.catering_name_price)
    TextView catering_name_price;
    @BindView(R.id.item_catering_background)
    ConstraintLayout item_catering_background;

    public static final int MULTISELECTED = 2;
    public static final int SINGLESELCTED = 1;

    private Context context;
    private OnCateringItemSelectedListener onCateringItemSelectedListener;
    CateringModel cateringModel;

    public CateringViewHolder(View itemView, Context context, OnCateringItemSelectedListener cateringItemSelectedListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
        this.onCateringItemSelectedListener = cateringItemSelectedListener;
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(cateringModel.isSelectedCatering() && getItemViewType() == MULTISELECTED) {
            setSelectedCatering(false);
        } else {
            setSelectedCatering(true);
        }
        onCateringItemSelectedListener.onCateringItemSelected(cateringModel);
    }

    void setSelectedCatering(boolean value) {
        if (value) {
            item_catering_background.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circular_layout_selected));
        } else {
            item_catering_background.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circular_layout));
        }
        cateringModel.setSelectedCatering(value);
    }
}
