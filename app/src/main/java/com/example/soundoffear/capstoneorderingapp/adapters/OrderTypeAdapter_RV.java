package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnOrderTypeSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.OrderTypeModel;

import java.util.List;

public class OrderTypeAdapter_RV extends RecyclerView.Adapter<OrderType_ViewHolder> implements OnOrderTypeSelectedListener {

    private Context orderContext;
    private List<OrderTypeModel> orderTypeModelList;
    private OnOrderTypeSelectedListener onOrderTypeSelectedListener;
    private boolean isMultiSelectable;

    public OrderTypeAdapter_RV(Context orderContext, List<OrderTypeModel> orderTypeModelList, OnOrderTypeSelectedListener onOrderTypeSelectedListener, boolean isMultiSelectable) {
        this.orderContext = orderContext;
        this.orderTypeModelList = orderTypeModelList;
        this.onOrderTypeSelectedListener = onOrderTypeSelectedListener;
        this.isMultiSelectable = isMultiSelectable;
    }

    @NonNull
    @Override
    public OrderType_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(orderContext).inflate(R.layout.item_types, parent,false);

        return new OrderType_ViewHolder(view, this, orderContext);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderType_ViewHolder holder, int position) {

        holder.order_type_order.setText(orderTypeModelList.get(position).getOrderType());

        holder.orderTypeModel = orderTypeModelList.get(position);
        holder.setSelected(holder.orderTypeModel.isSelected());
    }

    @Override
    public int getItemCount() {
        return orderTypeModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isMultiSelectable) {
            return OrderType_ViewHolder.MULTISELECT;
        } else {
            return OrderType_ViewHolder.SINGLESECECT;
        }
    }

    @Override
    public void onOrderTypeSelected(OrderTypeModel orderTypeModel) {
        if(!isMultiSelectable) {
            for(OrderTypeModel orderType: orderTypeModelList) {
                if(!orderType.equals(orderTypeModel) && orderType.isSelected()) {
                    orderType.setSelected(false);
                } else if (orderType.equals(orderTypeModel) && orderTypeModel.isSelected()) {
                    orderType.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        onOrderTypeSelectedListener.onOrderTypeSelected(orderTypeModel);
    }
}
