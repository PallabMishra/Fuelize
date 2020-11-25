package com.csinc.fuelize;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.VolleyError;
import com.csinc.fuelize.adapter.CustomPagerAdapter;
import com.csinc.fuelize.appdata.SharedPref;
import com.csinc.fuelize.db.DatabaseHelper;
import com.csinc.fuelize.fragment.OrderCompletedFragment;
import com.csinc.fuelize.fragment.OrderOnHoldFragment;
import com.csinc.fuelize.fragment.OrderPendingFragment;
import com.csinc.fuelize.fragment.OrderReturnFragment;
import com.csinc.fuelize.model.OrderData;
import com.csinc.fuelize.service_call.VolleyCall;
import com.csinc.fuelize.utility.Constants;
import com.csinc.fuelize.utility.MainApplication;
import com.google.android.material.tabs.TabLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private static final String TAG = "ORDER ACTIVITY";
    public List<OrderData> orderDataList = new ArrayList<>();
    TextView textViewHead;
    Toolbar mToolbar;
    Context mContext;
    AppCompatActivity mActivity;
    String driverId;
    ImageButton dashboard, orders, back;
    DatabaseHelper helper;
    ImageView downloadOrders;
    ViewPager viewPager;
    TabLayout tabLayout;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        mContext = this;
        mActivity = this;
        setView();
        helper = new DatabaseHelper(mActivity);

        sharedPref = new SharedPref();
        driverId = sharedPref.getPref(mContext).getString("driverId", "0");
        Log.e(TAG, "onCreate: " + helper.getAllOrderData().size());
        if (helper.getAllOrderData().size() == 0) {
            getOrders();
        }

        downloadOrders = findViewById(R.id.btn_download_orders);
        downloadOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainApplication.isConnectingToInternet(mContext)) {
                    getOrders();
                } else {
                    startActivity(new Intent(mContext, InternetActivity.class));
                }
            }
        });

        orders = findViewById(R.id.nav_orders);

        dashboard = findViewById(R.id.nav_dashboard);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mContext, DashBoardActivity.class);
                startActivity(mIntent);
                finish();
            }
        });

        back = findViewById(R.id.nav_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "ORDER ACTIVITY onResume: ");
        CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OrderPendingFragment(), "Today's Order");
        adapter.addFragment(new OrderOnHoldFragment(), "Order On Hold");
        adapter.addFragment(new OrderReturnFragment(), "Order Return");
        adapter.addFragment(new OrderCompletedFragment(), "Order Completed");
        viewPager.setAdapter(adapter);
    }

    private void getOrders() {
        HashMap<String, String> params = new HashMap<>();
        params.put("driverID", driverId);
        params.put("orderType", "2"); //1 : pfc 2 : mdu
        VolleyCall.getOrderList(mContext, params, new VolleyCall.MDUNetworkListener() {
            @Override
            public void onSuccess(JSONObject object) {
                setOrderList(object);
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(OrdersActivity.this, "Issue while Downloading Orders from Server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setView() {
        viewPager = findViewById(R.id.vpOrder);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    @SuppressLint("DefaultLocale")
    public void setOrderList(JSONObject jsonDataO) {
        Log.e(TAG, "setMDUList: " + jsonDataO);
        JSONArray mArray;
        try {
            orderDataList = new ArrayList<>();
            helper.deleteOrderData();
            if (jsonDataO.getBoolean("status")) {
                mArray = jsonDataO.getJSONArray("data");
                for (int i = 0; i < mArray.length(); i++) {
                    JSONObject mObject = mArray.getJSONObject(i);
                    OrderData mduData = new OrderData();
                    if (mObject.has("orderNumber")) {
                        mduData.setOrderId(mObject.getString("orderNumber"));
                    } else {
                        mduData.setOrderId("NA");
                    }
                    if (mObject.has("orderCRMNumber")) {
                        mduData.setOrderCRMNumber(mObject.getString("orderCRMNumber"));
                    } else {
                        mduData.setOrderCRMNumber("NA");
                    }
                    if (mObject.has("sap_ID")) {
                        mduData.setOrderSapID(mObject.getString("sap_ID"));
                    } else {
                        mduData.setOrderSapID("NA");
                    }
                    if (mObject.has("customerID")) {
                        mduData.setOrderCustomerID(mObject.getString("customerID"));
                    } else {
                        mduData.setOrderCustomerID("NA");
                    }
                    if (mObject.has("customerName")) {
                        mduData.setOrderCustomerName(mObject.getString("customerName"));
                    } else {
                        mduData.setOrderCustomerName("NA");
                    }
                    if (mObject.has("customerAddress")) {
                        mduData.setOrderCustomerAddress(mObject.getString("customerAddress"));
                    } else {
                        mduData.setOrderCustomerAddress("NA");
                    }
                    if (mObject.has("roName")) {
                        mduData.setRoName(mObject.getString("roName"));
                    } else {
                        mduData.setRoName("NA");
                    }
                    if (mObject.has("roAddress")) {
                        mduData.setRoAddress(mObject.getString("roAddress"));
                    } else {
                        mduData.setRoAddress("NA");
                    }
                    if (mObject.has("statusCode")) {
                        mduData.setStatus(mObject.getString("statusCode"));
                    } else {
                        mduData.setStatus("NA");
                    }
                    if (mObject.has("orderQuantity")) {
                        mduData.setOrderQuantity(mObject.getString("orderQuantity"));
                    } else {
                        mduData.setOrderQuantity("NA");
                    }
                    if (mObject.has("unitPrice")) {
                        mduData.setOrderUnitPrice(mObject.getString("unitPrice"));
                    } else {
                        mduData.setOrderUnitPrice("NA");
                    }
                    if (mObject.has("latitude") && mObject.has("longitude")) {
                        mduData.setLatitude(mObject.getString("latitude"));
                        mduData.setLongitude(mObject.getString("longitude"));
                        double lat = Double.valueOf(mObject.getString("latitude"));
                        double lng = Double.valueOf(mObject.getString("longitude"));
                        if (Constants.latitude == 0.0 && Constants.longitude == 0.0) {
                            Toast.makeText(OrdersActivity.this, "GPS Location Not Found!", Toast.LENGTH_SHORT).show();
                        }
                        double distance = MainApplication.distance(Constants.latitude, Constants.longitude, lat, lng);
                        mduData.setOrderDistance((distance));
                    } else {
                        mduData.setLatitude("0.0");
                        mduData.setLongitude("0.0");
                        mduData.setOrderDistance(0.0);
                    }
                    if (mObject.has("roLatitude") && mObject.has("roLongitude")) {
                        mduData.setROLatitude(mObject.getString("roLatitude"));
                        mduData.setROLongitude(mObject.getString("roLongitude"));
                    } else {
                        mduData.setROLatitude("26.739444");
                        mduData.setROLongitude("83.598924");
                    }
                    if (mObject.has("transactionInvoiceNumber")) {
                        mduData.setOrderInvoiceNumber(mObject.getString("transactionInvoiceNumber"));
                    } else {
                        mduData.setOrderInvoiceNumber("NA");
                    }
                    mduData.setOrderTransactionStatus("0");
                    if (mObject.has("assetID")) {
                        mduData.setOrderRFID(mObject.getString("assetID"));
                    } else {
                        mduData.setOrderRFID("000");
                    }
                    if (mObject.has("roAssetID")) {
                        mduData.setRoRFID(mObject.getString("roAssetID"));
                    } else {
                        mduData.setRoRFID("000");
                    }
                    if (mObject.has("masterTag")) {
                        Constants.MASTER_TAG_ID = mObject.getString("masterTag");
                    }
                    if (mObject.has("otp")) {
                        mduData.setOtp(String.format("%05d", Integer.parseInt(mObject.getString("otp"))));
                    } else {
                        mduData.setOtp("00000");
                    }
                    long result = helper.insertOrderData(mduData);
                    Log.e(TAG, "setOrderList: insertOrderData " + result);
                    orderDataList.add(mduData);
                    setOrderDownloadStatus(mduData.getOrderId());
                }
            } else {
                Toast.makeText(mContext, "No Orders Found", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setOrderDownloadStatus(String orderId) {
//        E00010
        HashMap<String, String> paramsMDU = new HashMap<>();
        paramsMDU.put("orderNumber", orderId);
        paramsMDU.put("driverID", driverId);
        paramsMDU.put("statusCode", Constants.ORDER_DOWNLOADED);
        paramsMDU.put("productID", "2");
        VolleyCall.sendTripUpdate(mContext, paramsMDU, new VolleyCall.MDUNetworkListener() {
            @Override
            public void onSuccess(JSONObject object) {
            }

            @Override
            public void onError(VolleyError error) {
                //Toast.makeText(OrdersActivity.this, R.string.volley_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}