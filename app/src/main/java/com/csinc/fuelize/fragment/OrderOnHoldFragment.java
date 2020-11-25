package com.csinc.fuelize.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.csinc.fuelize.R;
import com.csinc.fuelize.adapter.OrdersRecyclerAdapter;
import com.csinc.fuelize.db.DatabaseHelper;
import com.csinc.fuelize.model.OrderData;
import com.csinc.fuelize.utility.Constants;

import java.util.ArrayList;
import java.util.List;

public class OrderOnHoldFragment extends Fragment {
    RecyclerView recyclerView;
    OrdersRecyclerAdapter mAdapter;
    Context mContext;
    DatabaseHelper helper;
    TextView tvNoOrders;

    private List<OrderData> orderDataList;
    private List<OrderData> orderPendingDataList;

    //Override method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        View view = inflater.inflate(R.layout.frag_order_list, container, false);
        mContext = getContext();
        helper = new DatabaseHelper(mContext);
        tvNoOrders = view.findViewById(R.id.tv_no_orders_found);
        recyclerView = view.findViewById(R.id.recycler_order);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        orderDataList = new ArrayList<>();
        orderPendingDataList = new ArrayList<>();
        try {
            orderDataList.clear();
            orderDataList = helper.getAllOrderData();
            for (int i = 0; i < orderDataList.size(); i++) {
                if (orderDataList.get(i).getStatus().equalsIgnoreCase(Constants.ORDER_ON_HOLD)) {
                    orderPendingDataList.add(orderDataList.get(i));
                }
            }
            if (orderPendingDataList.size() > 0) {
                mAdapter = new OrdersRecyclerAdapter(orderPendingDataList, mContext, 1);
                tvNoOrders.setVisibility(View.GONE);
            } else {
                tvNoOrders.setVisibility(View.VISIBLE);
            }
            mAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}

