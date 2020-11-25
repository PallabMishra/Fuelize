package com.csinc.fuelize.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.csinc.fuelize.R;
import com.csinc.fuelize.model.OrderData;
import com.csinc.fuelize.utility.Constants;

import java.util.List;

public class OrdersRecyclerAdapter extends RecyclerView.Adapter<OrdersRecyclerAdapter.ViewHolder> {

    private List<OrderData> orderData;
    private Context activity;

    public OrdersRecyclerAdapter(List<OrderData> orderData, Context activity, int i) {
        this.orderData = orderData;
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrdersRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_custom_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersRecyclerAdapter.ViewHolder viewHolder, final int i) {

        final OrderData order = orderData.get(i);
        viewHolder.col_1.setText(order.getOrderCustomerName());
        viewHolder.col_2.setText(order.getOrderCRMNumber());
        viewHolder.col_3.setText(getStatus(order.getStatus()));
        viewHolder.col_4.setText(String.valueOf(Float.valueOf(order.getOrderQuantity())));
        viewHolder.col_5.setText(String.valueOf(order.getOrderDistance()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Bundle mBundle;
                String Status = order.getStatus();
                String OrderId = order.getOrderId();

                switch (Status) {

                /*    case "1":
                    case "E0001":   //Order Created
                    case "E0009":   //Order Start
                    case "E00010":  //Order Downloaded
                    case "C0001":   //Order On Hold
                        intent = new Intent(activity, ViewOrderData.class);
                        mBundle = new Bundle();
                        mBundle.putString("order_id", OrderId);
                        intent.putExtras(mBundle);
                        activity.startActivity(intent);
                        break;

                    case "E0003": //Reached at Customer
                    case "E00041": //Reached at Customer Manually
                        intent = new Intent(activity, HectronicsDecantActivity.class);
                        mBundle = new Bundle();
                        mBundle.putString("order_id", OrderId);
                        intent.putExtras(mBundle);
                        activity.startActivity(intent);
                        break;

                    case "E0006":   //Order Return Initiated at RO
                        intent = new Intent(activity, ReturnViewOrderData.class);
                        mBundle = new Bundle();
                        mBundle.putString("order_id", OrderId);
                        intent.putExtras(mBundle);
                        activity.startActivity(intent);
                        break;

                    case "C0002": // Reached at RO
                    case "C0003": // Reached at RO Manually
                        intent = new Intent(activity, HectronicsReturnDecantActivity.class);
                        mBundle = new Bundle();
                        mBundle.putString("order_id", OrderId);
                        intent.putExtras(mBundle);
                        activity.startActivity(intent);
                        break;   */
                }
                if (!Status.equals("E0004") && !Status.equals("E0005")) {
                    ((Activity) activity).finish();
                }
            }
        });
    }

    private String getStatus(String status) {
//        1 : created 2 : onGoing 3 : completed 4 : dropped/ changed dues to some problem
        if (status.equalsIgnoreCase(Constants.ORDER_CREATED)) {
            return "Available";
        } else if (status.equalsIgnoreCase(Constants.ORDER_DOWNLOADED)) {
            return "Downloaded";
        } else if (status.equalsIgnoreCase(Constants.ORDER_START)) {
            return "On Going";
        } else if (status.equalsIgnoreCase(Constants.REACHED_AT_CUSTOMER)) {
            return "Customer Place";
        } else if (status.equalsIgnoreCase(Constants.REACHED_AT_CUSTOMER_MANUALLY)) {
            return "Customer Place Manually";
        } else if (status.equalsIgnoreCase(Constants.ORDER_DELIVERED)) {
            return "Completed";
        } else if (status.equalsIgnoreCase(Constants.ORDER_ON_HOLD)) {
            return "On Hold";
        } else if (status.equalsIgnoreCase(Constants.ORDER_RETURN)) {
            return "Returned Initiated";
        } else if (status.equalsIgnoreCase(Constants.REACHED_AT_RO)) {
            return "RO Place";
        } else if (status.equalsIgnoreCase(Constants.REACHED_AT_RO_MANUALLY)) {
            return "RO Place Manually";
        } else if (status.equalsIgnoreCase(Constants.ORDER_CANCELLED)) {
            return "Cancelled";
        } else if (status.equalsIgnoreCase(Constants.ORDER_CANCELLED_ADMIN)) {
            return "Dropped";
        }
        return "Start";
    }

    @Override
    public int getItemCount() {
        return orderData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView col_1, col_2, col_3, col_4, col_5;
        LinearLayout linearLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            linearLayout = mView.findViewById(R.id.ll_set_bg_color);
            col_1 = mView.findViewById(R.id.col_1);
            col_2 = mView.findViewById(R.id.col_2);
            col_3 = mView.findViewById(R.id.col_3);
            col_4 = mView.findViewById(R.id.col_4);
            col_5 = mView.findViewById(R.id.col_5);
        }
    }
}
