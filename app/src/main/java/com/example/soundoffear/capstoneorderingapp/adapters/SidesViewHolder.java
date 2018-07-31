package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.SidesModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SidesViewHolder extends RecyclerView.ViewHolder {

    public static final int SINGLESELECT = 1;
    public static final int MULTISELECT = 2;

    private Context context;
    SidesModel sidesModel;

    @BindView(R.id.item_sides_layout)
    ConstraintLayout item_sides_layout;
    @BindView(R.id.item_sides_name)
    TextView item_sides_name;
    @BindView(R.id.item_sides_number_ordered)
    TextView item_sides_number_ordered;
    @BindView(R.id.item_sides_price)
    TextView item_sides_price;
    @BindView(R.id.item_sides_positive)
    ImageButton item_sides_positive;
    @BindView(R.id.item_sides_negative)
    ImageButton item_sides_negative;

    public SidesViewHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
    }

    public void setSidesSelected(int value) {
        if(isMoreThanZero(value)) {
            item_sides_layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_layout_selected));
        } else {
            item_sides_layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_layout));
        }
        sidesModel.setSideSelected(isMoreThanZero(value));
    }

    private boolean isMoreThanZero(int value) {
        return value > 0;
    }
}
