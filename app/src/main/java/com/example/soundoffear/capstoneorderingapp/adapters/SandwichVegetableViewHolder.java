package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnVegetableSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.VegetableModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SandwichVegetableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final int MULTISELECTABLE = 2;
    public static final int SINGLESELECTABLE = 1;

    private Context vegeContext;
    private OnVegetableSelectedListener onVegetableSelectedListener;
    VegetableModel vegetableModel;

    @BindView(R.id.item_vegetable_root_layout)
    LinearLayout item_vegetable_root_view;
    @BindView(R.id.item_vegetable_text_view)
    TextView item_vegetable_rv;

    SandwichVegetableViewHolder(View itemView, OnVegetableSelectedListener listener, Context context) {
        super(itemView);
        this.vegeContext = context;
        this.onVegetableSelectedListener = listener;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(vegetableModel.isSelected() && getItemViewType() == MULTISELECTABLE) {
            setSelected(false);
        } else {
            setSelected(true);
        }
        onVegetableSelectedListener.onVegetableSelected(vegetableModel);
    }

    public void setSelected(boolean value) {
        if(value) {
            item_vegetable_root_view.setBackgroundDrawable(vegeContext.getResources().getDrawable(R.drawable.border_layout_selected));
        } else {
            item_vegetable_root_view.setBackgroundDrawable(vegeContext.getResources().getDrawable(R.drawable.border_layout));
        }

        vegetableModel.setSelected(value);
    }
}
