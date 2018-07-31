package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnSidesSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.SidesModel;

import java.text.DecimalFormat;
import java.util.List;

public class SidesAdapter_RV extends RecyclerView.Adapter<SidesViewHolder> implements OnSidesSelectedListener {

    private List<SidesModel> sidesModelList;
    private Context sContext;
    private boolean isMultiselectable;
    private OnSidesSelectedListener onSidesSelectedListener;

    public SidesAdapter_RV(List<SidesModel> sidesModelList, Context sContext, boolean isMultiselectable, OnSidesSelectedListener onSidesSelectedListener) {
        this.sidesModelList = sidesModelList;
        this.sContext = sContext;
        this.isMultiselectable = isMultiselectable;
        this.onSidesSelectedListener = onSidesSelectedListener;
    }

    @NonNull
    @Override
    public SidesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewSides = LayoutInflater.from(sContext).inflate(R.layout.item_sides, parent, false);

        return new SidesViewHolder(viewSides, sContext);
    }

    @Override
    public void onBindViewHolder(@NonNull final SidesViewHolder holder, int position) {
        final SidesModel sidesModel = sidesModelList.get(position);
        holder.item_sides_name.setText(sidesModel.getSideName());
        holder.item_sides_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_value = holder.item_sides_number_ordered.getText().toString();
                int d_value = Integer.parseInt(s_value);
                d_value++;
                holder.item_sides_number_ordered.setText(String.valueOf(d_value));
                holder.sidesModel = sidesModel;
                holder.setSidesSelected(d_value);
                onSidesSelectedListener.onSidesSelected(sidesModel, d_value);
                double itemPrice = Double.parseDouble(sidesModel.getSidePrice());
                double finalPrice = itemPrice * d_value;
                holder.item_sides_price.setText(new DecimalFormat("0.00").format(finalPrice));
            }
        });
        holder.item_sides_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_value = holder.item_sides_number_ordered.getText().toString();
                int d_value = Integer.parseInt(s_value);
                if(d_value > 0) {
                    d_value--;
                    holder.item_sides_number_ordered.setText(String.valueOf(d_value));
                    holder.sidesModel = sidesModel;
                    holder.setSidesSelected(d_value);
                    onSidesSelectedListener.onSidesSelected(sidesModel, d_value);
                    double itemPrice = Double.parseDouble(sidesModel.getSidePrice());
                    double finalPrice = itemPrice * d_value;
                    holder.item_sides_price.setText(new DecimalFormat("0.00").format(finalPrice));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return sidesModelList.size();
    }

    @Override
    public void onSidesSelected(SidesModel sidesModel, int value) {
        if(!isMultiselectable) {
            for(SidesModel side: sidesModelList) {
                if(!side.equals(sidesModel) && side.isSideSelected()) {
                    side.setSideSelected(false);
                } else if(side.equals(sidesModel) && sidesModel.isSideSelected()) {
                    side.setSideSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        onSidesSelectedListener.onSidesSelected(sidesModel, value);
    }
}
