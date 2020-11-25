package com.csinc.fuelize;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.csinc.fuelize.appdata.SharedPref;
import com.csinc.fuelize.service_call.VolleyCall;
import com.csinc.fuelize.utility.MainApplication;
import com.ncorti.slidetoact.SlideToActView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DownloadActivity extends AppCompatActivity {

    Context mContext;
    SlideToActView proceed;
    String driverId, vehicleId, fuelQuantity = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        mContext = this;
        SharedPref sharedPref = new SharedPref();
        driverId = sharedPref.getPref(mContext).getString("driverId", "0");
        vehicleId = sharedPref.getPref(mContext).getString("vehicleID", null);
        setView();
    }

    @Override
    public void onBackPressed() {

    }

    @SuppressLint("SetTextI18n")
    private void setView() {
        proceed = findViewById(R.id.button_proceed_order);
        proceed.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                setTripStarted();
            }
        });
    }

    private void setTripStarted() {
        HashMap<String, String> paramsMDU = new HashMap<>();
       // paramsMDU.put("deviceID", MainApplication.getDeviceId(this)); // device ID Should be removed.....
        paramsMDU.put("vehicleID", vehicleId);
        paramsMDU.put("driverID", driverId);
        paramsMDU.put("orderQuantity", fuelQuantity);
        paramsMDU.put("suppliedQuantity", fuelQuantity);
        paramsMDU.put("tripType", "2");
        VolleyCall.sendTripStart(mContext, paramsMDU, new VolleyCall.MDUNetworkListener() {
            @Override
            public void onSuccess(JSONObject object) {
                checkTripStartedStatus(object);
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(com.csinc.fuelize.DownloadActivity.this, "Error in Sending Trip Start!", Toast.LENGTH_SHORT).show();
                proceed.resetSlider();
            }
        });
    }

    public void checkTripStartedStatus(JSONObject jsonDataO) {
        /* Order Downloaded Successfully set is_downloaded =1 . we are using this para at Passcode Activity*/
        try {
            if (jsonDataO.getBoolean("status")) {
                try {
                    JSONObject object = jsonDataO.getJSONObject("data");
                    SharedPref sharedPref = new SharedPref();
                    SharedPreferences.Editor editor = sharedPref.getPref(mContext).edit();
                    editor.putString("tripID", object.getString("tripID"));
                    editor.apply();
                    Toast.makeText(mContext, "" + jsonDataO.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SharedPref sharedPref = new SharedPref();
                SharedPreferences.Editor editor = sharedPref.getPref(mContext).edit();
                editor.putString("is_downloaded", "1");
                editor.apply();
                editor.commit();
                Intent intent = new Intent(DownloadActivity.this, DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
