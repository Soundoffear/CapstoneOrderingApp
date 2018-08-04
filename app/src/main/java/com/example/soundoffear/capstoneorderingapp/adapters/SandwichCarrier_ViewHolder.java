package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnCarrierSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.CarrierModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SandwichCarrier_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final int MULTISELECT = 2;
    public static final int SINGLESELECT = 1;

    private Context cContext;
    private OnCarrierSelectedListener carrierSelectedListener;
    CarrierModel carrierModel;

    @BindView(R.id.carrier_tv)
    TextView carrier_tv;

    SandwichCarrier_ViewHolder(View itemView, OnCarrierSelectedListener carrierListener, Context context) {
        super(itemView);
        this.cContext = context;
        this.carrierSelectedListener = carrierListener;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(carrierModel.isSelectedCarrier() && getItemViewType() == MULTISELECT) {
            setSelected(false);
        } else {
            setSelected(true);
        }
        carrierSelectedListener.onSelectedCarrier(carrierModel);
    }

    public void setSelected(boolean value) {
        if(value) {
            carrier_tv.setBackgroundDrawable(cContext.getResources().getDrawable(R.drawable.circular_layout_selected));
        } else {
            carrier_tv.setBackgroundDrawable(cContext.getResources().getDrawable(R.drawable.circular_layout));
        }
        carrierModel.setSelectedCarrier(value);
    }
}
