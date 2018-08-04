package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnNumberOfPaidAddOnsListener;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnPaidAddsSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.PaidAddsModel;

import java.text.DecimalFormat;
import java.util.List;

public class SandwichPaidAddsAdapter_RV extends RecyclerView.Adapter<SandwichPaidAdds_ViewHolder> implements OnPaidAddsSelectedListener {

    private Context paidContext;
    private List<PaidAddsModel> paidAddsModelList;
    private boolean isMultiSelectable;
    private OnPaidAddsSelectedListener onPaidAddsSelectedListener;
    private String carrierChosen;
    private OnNumberOfPaidAddOnsListener onNumberOfPaidAddOnsListener;

    public SandwichPaidAddsAdapter_RV(Context paidContext, List<PaidAddsModel> paidAddsModelList, boolean isMultiSelectable,
                                      OnPaidAddsSelectedListener onPaidAddsSelectedListener,
                                      String carrierChosen,
                                      OnNumberOfPaidAddOnsListener numberOfPaidAddOnsListener) {
        this.paidContext = paidContext;
        this.paidAddsModelList = paidAddsModelList;
        this.isMultiSelectable = isMultiSelectable;
        this.onPaidAddsSelectedListener = onPaidAddsSelectedListener;
        this.carrierChosen = carrierChosen;
        this.onNumberOfPaidAddOnsListener = numberOfPaidAddOnsListener;
    }

    @NonNull
    @Override
    public SandwichPaidAdds_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(paidContext).inflate(R.layout.item_paidadds, parent, false);

        return new SandwichPaidAdds_ViewHolder(view, paidContext);
    }

    private int d_numberCount;
    private double finalPrice;
    private double d_pricePerOne;

    @Override
    public void onBindViewHolder(@NonNull final SandwichPaidAdds_ViewHolder holder, int position) {
        final PaidAddsModel paidAddsModel = paidAddsModelList.get(position);
        holder.paid_adds_currency_TV.setText(paidContext.getResources().getString(R.string.pln));
        holder.paid_adds_name_tv.setText(paidAddsModel.getPaidAddName());

        d_numberCount = 0;
        finalPrice = 0;

        final String numberCount = holder.paid_adds_number_count_tv.getText().toString();
        d_numberCount = Integer.parseInt(numberCount);
        finalPrice = d_pricePerOne * d_numberCount;
        holder.paid_adds_price_tv.setText(new DecimalFormat("#.##").format(finalPrice));
        if (paidAddsModel.getPaidAddName().equals("Cheese")) {
            holder.paid_adds_number_count_tv.setText("1");
            holder.paidAddsModel = paidAddsModel;
            holder.setSelected(1);
        }


        // Set values once user decides to increase the amount
        holder.paid_adds_positive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d_pricePerOne = Double.parseDouble(paidAddsModelList.get(holder.getAdapterPosition()).getPaidAddPrice());
                if (carrierChosen.equals("SUB30")) {
                    d_pricePerOne = d_pricePerOne * 2;
                }
                d_numberCount = Integer.parseInt(holder.paid_adds_number_count_tv.getText().toString());
                d_numberCount++;
                if (d_numberCount == 1 && paidAddsModel.getPaidAddName().equals("Cheese") || d_numberCount == 0 && paidAddsModel.getPaidAddName().equals("Cheese")) {
                    finalPrice = (d_numberCount - 1) * d_pricePerOne;
                    holder.paid_adds_number_count_tv.setText(String.valueOf(d_numberCount));
                    holder.paid_adds_price_tv.setText(new DecimalFormat("#.##").format(finalPrice));
                    onNumberOfPaidAddOnsListener.onFinalNumberSelected(paidAddsModel, d_numberCount, String.valueOf(finalPrice));
                    holder.paidAddsModel = paidAddsModel;
                    holder.setSelected(d_numberCount);
                } else {
                    if(paidAddsModel.getPaidAddName().equals("Cheese")) {
                        finalPrice = (d_numberCount - 1) * d_pricePerOne;
                        holder.paid_adds_number_count_tv.setText(String.valueOf(d_numberCount));
                        holder.paid_adds_price_tv.setText(new DecimalFormat("0.00").format(finalPrice));
                        onNumberOfPaidAddOnsListener.onFinalNumberSelected(paidAddsModel, d_numberCount, String.valueOf(finalPrice));
                        holder.paidAddsModel = paidAddsModel;
                        holder.setSelected(d_numberCount);
                    } else {
                        finalPrice = d_numberCount * d_pricePerOne;
                        holder.paid_adds_number_count_tv.setText(String.valueOf(d_numberCount));
                        holder.paid_adds_price_tv.setText(new DecimalFormat("0.00").format(finalPrice));
                        onNumberOfPaidAddOnsListener.onFinalNumberSelected(paidAddsModel, d_numberCount, String.valueOf(finalPrice));
                        holder.paidAddsModel = paidAddsModel;
                        holder.setSelected(d_numberCount);
                    }
                }
            }
        });

        // Set the values once user decides to decrease the amount
        holder.paid_adds_negative_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d_pricePerOne = Double.parseDouble(paidAddsModelList.get(holder.getAdapterPosition()).getPaidAddPrice());
                if (carrierChosen.equals("SUB30")) {
                    d_pricePerOne = d_pricePerOne * 2;
                }
                d_numberCount = Integer.parseInt(holder.paid_adds_number_count_tv.getText().toString());
                if (d_numberCount > 0) {
                    d_numberCount--;
                    if(paidAddsModel.getPaidAddName().equals("Cheese") && d_numberCount == 1 || d_numberCount == 0) {
                        holder.paid_adds_number_count_tv.setText(String.valueOf(d_numberCount));
                        holder.paid_adds_price_tv.setText(new DecimalFormat("0.00").format(0));
                        onNumberOfPaidAddOnsListener.onFinalNumberSelected(paidAddsModel, d_numberCount, String.valueOf(finalPrice));
                        holder.paidAddsModel = paidAddsModel;
                        holder.setSelected(d_numberCount);
                    } else {
                        finalPrice = d_numberCount * d_pricePerOne;
                        holder.paid_adds_number_count_tv.setText(String.valueOf(d_numberCount));
                        holder.paid_adds_price_tv.setText(new DecimalFormat("0.00").format(finalPrice));
                        onNumberOfPaidAddOnsListener.onFinalNumberSelected(paidAddsModel, d_numberCount, String.valueOf(finalPrice));
                        holder.paidAddsModel = paidAddsModel;
                        holder.setSelected(d_numberCount);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return paidAddsModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isMultiSelectable) {
            return SandwichPaidAdds_ViewHolder.MULTIPLESECECTION;
        } else {
            return SandwichPaidAdds_ViewHolder.SINGLESELECTION;
        }
    }

    @Override
    public void onSelectedPaidAdd(PaidAddsModel paidAddsModel, int value) {
        if (!isMultiSelectable) {
            for (PaidAddsModel paidAddon : paidAddsModelList) {
                if (!paidAddon.equals(paidAddsModel) && paidAddon.isSelected()) {
                    paidAddon.setSelected(false);
                } else if (paidAddon.equals(paidAddsModel) && paidAddsModel.isSelected()) {
                    paidAddon.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        onPaidAddsSelectedListener.onSelectedPaidAdd(paidAddsModel, value);
    }
}
