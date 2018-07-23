package com.example.soundoffear.capstoneorderingapp.ordering_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.OrderPlacingActivity;
import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.adapters.SandwichVegetableAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnVegetableSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.VegetableModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VegetableFragment extends Fragment implements OnVegetableSelectedListener {

    @BindView(R.id.order_vegetable_recyclerView)
    RecyclerView order_vegetable_rv;

    ArrayList<String> veges;
    public List<VegetableModel> selectedForSandwichVegetablesList;

    SandwichVegetableAdapter_RV sandwichVegetableAdapter_rv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vegetableView = inflater.inflate(R.layout.fragment_order_vegetables, container, false);
        ButterKnife.bind(this, vegetableView);

        veges = new ArrayList<>();
        selectedForSandwichVegetablesList = new ArrayList<>();

        Bundle vegeBundle = getArguments();
        assert vegeBundle != null;
        List<VegetableModel> vegetableModelList = vegeBundle.getParcelableArrayList(OrderPlacingActivity.SANDWICH_VEGETABLES);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        order_vegetable_rv.setLayoutManager(gridLayoutManager);
        order_vegetable_rv.setHasFixedSize(true);
        sandwichVegetableAdapter_rv = new SandwichVegetableAdapter_RV(getContext(), vegetableModelList, this, true);
        order_vegetable_rv.setAdapter(sandwichVegetableAdapter_rv);

        return vegetableView;
    }

    @Override
    public void onVegetableSelected(VegetableModel vegetableModel) {

        if(vegetableModel.isSelected()) {
            selectedForSandwichVegetablesList.add(vegetableModel);
        } else {
            selectedForSandwichVegetablesList.remove(vegetableModel);
        }

    }

    public String getAllVegesChosen() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < selectedForSandwichVegetablesList.size(); i++) {
            if(i < selectedForSandwichVegetablesList.size() - 1) {
                stringBuilder.append(selectedForSandwichVegetablesList.get(i).getVegetable()).append("_");
            } else {
                stringBuilder.append(selectedForSandwichVegetablesList.get(i).getVegetable());
            }
        }
        return stringBuilder.toString();
    }
}
