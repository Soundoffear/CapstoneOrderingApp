package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.BreadModel;

import java.util.List;

public class SandwichBreadTypeAdapter_RV extends RecyclerView.Adapter<SandwichBreadType_ViewHolder> implements SandwichBreadType_ViewHolder.OnBreadSelectedListener {

    private Context mContext;
    private List<BreadModel> breadTypesList;
    private boolean isMultiselectAvaliable;
    SandwichBreadType_ViewHolder.OnBreadSelectedListener onBreadSelectedListener;

    public SandwichBreadTypeAdapter_RV(Context mContext, List<BreadModel> breadTypesList,
                                       SandwichBreadType_ViewHolder.OnBreadSelectedListener listener, boolean isMultiselectAvaliable) {
        this.mContext = mContext;
        this.breadTypesList = breadTypesList;
        this.isMultiselectAvaliable = isMultiselectAvaliable;
        this.onBreadSelectedListener = listener;
    }

    @NonNull
    @Override
    public SandwichBreadType_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View breadTypesView = LayoutInflater.from(mContext).inflate(R.layout.item_bread_type, parent, false);

        return new SandwichBreadType_ViewHolder(breadTypesView, this, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull final SandwichBreadType_ViewHolder holder, final int position) {
        final BreadModel breadModel = breadTypesList.get(position);
        holder.bread_type_tv.setText(breadTypesList.get(position).getBreadName());

        holder.breadModel = breadModel;
        holder.setSelected(holder.breadModel.isSelected());

    }

    @Override
    public int getItemCount() {
        return breadTypesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isMultiselectAvaliable) {
            return SandwichBreadType_ViewHolder.MULTI_SELECTION;
        } else {
            return SandwichBreadType_ViewHolder.SINGLE_SELECTION;
        }
    }

    @Override
    public void onBreadSelected(BreadModel breadModel) {
        if(!isMultiselectAvaliable) {
            for(BreadModel bread : breadTypesList) {
                if(!bread.equals(breadModel) && bread.isSelected()) {
                    bread.setBread(false);
                } else if (bread.equals(breadModel) && breadModel.isSelected()) {
                    bread.setBread(true);
                }
            }
            notifyDataSetChanged();
        }
        onBreadSelectedListener.onBreadSelected(breadModel);
    }


}
