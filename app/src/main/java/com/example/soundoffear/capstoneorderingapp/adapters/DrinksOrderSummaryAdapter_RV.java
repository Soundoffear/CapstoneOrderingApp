package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.DrinksModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrinksOrderSummaryAdapter_RV extends RecyclerView.Adapter<DrinksOrderSummaryAdapter_RV.DrinksOrderSummaryViewHolder> {

    private Context odContext;
    private List<DrinksModel> drinksModelList;

    public DrinksOrderSummaryAdapter_RV(Context odContext, List<DrinksModel> drinksModelList) {
        this.odContext = odContext;
        this.drinksModelList = drinksModelList;
    }

    @NonNull
    @Override
    public DrinksOrderSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(odContext).inflate(R.layout.item_drinks_summary, parent, false);

        return new DrinksOrderSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinksOrderSummaryViewHolder holder, int position) {

        holder.ids_drink_name.setText(drinksModelList.get(position).getName());
        holder.ids_drink_value.setText(drinksModelList.get(position).getValue());
        holder.ids_drink_price.setText(drinksModelList.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return drinksModelList.size();
    }

    class DrinksOrderSummaryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ids_drink_name) TextView ids_drink_name;
        @BindView(R.id.ids_drink_value) TextView ids_drink_value;
        @BindView(R.id.ids_drink_price) TextView ids_drink_price;
        @BindView(R.id.ids_drink_currency) TextView ids_drink_currency;

        DrinksOrderSummaryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
