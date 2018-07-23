package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.SelectionListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SandwichCarrierAdapter_RV extends RecyclerView.Adapter<SandwichCarrierAdapter_RV.SandwichCarrier_ViewHolder> {

    // TODO needs to be redone so it can be single selectable
    private Context mContext;
    private List<String> carriersList;
    private SelectionListener onCarrierSelectedListener;

    public SandwichCarrierAdapter_RV(Context mContext, List<String> carriersList, SelectionListener carrierSelectionListener) {
        this.mContext = mContext;
        this.carriersList = carriersList;
        onCarrierSelectedListener = carrierSelectionListener;
    }

    @NonNull
    @Override
    public SandwichCarrier_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View carriersView = LayoutInflater.from(mContext).inflate(R.layout.item_carrier, parent, false);

        return new SandwichCarrier_ViewHolder(carriersView);
    }

    @Override
    public void onBindViewHolder(@NonNull SandwichCarrier_ViewHolder holder, final int position) {
        holder.carrier_tv.setText(carriersList.get(position));


    }

    @Override
    public int getItemCount() {
        return carriersList.size();
    }


    class SandwichCarrier_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.carrier_tv)
        TextView carrier_tv;
        @BindView(R.id.carrier_layout)
        ConstraintLayout carrier_layout;

        SandwichCarrier_ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int row_index = getLayoutPosition();

            if(row_index == getLayoutPosition()) {
                carrier_layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            } else if(row_index != getLayoutPosition()) {
                carrier_layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            }

            onCarrierSelectedListener.onCarrierSelectedListener(getLayoutPosition(), carriersList.get(getLayoutPosition()));
        }
    }

}
