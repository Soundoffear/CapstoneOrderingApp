package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.HistoryModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersHistoryAdapter_RV extends RecyclerView.Adapter {

    private Context hContext;
    private List<HistoryModel> historyModelList;

    public OrdersHistoryAdapter_RV(Context hContext, List<HistoryModel> historyModelList) {
        this.hContext = hContext;
        this.historyModelList = historyModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View historyView;
        switch (viewType) {
            case HistoryModel.LABEL_TYPE:
                historyView = LayoutInflater.from(hContext).inflate(R.layout.item_history_label, parent, false);
                return new OrderHistoryLabel_ViewHolder(historyView);
            case HistoryModel.SANDWICH_TYPE:
                historyView = LayoutInflater.from(hContext).inflate(R.layout.item_order_summary, parent, false);
                return new OrderHistorySandwiches_ViewHolder(historyView);
            case HistoryModel.DRINK_TYPE:
                historyView = LayoutInflater.from(hContext).inflate(R.layout.item_drinks_summary, parent, false);
                return new OrderHistoryDrinks_ViewHolder(historyView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HistoryModel historyModel = historyModelList.get(position);
        if (historyModel != null) {
            switch (historyModel.getType()) {
                case HistoryModel.LABEL_TYPE:
                    ((OrderHistoryLabel_ViewHolder) holder).item_history_label_textView.setText(historyModel.getName());
                    break;
                case HistoryModel.SANDWICH_TYPE:
                    ((OrderHistorySandwiches_ViewHolder) holder).item_os_name.setText(historyModel.getName());
                    ((OrderHistorySandwiches_ViewHolder) holder).item_os_carrier.setText(historyModel.getCarrier());
                    ((OrderHistorySandwiches_ViewHolder) holder).item_os_bread_output.setText(historyModel.getBread());
                    ((OrderHistorySandwiches_ViewHolder) holder).item_os_final_price.setText(historyModel.getPrice());
                    ((OrderHistorySandwiches_ViewHolder) holder).item_os_paid_addons_output.setText(historyModel.getPaidAddons());
                    ((OrderHistorySandwiches_ViewHolder) holder).item_os_sauces_output.setText(historyModel.getSauces());
                    ((OrderHistorySandwiches_ViewHolder) holder).item_os_vegetable_output.setText(historyModel.getVegetables());
                    break;
                case HistoryModel.DRINK_TYPE:
                    ((OrderHistoryDrinks_ViewHolder) holder).ids_drink_name.setText(historyModel.getName());
                    ((OrderHistoryDrinks_ViewHolder) holder).ids_drink_price.setText(historyModel.getPrice());
                    ((OrderHistoryDrinks_ViewHolder) holder).ids_drink_value.setText(historyModel.getQuantity());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return historyModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (historyModelList.get(position).getType()) {
            case 0:
                return HistoryModel.LABEL_TYPE;
            case 1:
                return HistoryModel.SANDWICH_TYPE;
            case 2:
                return HistoryModel.DRINK_TYPE;
            default:
                return -1;
        }
    }

    class OrderHistoryLabel_ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_history_label_textView)
        TextView item_history_label_textView;

        OrderHistoryLabel_ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class OrderHistoryDrinks_ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ids_drink_name) TextView ids_drink_name;
        @BindView(R.id.ids_drink_value) TextView ids_drink_value;
        @BindView(R.id.ids_drink_price) TextView ids_drink_price;

        OrderHistoryDrinks_ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class OrderHistorySandwiches_ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_os_carrier) TextView item_os_carrier;
        @BindView(R.id.item_os_name) TextView item_os_name;
        @BindView(R.id.item_os_bread_output) TextView item_os_bread_output;
        @BindView(R.id.item_os_vegetable_output) TextView item_os_vegetable_output;
        @BindView(R.id.item_os_sauces_output) TextView item_os_sauces_output;
        @BindView(R.id.item_os_paid_addons_output) TextView item_os_paid_addons_output;
        @BindView(R.id.item_os_final_price) TextView item_os_final_price;

        OrderHistorySandwiches_ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
