package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnCarrierSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.CarrierModel;

import java.util.List;

public class SandwichCarrierAdapter_RV extends RecyclerView.Adapter<SandwichCarrier_ViewHolder> implements OnCarrierSelectedListener {

    private Context mContext;
    private List<CarrierModel> carriersList;
    private OnCarrierSelectedListener onCarrierSelectedListener;
    private boolean isMultiSelectable;
    private String selectedNameCarrier;

    public SandwichCarrierAdapter_RV(Context mContext, List<CarrierModel> carriersList, OnCarrierSelectedListener carrierSelectionListener, boolean isMultiSelectable, @Nullable String selectedCarrierName) {
        this.mContext = mContext;
        this.carriersList = carriersList;
        this.onCarrierSelectedListener = carrierSelectionListener;
        this.isMultiSelectable = isMultiSelectable;
        this.selectedNameCarrier = selectedCarrierName;
    }

    @NonNull
    @Override
    public SandwichCarrier_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View carriersView = LayoutInflater.from(mContext).inflate(R.layout.item_carrier, parent, false);

        return new SandwichCarrier_ViewHolder(carriersView, this, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull SandwichCarrier_ViewHolder holder, final int position) {
        CarrierModel carrierModel = carriersList.get(position);

        Log.d("SELECTED CARRIER", ":" + carrierModel.getCarrierName().equals(selectedNameCarrier));
        Log.d("SEL CAR", "---===" + selectedNameCarrier);
        if (carrierModel.getCarrierName().equals(selectedNameCarrier)) {
            holder.carrier_tv.setText(carrierModel.getCarrierName());
            holder.carrierModel = carrierModel;
            holder.setSelected(true);
        } else {
            holder.carrier_tv.setText(carrierModel.getCarrierName());
            holder.carrierModel = carrierModel;
            holder.setSelected(holder.carrierModel.isSelectedCarrier());
            //selectedNameCarrier = carrierModel.getCarrierName();
        }
    }

    @Override
    public int getItemCount() {
        return carriersList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (isMultiSelectable) {
            return SandwichCarrier_ViewHolder.MULTISELECT;
        } else {
            return SandwichCarrier_ViewHolder.SINGLESELECT;
        }
    }

    @Override
    public void onSelectedCarrier(CarrierModel carrierModel) {
        if (!isMultiSelectable) {
            for (CarrierModel carrier : carriersList) {
                if (!carrier.equals(carrierModel) && carrier.isSelectedCarrier()) {
                    carrier.setSelectedCarrier(false);
                } else if (carrier.equals(carrierModel) && carrierModel.isSelectedCarrier()) {
                    carrier.setSelectedCarrier(true);
                }
            }
            notifyDataSetChanged();
        }
        onCarrierSelectedListener.onSelectedCarrier(carrierModel);
    }
}
