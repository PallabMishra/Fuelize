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

import com.android.volley.VolleyError;
import com.csinc.fuelize.appdata.SharedPref;
import com.csinc.fuelize.db.DatabaseHelper;
import com.csinc.fuelize.model.OrderData;
import com.csinc.fuelize.service_call.VolleyCall;
import com.csinc.fuelize.utility.MainApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateOrderLogToServer extends Service {
    private static final String TAG = "UPDATE ORDER LOGSERVICE";
    private static Timer timer = new Timer();
    SharedPref mShared;
    String userId, tripID, vehicleId;
    private Context contextLog;
    @SuppressLint("HandlerLeak")
    private final Handler toastHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                Map<String, String> params = new HashMap<String, String>();
                DatabaseHelper helper = new DatabaseHelper(contextLog);
                List<OrderData> orderData = helper.getAllOrderLogData();

                JSONArray jArray = new JSONArray();// /ItemDetail jsonArray
                for (int i = 0; i < orderData.size(); i++) {
                    JSONObject jGroup = new JSONObject();// /sub Object
                    try {
                        jGroup.put("tripID", tripID);
                        jGroup.put("statusCode", orderData.get(i).getStatus());
                        jGroup.put("time", orderData.get(i).getTime());
                        jGroup.put("productID", "2");
                        jGroup.put("driverID", userId);
                        jGroup.put("orderNumber", orderData.get(i).getOrderId());
                        jGroup.put("vehicleID", vehicleId);
                        jGroup.put("assetID", orderData.get(i).getOrderRFID());
                       // jGroup.put("deviceID", MainApplication.getDeviceId(contextLog));   ------------------ device ID not present
                        jGroup.put("decantLat", orderData.get(i).getDecantLatitude());
                        jGroup.put("decantLong", orderData.get(i).getDecantLongitude());
                        jGroup.put("masterTag", orderData.get(i).getMasterTag());
                        jGroup.put("transactionMDUStartTOT", orderData.get(i).getOrderTotalizerStart());
                        jGroup.put("transactionMDUEndTOT", orderData.get(i).getOrderTotalizerEnd());
                        jGroup.put("transactionMDUStartOdometer", orderData.get(i).getOdometerStart());
                        jGroup.put("transactionMDUEndOdometer", orderData.get(i).getOdometerEnd());
                        jGroup.put("suppliedQuantity", orderData.get(i).getDecantQty());
                        jGroup.put("otp_used", orderData.get(i).getOtpCode());
                        jArray.put(jGroup);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG, "handleMessage: " + jArray);
                params.put("data", String.valueOf(jArray));
                if (MainApplication.isConnectingToInternet(contextLog)) {
                    if (!orderData.isEmpty()) {
                        VolleyCall.sendOrderMultiStatusSync(contextLog, params, new VolleyCall.MDUNetworkListener() {
                            @Override
                            public void onSuccess(JSONObject object) {
                                Log.e(TAG, "onSuccess: " + object);
                                try {
                                    JSONArray array = object.getJSONArray("data");
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject jsonObject = array.getJSONObject(i);
                                        DatabaseHelper handle = new DatabaseHelper(contextLog);
                                        handle.deleteOrderLog(jsonObject.getString("time"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                VolleyCall.InvalidUserLogout(object, contextLog);
                            }

                            @Override
                            public void onError(VolleyError error) {
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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

        timer.scheduleAtFixedRate(new mainTask(), 0, (50 * 1000));
    }

    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Stopped ...", Toast.LENGTH_SHORT).show();
    }

    private class mainTask extends TimerTask {
        public void run() {
            toastHandler.sendEmptyMessage(00);
        }
    }
}
