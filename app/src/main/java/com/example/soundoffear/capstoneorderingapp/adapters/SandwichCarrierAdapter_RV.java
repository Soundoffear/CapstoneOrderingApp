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
import com.example.soundoffear.capstoneorderingapp.interfaces.OnCarrierSelectedListener;
import com.example.soundoffear.capstoneorderingapp.interfaces.SelectionListener;
import com.example.soundoffear.capstoneorderingapp.models.CarrierModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SandwichCarrierAdapter_RV extends RecyclerView.Adapter<SandwichCarrier_ViewHolder> implements OnCarrierSelectedListener {

    private Context mContext;
    private List<CarrierModel> carriersList;
    private OnCarrierSelectedListener onCarrierSelectedListener;
    private boolean isMultiSelectable;

    public SandwichCarrierAdapter_RV(Context mContext, List<CarrierModel> carriersList, OnCarrierSelectedListener carrierSelectionListener, boolean isMultiSelectable) {
        this.mContext = mContext;
        this.carriersList = carriersList;
        this.onCarrierSelectedListener = carrierSelectionListener;
        this.isMultiSelectable = isMultiSelectable;
    }

    @NonNull
    @Override
    public SandwichCarrier_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View carriersView = LayoutInflater.from(mContext).inflate(R.layout.item_carrier, parent, false);

        return new SandwichCarrier_ViewHolder(carriersView, this, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull SandwichCarrier_ViewHolder holder, final int position) {
        holder.carrier_tv.setText(carriersList.get(position).getCarrierName());

        holder.carrierModel = carriersList.get(position);
        holder.setSelected(holder.carrierModel.isSelectedCarrier());

    }

    @Override
    public int getItemCount() {
        return carriersList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if(isMultiSelectable) {
            return SandwichCarrier_ViewHolder.MULTISELECT;
        } else {
            return SandwichCarrier_ViewHolder.SINGLESELECT;
        }
    }

    @Override
    public void onSelectedCarrier(CarrierModel carrierModel) {
        if(!isMultiSelectable) {
            for(CarrierModel carrier: carriersList) {
                if(!carrier.equals(carrierModel) && carrier.isSelectedCarrier()) {
                    carrier.setSelectedCarrier(false);
                } else if(carrier.equals(carrierModel) && carrierModel.isSelectedCarrier()) {
                    carrier.setSelectedCarrier(true);
                }
            }
            notifyDataSetChanged();
        }
        onCarrierSelectedListener.onSelectedCarrier(carrierModel);
    }
}
