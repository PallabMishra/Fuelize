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
import com.csinc.fuelize.model.TripData;
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

public class UpdateTripLog extends Service {
    private static final String TAG = "UPDATE Trip Log";
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
                List<TripData> tripData = helper.getAllLogData();

                JSONArray jArray = new JSONArray();// /ItemDetail jsonArray
                for (int i = 0; i < tripData.size(); i++) {
                    JSONObject jGroup = new JSONObject();// /sub Object
                    try {
                        jGroup.put("deviceID", tripData.get(i).getDeviceId());
                        jGroup.put("vehicleID", tripData.get(i).getVehicleNo());
                        jGroup.put("driverID", tripData.get(i).getDriverId());
                        jGroup.put("tripID", tripData.get(i).getTripId());
                        jGroup.put("tripLocationLat", tripData.get(i).getLatitude());
                        jGroup.put("tripLocationLng", tripData.get(i).getLongitude());
                        jGroup.put("productID", "2");
                        jGroup.put("time", tripData.get(i).getTimeTaken());
                        jGroup.put("orderNumber", tripData.get(i).getOrderID());
                        jGroup.put("tripStatus", "1");
                        jGroup.put("speed", tripData.get(i).getSpeed());
                        jArray.put(jGroup);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG, "handleMessage: " + jArray);
                params.put("data", String.valueOf(jArray));
                if (MainApplication.isConnectingToInternet(contextLog)) {
                    if (!tripData.isEmpty()) {
                        VolleyCall.sendTripMultiStatusSync(contextLog, params, new VolleyCall.MDUNetworkListener() {
                            @Override
                            public void onSuccess(JSONObject object) {
                                Log.e(TAG, "onSuccess: " + object);
                                try {
                                    JSONArray array = object.getJSONArray("data");
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject jsonObject = array.getJSONObject(i);
                                        DatabaseHelper handle = new DatabaseHelper(contextLog);
                                        handle.deleteTripLog(jsonObject.getString("time"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                VolleyCall.InvalidUserLogout(object, contextLog);
                            }

                            @Override
                            public void onError(VolleyError error) {
                                //Toast.makeText(UpdateTripLog.this, R.string.volley_error, Toast.LENGTH_SHORT).show();
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

        timer.scheduleAtFixedRate(new mainTask(), 0, (60 * 1000));
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


