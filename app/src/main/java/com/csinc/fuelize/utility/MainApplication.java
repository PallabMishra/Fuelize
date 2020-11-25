package com.csinc.fuelize.utility;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;


import com.csinc.fuelize.appdata.SharedPref;
import com.csinc.fuelize.db.DatabaseHelper;
import com.csinc.fuelize.model.OrderData;

import java.util.ArrayList;

public class MainApplication extends Application {

    public static final String TAG = "MDU";
    public static double latitude;
    public static double longitude;

    public static boolean isConnectingToInternet(Context context) {
        {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (NetworkInfo anInfo : info)
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
            }
            return false;
        }
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
   /* public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }*/

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lon1);
        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lon2);
        double distanceInMeters = loc1.distanceTo(loc2);
        return (distanceInMeters / 1000);
    }

    public static double CalculateRouteTravel(Context mContext, ArrayList<String> LocationPoints, String orderId) {
        double routeTravel = 0;
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
            OrderData odometerData = new OrderData();
            odometerData.setOrderId(orderId);
            odometerData.setOdometerStart(String.valueOf(Math.round(Float.valueOf(SharedPref.getOdometerReading(mContext)))));
            if (LocationPoints.size() > 1) {
                for (int i = 0; i < LocationPoints.size() - 1; i++) {
                    double Lat1 = Double.parseDouble(LocationPoints.get(i).split(",")[0]);
                    double Long1 = Double.parseDouble(LocationPoints.get(i).split(",")[1]);
                    double Lat2 = Double.parseDouble(LocationPoints.get(i + 1).split(",")[0]);
                    double Long2 = Double.parseDouble(LocationPoints.get(i + 1).split(",")[1]);
                    double CalRouteTravel = distance(Lat1, Long1, Lat2, Long2);
                    routeTravel = routeTravel + CalRouteTravel;
                }
            }
            if (routeTravel > 0) {
                //SharedPref.setOdometerReading(mContext, String.valueOf(routeTravel));
            }
            odometerData.setOdometerEnd(String.valueOf(Math.round(Float.valueOf(SharedPref.getOdometerReading(mContext)))));
            databaseHelper.insertOdometerLogData(odometerData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routeTravel;
    }
}
