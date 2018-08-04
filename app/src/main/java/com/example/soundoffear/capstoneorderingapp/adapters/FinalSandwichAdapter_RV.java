package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.FinalSandwichModel;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinalSandwichAdapter_RV extends RecyclerView.Adapter<FinalSandwichAdapter_RV.FinalSandwich_ViewHolder> {

    private Context fContext;
    private List<FinalSandwichModel> finalSandwichModelList;

    public FinalSandwichAdapter_RV(Context fContext, List<FinalSandwichModel> finalSandwichModelList) {
        this.fContext = fContext;
        this.finalSandwichModelList = finalSandwichModelList;
    }

    @NonNull
    @Override
    public FinalSandwich_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fContext).inflate(R.layout.item_order_summary, parent, false);

        return new FinalSandwich_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinalSandwich_ViewHolder holder, int position) {
        FinalSandwichModel finalSandwichModel = finalSandwichModelList.get(position);
        holder.item_os_carrier.setText(finalSandwichModel.getCarrier());
        holder.item_os_sandwich_name.setText(finalSandwichModel.getSandwich());
        holder.item_os_bread.setText(finalSandwichModel.getBread());
        String[] vegetables = finalSandwichModel.getVegetables().split("_");
        StringBuilder vegesSpace = new StringBuilder();
        for (String vegetable : vegetables) {
            vegesSpace.append(vegetable).append(" ");
        }
        holder.item_os_vegetable.setText(brokenDownData(vegesSpace.toString()));
        String[] sauces = finalSandwichModel.getSauces().split("_");
        StringBuilder saucesSpace = new StringBuilder();
        for (String sauce : sauces) {
            saucesSpace.append(sauce).append(" ");
        }
        holder.item_os_sauces.setText(saucesSpace.toString());
        String[] paidSplitted = finalSandwichModel.getPaidAddOns().split("-");
        StringBuilder paidBuilder = new StringBuilder();
        for (String paidAddOn : paidSplitted) {
            String[] paidAddOnSplitted = paidAddOn.split("_");
            if (paidAddOnSplitted.length > 1) {
                paidBuilder.append(paidAddOnSplitted[0]).append(" ").append("x").append(" ").append(paidAddOnSplitted[1]).append(" ").append("-").append(" ").append(paidAddOnSplitted[2]).append("\n");
            } else {
                paidBuilder.append("brak");
            }
        }
        holder.item_os_paid_addons.setText(paidBuilder);
        double finalPrice = Double.parseDouble(finalSandwichModel.getFinalPrice());
        holder.item_os_final_price.setText(new DecimalFormat("0.00").format(finalPrice));

    }

    @Override
    public int getItemCount() {
        return finalSandwichModelList.size();
    }

    class FinalSandwich_ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_os_carrier)
        TextView item_os_carrier;
        @BindView(R.id.item_os_name)
        TextView item_os_sandwich_name;
        @BindView(R.id.item_os_bread_output)
        TextView item_os_bread;
        @BindView(R.id.item_os_vegetable_output)
        TextView item_os_vegetable;
        @BindView(R.id.item_os_sauces_output)
        TextView item_os_sauces;
        @BindView(R.id.item_os_paid_addons_output)
        TextView item_os_paid_addons;
        @BindView(R.id.item_os_final_price)
        TextView item_os_final_price;

        FinalSandwich_ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private String brokenDownData(String data) {
        String[] brokenData1 = data.split("_");
        StringBuilder stringBuilder = new StringBuilder();
        for (String aBrokenData1 : brokenData1) {
            stringBuilder.append(aBrokenData1).append(" ");
        }

        return stringBuilder.toString();
    }

}
