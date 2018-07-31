package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.CateringModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CateringSummaryAdapter_RV extends RecyclerView.Adapter<CateringSummaryAdapter_RV.CateringSummaryViewHolder> {

    private Context cateringContext;
    private List<CateringModel> cateringModelList;

    public CateringSummaryAdapter_RV(Context cateringContext, List<CateringModel> cateringModelList) {
        this.cateringContext = cateringContext;
        this.cateringModelList = cateringModelList;
    }

    @NonNull
    @Override
    public CateringSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cateringContext).inflate(R.layout.items_catering_summary, parent, false);

        return new CateringSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CateringSummaryViewHolder holder, int position) {
        CateringModel cateringModel = cateringModelList.get(position);

        holder.catering_summary_name.setText(cateringModel.getCateringName());
        holder.catering_summary_price.setText(cateringModel.getCateringPrice());

    }

    @Override
    public int getItemCount() {
        return cateringModelList.size();
    }

    class CateringSummaryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_catering_summary_name)
        TextView catering_summary_name;
        @BindView(R.id.item_catering_summary_price)
        TextView catering_summary_price;

        CateringSummaryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
