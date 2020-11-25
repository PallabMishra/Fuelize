package com.csinc.fuelize.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.csinc.fuelize.appdata.SharedPref;
import com.csinc.fuelize.db.DatabaseHelper;
import com.csinc.fuelize.model.OrderData;
import com.csinc.fuelize.service_call.CustomRequestQueue;
import com.csinc.fuelize.service_call.VolleyCall;
import com.csinc.fuelize.utility.Constants;
import com.csinc.fuelize.utility.MainApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateOrders extends Service {

    private static final String TAG = "UPDATE ORDER BY SERVICE";
    private static Timer timer = new Timer();
    SharedPref mShared;
    String userId, tripID, vehicleId;
    private Context contextLog;
    @SuppressLint("HandlerLeak")
    private final Handler toastHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            HashMap<String, String> params = new HashMap<>();
            params.put("driverID", userId);
            params.put("orderType", "2");
            if (MainApplication.isConnectingToInternet(contextLog)) {
                getOrderData(Constants.URL_ORDER_LIST, params);
            } else {
                Toast.makeText(contextLog, "No Internet Found", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        contextLog = this;
        mShared = new SharedPref();
        //Toast.makeText(mContext, "Service Location Update", Toast.LENGTH_LONG).show();
        userId = mShared.getPref(contextLog).getString("driverId", null);
        tripID = mShared.getPref(contextLog).getString("tripID", null);
        vehicleId = mShared.getPref(contextLog).getString("vehicleID", null);
        startService();
    }

    private void startService() {
        timer.scheduleAtFixedRate(new mainTask(), 0, (5 * 60 * 1000));
    }

    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Stopped ...", Toast.LENGTH_SHORT).show();
    }

    public void getOrderData(String url, final Map<String, String> dataForPost) {
        Log.e(TAG, "setMDUList: " + url);
        Log.e(TAG, "setMDUList: " + dataForPost);
        final DatabaseHelper helper = new DatabaseHelper(contextLog);
        try {
            JSONObject jsonObject = new JSONObject(dataForPost);
            RequestQueue mRequestQueue = CustomRequestQueue.getInstance(contextLog).getRequestQueue();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onResponse(JSONObject response) {
                    if (response != null) {
                        Log.e(TAG, "setMDUList: " + response);
                        JSONArray mArray;
                        try {
                            helper.deleteOrderData();
                            mArray = response.getJSONArray("data");
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
                                setOrderDownloadStatus(mduData.getOrderId());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {
                        Log.e(TAG, "onErrorResponse: 1" + error.getMessage() + error.getLocalizedMessage());
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(7000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setOrderDownloadStatus(String orderId) {
        try {
            HashMap<String, String> paramsMDU = new HashMap<>();
            paramsMDU.put("orderNumber", orderId);
            paramsMDU.put("driverID", userId);
            paramsMDU.put("statusCode", Constants.ORDER_DOWNLOADED);
            paramsMDU.put("productID", "2");
            VolleyCall.sendTripUpdate(contextLog, paramsMDU, new VolleyCall.MDUNetworkListener() {
                @Override
                public void onSuccess(JSONObject object) {

                }

                @Override
                public void onError(VolleyError error) {
                    Toast.makeText(UpdateOrders.this, "Error in Sending Trip Update!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class mainTask extends TimerTask {
        public void run() {
            toastHandler.sendEmptyMessage(00);
        }
    }
}
