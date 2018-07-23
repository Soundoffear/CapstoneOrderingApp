package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnSandwichSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.SandwichModel;

import java.text.DecimalFormat;
import java.util.List;

public class SandwichSelectedAdapter_RV extends RecyclerView.Adapter<SandwichSelected_ViewHolder> implements OnSandwichSelectedListener {

    private Context context;
    private List<SandwichModel> sandwichModelList;
    OnSandwichSelectedListener onSandwichSelectedListener;
    private boolean isMultiselectable;
    private String carrierType;

    public SandwichSelectedAdapter_RV(Context context, List<SandwichModel> sandwichModelList, OnSandwichSelectedListener listener, boolean isMultiselectable, String carrierType) {
        this.context = context;
        this.sandwichModelList = sandwichModelList;
        this.onSandwichSelectedListener = listener;
        this.isMultiselectable = isMultiselectable;
        this.carrierType = carrierType;
    }

    @NonNull
    @Override
    public SandwichSelected_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sandwich, parent, false);

        return new SandwichSelected_ViewHolder(view, this, context);
    }

    @Override
    public void onBindViewHolder(@NonNull SandwichSelected_ViewHolder holder, int position) {

        holder.sandwich_checkbox.setText(sandwichModelList.get(position).getSandwichName());
        holder.sandwich_description_tv.setText(sandwichModelList.get(position).getSandwichDescription());
        if(carrierType.equals("SALAD")) {
            double sandwichPrice = Double.parseDouble(sandwichModelList.get(position).getSandwichPrice());
            sandwichPrice = sandwichPrice + 2;
            holder.sandwich_price_tv.setText(new DecimalFormat("#.00").format(sandwichPrice));
        } else if (carrierType.equals("SUB30")) {
            double sandwichPrice = Double.parseDouble(sandwichModelList.get(position).getSandwichPrice());
            sandwichPrice = sandwichPrice + 8;
            holder.sandwich_price_tv.setText(new DecimalFormat("#.00").format(sandwichPrice));
        } else {
            double sandwichPrice = Double.parseDouble(sandwichModelList.get(position).getSandwichPrice());
            holder.sandwich_price_tv.setText(new DecimalFormat("#.00").format(sandwichPrice));
        }

        holder.sandwich_price_currency_tv.setText("PLN");

        holder.sandwichModel = sandwichModelList.get(position);
        holder.setSelected(holder.sandwichModel.isSelected());

    }

    @Override
    public int getItemCount() {
        return sandwichModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isMultiselectable) {
            return SandwichSelected_ViewHolder.MULTISELECTION;
        } else {
            return SandwichSelected_ViewHolder.SINGLESELECTION;
        }
    }

    @Override
    public void onSelectedSandwich(SandwichModel sandwichModel) {
        if(!isMultiselectable) {
            for(SandwichModel sandwich : sandwichModelList) {
                if(!sandwich.equals(sandwichModel) && sandwich.isSelected()) {
                    sandwich.setSelected(false);
                } else if (sandwich.equals(sandwichModel) && sandwichModel.isSelected()) {
                    sandwich.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        onSandwichSelectedListener.onSelectedSandwich(sandwichModel);
    }
}