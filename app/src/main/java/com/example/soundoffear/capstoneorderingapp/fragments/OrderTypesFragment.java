package com.example.soundoffear.capstoneorderingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.adapters.OrderTypeAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.interfaces.OnOrderTypeSelectedListener;
import com.example.soundoffear.capstoneorderingapp.models.OrderTypeModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderTypesFragment extends Fragment implements OnOrderTypeSelectedListener {

    @BindView(R.id.order_type_recyclerView)
    RecyclerView order_type_recyclerView;
    List<OrderTypeModel> orderTypeModelList;

    private String orderType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_types, container, false);
        ButterKnife.bind(this, view);

        Bundle orderTypesBundle = getArguments();
        assert orderTypesBundle != null;
        orderTypeModelList = orderTypesBundle.getParcelableArrayList(MainPageFragment.LIST_OF_ORDER_TYPES);
        order_type_recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        order_type_recyclerView.setLayoutManager(linearLayoutManager);
        OrderTypeAdapter_RV orderTypeAdapter_rv = new OrderTypeAdapter_RV(getContext(), orderTypeModelList, this, false);
        order_type_recyclerView.setAdapter(orderTypeAdapter_rv);

        return view;
    }

    @Override
    public void onOrderTypeSelected(OrderTypeModel orderTypeModel) {
        orderType = orderTypeModel.getOrderType();
    }

    public String getOrderType() {
        return orderType;
    }
}
