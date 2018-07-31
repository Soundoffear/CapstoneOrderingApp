package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnCateringItemSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.CateringModel;

import java.util.List;

public class CateringAdapter_RV extends RecyclerView.Adapter<CateringViewHolder> implements OnCateringItemSelectedListener {

    private Context cateringContext;
    private List<CateringModel> cateringModelList;
    OnCateringItemSelectedListener onCateringItemSelectedListener;
    private boolean isMultiSelectable;

    public CateringAdapter_RV(Context cateringContext, List<CateringModel> cateringModelList, OnCateringItemSelectedListener onCateringItemSelectedListener, boolean isMultiSelectable) {
        this.cateringContext = cateringContext;
        this.cateringModelList = cateringModelList;
        this.onCateringItemSelectedListener = onCateringItemSelectedListener;
        this.isMultiSelectable = isMultiSelectable;
    }

    @NonNull
    @Override
    public CateringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(cateringContext).inflate(R.layout.item_catering, parent, false);

        return new CateringViewHolder(view, cateringContext, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CateringViewHolder holder, int position) {
        holder.catering_name_tv.setText(cateringModelList.get(position).getCateringName());
        holder.catering_name_price.setText(cateringModelList.get(position).getCateringPrice());

        holder.cateringModel = cateringModelList.get(position);
        holder.setSelectedCatering(holder.cateringModel.isSelectedCatering());
    }

    @Override
    public int getItemCount() {
        return cateringModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isMultiSelectable) {
            return CateringViewHolder.MULTISELECTED;
        } else {
            return CateringViewHolder.SINGLESELCTED;
        }
    }

    @Override
    public void onCateringItemSelected(CateringModel cateringModel) {
        if(!isMultiSelectable) {
            for(CateringModel cateringItem: cateringModelList) {
                if(cateringItem.isSelectedCatering() && !cateringItem.equals(cateringModel)) {
                    cateringItem.setSelectedCatering(false);
                } else if (cateringItem.equals(cateringModel) && cateringModel.isSelectedCatering()) {
                    cateringItem.setSelectedCatering(true);
                }
            }
            notifyDataSetChanged();
        }
        onCateringItemSelectedListener.onCateringItemSelected(cateringModel);
    }
}
