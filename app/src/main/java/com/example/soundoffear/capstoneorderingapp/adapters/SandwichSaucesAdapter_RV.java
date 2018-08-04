package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnSauceSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.SaucesModel;

import java.util.List;

public class SandwichSaucesAdapter_RV extends RecyclerView.Adapter<SandwichSauces_ViewHolder> implements OnSauceSelectedListener {

    private Context mContext;
    private List<SaucesModel> saucesModels;
    private OnSauceSelectedListener onSauceSelectedListener;
    private boolean isMultiSelectable;
    private List<SaucesModel> selectedSaucesList;

    public SandwichSaucesAdapter_RV(Context mContext, List<SaucesModel> saucesModels, OnSauceSelectedListener onSauceSelectedListener, boolean isMultiSelectable, List<SaucesModel> selectedSauces) {
        this.mContext = mContext;
        this.saucesModels = saucesModels;
        this.onSauceSelectedListener = onSauceSelectedListener;
        this.isMultiSelectable = isMultiSelectable;
        this.selectedSaucesList = selectedSauces;
    }

    @NonNull
    @Override
    public SandwichSauces_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View saucesView = LayoutInflater.from(mContext).inflate(R.layout.item_sauce, parent, false);

        return new SandwichSauces_ViewHolder(saucesView, this, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull SandwichSauces_ViewHolder holder, int position) {
        SaucesModel saucesModel = saucesModels.get(position);

        if (selectedSaucesList.size() > 0) {
            for (SaucesModel sauce : selectedSaucesList) {
                if (saucesModel.equals(sauce)) {
                    holder.sauces_textview_name.setText(saucesModels.get(position).getSauceName());
                    holder.saucesModel = sauce;
                    holder.setSelected(true);
                } else {
                    holder.sauces_textview_name.setText(saucesModels.get(position).getSauceName());
                    holder.saucesModel = saucesModel;
                    holder.setSelected(holder.saucesModel.isSelected());
                }
            }
        } else {
            holder.sauces_textview_name.setText(saucesModels.get(position).getSauceName());
            holder.saucesModel = saucesModel;
            holder.setSelected(holder.saucesModel.isSelected());
        }
    }

    @Override
    public int getItemCount() {
        return saucesModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isMultiSelectable) {
            return SandwichSauces_ViewHolder.MULTISELECT;
        } else {
            return SandwichSauces_ViewHolder.SINGLESELECT;
        }
    }

    @Override
    public void onSauceSelected(SaucesModel saucesModel) {
        if (!isMultiSelectable) {
            for (SaucesModel sauces : saucesModels) {
                if (!sauces.equals(saucesModel) && sauces.isSelected()) {
                    sauces.setSelected(false);
                } else if (sauces.equals(saucesModel) && saucesModel.isSelected()) {
                    sauces.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        onSauceSelectedListener.onSauceSelected(saucesModel);
    }
}
