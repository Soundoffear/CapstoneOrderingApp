package com.example.soundoffear.capstoneorderingapp.ordering_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.OrderPlacingActivity;
import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.adapters.SandwichBreadTypeAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.adapters.SandwichBreadType_ViewHolder;
import com.example.soundoffear.capstoneorderingapp.models.BreadModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BreadTypeFragment extends Fragment implements SandwichBreadType_ViewHolder.OnBreadSelectedListener {

    @BindView(R.id.order_bread_type_recyclerView)
    RecyclerView breadType_recyclerView;

    List<BreadModel> breadTypesList;

    public BreadModel breadModelSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_breadtype, container,false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        assert bundle != null;
        breadTypesList = bundle.getParcelableArrayList(OrderPlacingActivity.SANDWICH_BREAD_TYPES);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        breadType_recyclerView.hasFixedSize();
        breadType_recyclerView.setLayoutManager(gridLayoutManager);
        if(breadModelSelected!= null) {
            Log.d("BREAD BREAD", "HERE IS BREAD _____-------+++++++");
            SandwichBreadTypeAdapter_RV sandwichBreadTypeAdapter_rv = new SandwichBreadTypeAdapter_RV(getContext(),
                    breadTypesList,
                    this,
                    false,
                    breadModelSelected);
            breadType_recyclerView.setAdapter(sandwichBreadTypeAdapter_rv);
        } else {
            Log.d("BREAD BREAD", "HERE IS NO BREAD _____-------+++++++");
            SandwichBreadTypeAdapter_RV sandwichBreadTypeAdapter_rv = new SandwichBreadTypeAdapter_RV(getContext(),
                    breadTypesList,
                    this,
                    false,
                    null);
            breadType_recyclerView.setAdapter(sandwichBreadTypeAdapter_rv);
        }

        return view;
    }

    @Override
    public void onBreadSelected(BreadModel breadModel) {
        breadModelSelected = breadModel;
    }

    public BreadModel getBreadModelSelected() {
        return breadModelSelected;
    }

    public void setBreadModelSelected(BreadModel breadModelSelected) {
        this.breadModelSelected = breadModelSelected;
    }
}
