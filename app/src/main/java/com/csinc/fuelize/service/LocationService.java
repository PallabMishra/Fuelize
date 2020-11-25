package com.csinc.fuelize.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.csinc.fuelize.appdata.SharedPref;
import com.csinc.fuelize.db.DatabaseHelper;
import com.csinc.fuelize.model.TripData;
import com.csinc.fuelize.utility.Constants;
import com.csinc.fuelize.utility.MainApplication;


public class LocationService extends Service {
    private static final String TAG = "TEST_GPS";
    private static final int LOCATION_INTERVAL = 10000;
    private static final float LOCATION_DISTANCE = 0f;
    SharedPref mShared;
    Context mContext;
    String userId, tripID, vehicleId, deviceId;
    DatabaseHelper databaseHelper;
    /**
     * interface for clients that bind
     */
    IBinder mBinder;
    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };
    private LocationManager mLocationManager = null;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        mContext = getApplicationContext();
        mShared = new SharedPref();
        Toast.makeText(mContext, "Service Location Update", Toast.LENGTH_LONG).show();
        userId = mShared.getPref(mContext).getString("driverId", null);
        tripID = mShared.getPref(mContext).getString("tripID", null);
        vehicleId = mShared.getPref(mContext).getString("vehicleID", null);
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListeners[1]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListeners[0]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (LocationListener mLocationListener : mLocationListeners) {
                try {
                    mLocationManager.removeUpdates(mLocationListener);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listener, ignore", ex);
                }
            }
        }
        Log.e("Location Service", "------------------------------------------ Destroyed Location update Service");
        startService(new Intent(this, LocationService.class)); // add this line
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
//            Toast.makeText(mContext, "Service LocationListener", Toast.LENGTH_LONG).show();
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            try {
                Log.e(TAG, "onLocationChanged: " + location + "\n" + userId);
                mLastLocation.set(location);
                if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                    Constants.latitude = location.getLatitude();
                    Constants.longitude = location.getLongitude();
                } else {
                    Constants.latitude = 0.0;
                    Constants.longitude = 0.0;
                }
                if (location.getSpeed() != 0.0) {
                    Constants.speed = (int) (location.getSpeed() * 3600) / 1000;
                } else {
                    Constants.speed = 0;
                }
                databaseHelper = new DatabaseHelper(mContext);
               // deviceId = MainApplication.getDeviceId(mContext);

                TripData tripData = new TripData();
                tripData.setTripId(tripID);
                tripData.setDriverId(userId);
                tripData.setVehicleNo(vehicleId);
                tripData.setDeviceId(deviceId);
                tripData.setLatitude(String.valueOf(Constants.latitude));
                tripData.setLongitude(String.valueOf(Constants.longitude));
                tripData.setSpeed(String.valueOf(Constants.speed));
                tripData.setIsSynced("0");
                databaseHelper.insertLatLongData(tripData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }
}
