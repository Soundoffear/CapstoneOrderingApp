package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnOrderTypeSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.OrderTypeModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderType_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.order_type_order)
    TextView order_type_order;

    @BindView(R.id.order_type_background)
    ConstraintLayout order_type_background;

    public static final int MULTISELECT = 2;
    public static final int SINGLESECECT = 1;

    private OnOrderTypeSelectedListener onOrderTypeSelectedListener;
    private Context orderContext;
    OrderTypeModel orderTypeModel;

    public OrderType_ViewHolder(View itemView, OnOrderTypeSelectedListener orderTypeSelectedListener, Context context) {
        super(itemView);
        this.orderContext = context;
        ButterKnife.bind(this, itemView);
        this.onOrderTypeSelectedListener = orderTypeSelectedListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(orderTypeModel.isSelected() && getItemViewType() == MULTISELECT) {
            setSelected(false);
        } else {
            setSelected(true);
        }
        onOrderTypeSelectedListener.onOrderTypeSelected(orderTypeModel);
    }

    public void setSelected(boolean isSelected) {
        if(isSelected) {
            order_type_background.setBackgroundDrawable(orderContext.getResources().getDrawable(R.drawable.circular_layout_selected));
        } else {
            order_type_background.setBackgroundDrawable(orderContext.getResources().getDrawable(R.drawable.circular_layout));
        }
        orderTypeModel.setSelected(isSelected);
    }

}
