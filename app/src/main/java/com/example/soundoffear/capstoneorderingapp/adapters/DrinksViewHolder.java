package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.DrinksModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrinksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.item_drinks_background)
    ConstraintLayout item_drinks_background;
    @BindView(R.id.item_drinks_name)
    TextView item_drinks_name;
    @BindView(R.id.item_drinks_price)
    TextView item_drinks_price;
    @BindView(R.id.item_drinks_positive)
    ImageButton item_drinks_positive_increase;
    @BindView(R.id.item_drinks_negative)
    ImageButton item_drinks_negative_decrease;
    @BindView(R.id.item_drinks_number_ordered)
    TextView item_drinks_number_ordered;

    private Context drinksContext;
    DrinksModel drinksModel;

    DrinksViewHolder(View itemView, Context drinksContext) {
        super(itemView);
        this.drinksContext = drinksContext;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    public void setSelected(int value) {
        if(isMoreThanZero(value)) {
            item_drinks_background.setBackgroundColor(drinksContext.getResources().getColor(R.color.colorAccent));
        } else  {
            item_drinks_background.setBackgroundColor(drinksContext.getResources().getColor(R.color.background));
        }
        drinksModel.setSelected(isMoreThanZero(value));
    }

    private boolean isMoreThanZero(int value) {
        return value > 0;
    }
}
