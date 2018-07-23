package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnSauceSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.SaucesModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SandwichSauces_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final int MULTISELECT = 2;
    public static final int SINGLESELECT = 1;

    private Context mContext;
    SaucesModel saucesModel;
    OnSauceSelectedListener onSauceSelectedListener;

    @BindView(R.id.sauces_textView_name)
    TextView sauces_textview_name;

    @BindView(R.id.sauces_background_cl)
    ConstraintLayout sauces_background_cl;


    public SandwichSauces_ViewHolder(View itemView, OnSauceSelectedListener listener, Context context) {
        super(itemView);
        this.mContext = context;
        this.onSauceSelectedListener = listener;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(saucesModel.isSelected() && getItemViewType() == MULTISELECT) {
            setSelected(false);
        } else {
            setSelected(true);
        }

        onSauceSelectedListener.onSauceSelected(saucesModel);

    }

    void setSelected(boolean isSelected) {
        if(isSelected) {
            sauces_background_cl.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            sauces_background_cl.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        }
        saucesModel.setSelected(isSelected);
    }


}
