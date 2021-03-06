package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.PaidAddsModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SandwichPaidAdds_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final int MULTIPLESECECTION = 2;
    public static final int SINGLESELECTION = 1;

    private Context paidContext;
    PaidAddsModel paidAddsModel;

    @BindView(R.id.paid_adds_background)
    ConstraintLayout paid_adds_background;
    @BindView(R.id.paid_adds_currency)
    TextView paid_adds_currency_TV;
    @BindView(R.id.paid_adds_negative_btn)
    ImageButton paid_adds_negative_btn;
    @BindView(R.id.paid_adds_positive_btn)
    ImageButton paid_adds_positive_btn;
    @BindView(R.id.paid_adds_number)
    TextView paid_adds_number_count_tv;
    @BindView(R.id.paid_adds_price)
    TextView paid_adds_price_tv;
    @BindView(R.id.paid_adds_name_textView) TextView paid_adds_name_tv;

    SandwichPaidAdds_ViewHolder(View itemView, Context context) {
        super(itemView);
        this.paidContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void setSelected(int value) {
        if(isMoreThanZero(value)) {
            paid_adds_background.setBackgroundDrawable(paidContext.getResources().getDrawable(R.drawable.border_layout_selected));
        } else {
            paid_adds_background.setBackgroundDrawable(paidContext.getResources().getDrawable(R.drawable.border_layout));
        }

        paidAddsModel.setSelected(isMoreThanZero(value));
    }

    private boolean isMoreThanZero(int value){
        return value > 0;
    }

    @Override
    public void onClick(View v) {

    }
}
