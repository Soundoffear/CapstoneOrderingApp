package com.example.soundoffear.capstoneorderingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnVegetableSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.VegetableModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SandwichVegetableAdapter_RV extends RecyclerView.Adapter<SandwichVegetableViewHolder> implements OnVegetableSelectedListener {

    private Context mContext;
    private List<VegetableModel> vegetableModelsList;
    private OnVegetableSelectedListener onVegetableSelectedListener;
    private boolean isMultiSelectable;
    private List<VegetableModel> vegetableModelsSelected;

    public SandwichVegetableAdapter_RV(Context mContext, List<VegetableModel> vegetableModelsList, OnVegetableSelectedListener vegesListener,
                                       boolean isMultiSelectable, List<VegetableModel> vegetableModels) {
        this.mContext = mContext;
        this.vegetableModelsList = vegetableModelsList;
        onVegetableSelectedListener = vegesListener;
        this.isMultiSelectable = isMultiSelectable;
        this.vegetableModelsSelected = vegetableModels;
    }

    @NonNull
    @Override
    public SandwichVegetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vegeView = LayoutInflater.from(mContext).inflate(R.layout.item_vegetable, parent, false);

        return new SandwichVegetableViewHolder(vegeView, this, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull final SandwichVegetableViewHolder holder, int position) {
        final VegetableModel vegetableModel = vegetableModelsList.get(position);

        if(vegetableModelsSelected.size() > 0) {
            for(VegetableModel vegeModel: vegetableModelsSelected) {
                if(vegetableModel.equals(vegeModel)) {
                    holder.item_vegetable_rv.setText(vegetableModel.getVegetable());
                    holder.vegetableModel = vegeModel;
                    holder.setSelected(true);
                } else {
                    holder.item_vegetable_rv.setText(vegetableModel.getVegetable());
                    holder.vegetableModel = vegetableModel;
                    holder.setSelected(holder.vegetableModel.isSelected());
                }
            }
        } else {
            holder.item_vegetable_rv.setText(vegetableModel.getVegetable());
            holder.vegetableModel = vegetableModel;
            holder.setSelected(holder.vegetableModel.isSelected());
        }
    }

    @Override
    public int getItemCount() {
        return vegetableModelsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isMultiSelectable) {
            return SandwichVegetableViewHolder.MULTISELECTABLE;
        } else {
            return SandwichVegetableViewHolder.SINGLESELECTABLE;
        }
    }

    @Override
    public void onVegetableSelected(VegetableModel vegetableModel) {
        if (!isMultiSelectable) {
            for (VegetableModel vege : vegetableModelsList) {
                if (!vege.equals(vegetableModel) && vege.isSelected()) {
                    vege.setSelected(false);
                } else if (vege.equals(vegetableModel) && vegetableModel.isSelected()) {
                    vege.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        onVegetableSelectedListener.onVegetableSelected(vegetableModel);
    }
}
