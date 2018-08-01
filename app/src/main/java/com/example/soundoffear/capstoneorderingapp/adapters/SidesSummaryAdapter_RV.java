package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.SidesModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SidesSummaryAdapter_RV extends RecyclerView.Adapter<SidesSummaryAdapter_RV.SidesSummaryViewHolder> {

    private Context context;
    private List<SidesModel> sidesModelList;

    public SidesSummaryAdapter_RV(Context context, List<SidesModel> sidesModelList) {
        this.context = context;
        this.sidesModelList = sidesModelList;
    }

    @NonNull
    @Override
    public SidesSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sides_summary, parent, false);

        return new SidesSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SidesSummaryViewHolder holder, int position) {
        SidesModel sidesModel = sidesModelList.get(position);

        holder.iss_sides_name.setText(sidesModel.getSideName());
        holder.iss_sides_price.setText(sidesModel.getSidePrice());
        holder.iss_sides_value.setText(sidesModel.getSideNumber());
    }

    @Override
    public int getItemCount() {
        return sidesModelList.size();
    }


    class SidesSummaryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iss_sides_name)
        TextView iss_sides_name;
        @BindView(R.id.iss_sides_price)
        TextView iss_sides_price;
        @BindView(R.id.iss_sides_value)
        TextView iss_sides_value;

        public SidesSummaryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
