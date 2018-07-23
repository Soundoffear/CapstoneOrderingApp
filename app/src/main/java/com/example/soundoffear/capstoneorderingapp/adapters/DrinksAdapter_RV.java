package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.databases.DrinksOrderDatabase;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnDrinksSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.DrinksModel;

import java.text.DecimalFormat;
import java.util.List;

public class DrinksAdapter_RV extends RecyclerView.Adapter implements OnDrinksSelectedListener {

    public static boolean isOrderingMoreDrinks = false;
    private Context context;
    private List<DrinksModel> drinksModelList;
    private OnDrinksSelectedListener onDrinksSelectedListener;
    private boolean isMultiSelectable;
    private int valueNumber;

    public DrinksAdapter_RV(Context context, List<DrinksModel> drinksModelList, OnDrinksSelectedListener drinksSelectedListener, boolean isMultiSelectable) {
        this.context = context;
        this.drinksModelList = drinksModelList;
        this.onDrinksSelectedListener = drinksSelectedListener;
        this.isMultiSelectable = isMultiSelectable;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case DrinksModel.FAMILY_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.item_drinks_family, parent, false);
                return new DrinksFamilyViewHolder(view);
            case DrinksModel.DRINK_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.item_drinks, parent, false);
                return new DrinksViewHolder(view, this, context);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final DrinksModel drinksModel = drinksModelList.get(position);
        if (drinksModel != null) {
            switch (drinksModel.getType()) {
                case DrinksModel.FAMILY_TYPE:
                    ((DrinksFamilyViewHolder) holder).item_drinks_family_view_holder.setText(drinksModel.getName());
                    break;
                case DrinksModel.DRINK_TYPE:
                    if(isOrderingMoreDrinks) {
                        DrinksOrderDatabase drinksOrderDatabase = new DrinksOrderDatabase(context);
                        List<DrinksModel> drinksList = drinksOrderDatabase.getAllDrinksData();
                        for(int i = 0; i < drinksList.size(); i++) {
                            if(drinksList.get(i).getName().equals(drinksModel.getName())) {
                                int value = Integer.parseInt(drinksList.get(i).getValue());
                                ((DrinksViewHolder) holder).item_drinks_number_ordered.setText(String.valueOf(value));
                                ((DrinksViewHolder) holder).drinksModel = drinksList.get(i);
                                ((DrinksViewHolder) holder).setSelected(value);
                                double itemPrice = Double.parseDouble(drinksList.get(i).getPrice());
                                double finalPrice = value * itemPrice;
                                ((DrinksViewHolder) holder).item_drinks_price.setText(new DecimalFormat("0.00").format(finalPrice));
                            }
                        }
                    }
                    ((DrinksViewHolder) holder).item_drinks_name.setText(drinksModel.getName());
                    ((DrinksViewHolder) holder).item_drinks_price.setText(context.getResources().getString(R.string.zero_pln));
                    ((DrinksViewHolder) holder).item_drinks_positive_increase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s_value = ((DrinksViewHolder) holder).item_drinks_number_ordered.getText().toString();
                            int d_value = Integer.parseInt(s_value);
                            d_value++;
                            ((DrinksViewHolder) holder).item_drinks_number_ordered.setText(String.valueOf(d_value));
                            ((DrinksViewHolder) holder).drinksModel = drinksModel;
                            ((DrinksViewHolder) holder).setSelected(d_value);
                            onDrinksSelectedListener.onDrinksSelected(drinksModel, d_value);
                            double itemPrice = Double.parseDouble(drinksModel.getPrice());
                            double finalPrice = itemPrice * d_value;
                            ((DrinksViewHolder) holder).item_drinks_price.setText(new DecimalFormat("0.00").format(finalPrice));
                        }
                    });
                    ((DrinksViewHolder) holder).item_drinks_negative_decrease.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s_value = ((DrinksViewHolder) holder).item_drinks_number_ordered.getText().toString();
                            int d_value = Integer.parseInt(s_value);
                            if(d_value > 0) {
                                d_value--;
                                ((DrinksViewHolder) holder).item_drinks_number_ordered.setText(String.valueOf(d_value));
                                ((DrinksViewHolder) holder).drinksModel = drinksModel;
                                ((DrinksViewHolder) holder).setSelected(d_value);
                                onDrinksSelectedListener.onDrinksSelected(drinksModel, d_value);
                                double itemPrice = Double.parseDouble(drinksModel.getPrice());
                                double finalPrice = itemPrice * d_value;
                                ((DrinksViewHolder) holder).item_drinks_price.setText(new DecimalFormat("0.00").format(finalPrice));
                            }
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return drinksModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (drinksModelList.get(position).getType()) {
            case 0:
                return DrinksModel.FAMILY_TYPE;
            case 1:
                return DrinksModel.DRINK_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public void onDrinksSelected(DrinksModel drinksModel, int value) {
        Log.d("DRINKS RV", drinksModel.getName() + " " + value);
        valueNumber = value;
        if(!isMultiSelectable) {
            for(DrinksModel d_model: drinksModelList) {
                if(!d_model.equals(drinksModel) && d_model.isSelected()) {
                    d_model.setSelected(false);
                } else if (d_model.equals(drinksModel) && drinksModel.isSelected()) {
                    d_model.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        onDrinksSelectedListener.onDrinksSelected(drinksModel, value);
    }
}
