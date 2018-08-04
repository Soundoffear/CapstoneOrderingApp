package com.example.soundoffear.capstoneorderingapp.ordering_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.OrderPlacingActivity;
import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.adapters.SandwichSaucesAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnSauceSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.SaucesModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaucesFragment extends Fragment implements OnSauceSelectedListener {

    @BindView(R.id.sauces_recyclerView)
    RecyclerView sauces_recyclerView;

    List<SaucesModel> saucesModelsSelected;

    public SaucesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View saucesView = inflater.inflate(R.layout.fragment_order_sauces, container, false);
        ButterKnife.bind(this, saucesView);

        saucesModelsSelected = new ArrayList<>();

        Bundle receivedSauceData = getArguments();
        assert receivedSauceData != null;
        List<SaucesModel> saucesModels = receivedSauceData.getParcelableArrayList(OrderPlacingActivity.SANDWICH_SAUCE_BUNDLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        sauces_recyclerView.setLayoutManager(linearLayoutManager);
        sauces_recyclerView.setHasFixedSize(true);
        SandwichSaucesAdapter_RV sandwichSaucesAdapter_rv = new SandwichSaucesAdapter_RV(getContext(), saucesModels, this, true, saucesModelsSelected);

        sauces_recyclerView.setAdapter(sandwichSaucesAdapter_rv);

        return saucesView;
    }

    @Override
    public void onSauceSelected(SaucesModel saucesModel) {

        if (saucesModel.isSelected()) {
            saucesModelsSelected.add(saucesModel);
        } else {
            saucesModelsSelected.remove(saucesModel);
        }
    }

    public String getAllSaucesChosen() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < saucesModelsSelected.size(); i++) {
            if(i < saucesModelsSelected.size() - 1) {
                stringBuilder.append(saucesModelsSelected.get(i).getSauceName()).append("_");
            } else {
                stringBuilder.append(saucesModelsSelected.get(i).getSauceName());
            }
        }
        return stringBuilder.toString();
    }

    public List<SaucesModel> getSaucesModelsSelected() {
        return saucesModelsSelected;
    }

    public void setSaucesModelsSelected(List<SaucesModel> saucesModelsSelected) {
        this.saucesModelsSelected = saucesModelsSelected;
    }
}
