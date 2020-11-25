package com.csinc.fuelize.appdata;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class SharedPref {

    private static final String PREFERENCE_NAME = "FUELIZE";
    private static final String KEY_ODOMETER_READING = "odometerReading";
    private static final String KEY_LOGIN_STATUS = "LoginStatus";
    private static final String WELCOME_STATUS = "firstTime";
    private static final String KEY_DECANT_QUANTITY = "decantQuantity";
    private static final String KEY_TOTAL_DECANT_QUANTITY = "totalDecantQuantity";
    private static final String KEY_TOTALIZER_READING = "totalizerReading";
    private static SharedPreferences sharedPreferences;

    /**
     * method to get the status of login for the application
     *
     * @param context
     * @return login status
     */
    public static int getLoginStatus(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_LOGIN_STATUS, 0);
    }

    /**
     * method to set the login status for the application
     * 1 - login done
     * 0 - pending
     *
     * @param context
     * @param status
     */
    public static void setLoginStatus(Context context, int status) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_LOGIN_STATUS, status);
        editor.apply();
    }



    public static boolean getWelcomeScreenStatus(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(WELCOME_STATUS,true );
    }


    public static void setWelcomeScreenStatus(Context context, boolean value) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(WELCOME_STATUS, value);
        editor.apply();

    }

    public static String getOdometerReading(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        String odometerReading = sharedPreferences.getString(KEY_ODOMETER_READING, "");
        if (TextUtils.isEmpty(odometerReading)) {
            return "0";
        } else {
            return odometerReading;
        }
    }

    public static void setOdometerReading(Context mContext, String odometerReading) {
        float odometer;
        float odometer_value = 0;
        String odometerValue = getOdometerReading(mContext);
        if (!TextUtils.isEmpty(odometerValue)) {
            odometer_value = Float.valueOf(odometerValue);
        }
        if (odometer_value > 0) {
            odometer = odometer_value + Float.valueOf(odometerReading);
        } else {
            odometer = Float.valueOf(odometerReading);
        }
        if (odometer > 99999) {
            odometer = odometer - 99999;
        }
        sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ODOMETER_READING, String.valueOf(odometer));
        editor.apply();
    }

    public static void setKeyOdometerReading(Context mContext, String odometerReading) {
        sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ODOMETER_READING, odometerReading);
        editor.apply();
    }

    public static String getTotalizerReading(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        String totalizerReading = sharedPreferences.getString(KEY_TOTALIZER_READING, "");
        if (TextUtils.isEmpty(totalizerReading)) {
            return "0.0";
        } else {
            return totalizerReading;
        }
    }

    public static void setTotalizerReading(Context mContext, String totalizerReading) {
        double totalizer;
        double totalizer_value = 0.0;
        String totalizerValue = getTotalizerReading(mContext);
        if (!TextUtils.isEmpty(totalizerValue)) {
            totalizer_value = Double.parseDouble(totalizerValue);
        }
        if (totalizer_value > 0) {
            totalizer = totalizer_value + Double.parseDouble(totalizerReading);
        } else {
            totalizer = Double.parseDouble(totalizerReading);
        }
        if (totalizer > 99999999) {
            totalizer = totalizer - 99999999;
        }
        sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOTALIZER_READING, String.valueOf(totalizer));
        editor.apply();
    }

    public static String getDecantQuantity(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DECANT_QUANTITY, "");
    }

    public static void setDecantQuantity(Context mContext, String decantQty) {
        float actualDecantQty;
        float decant_qty = 0;
        String qty = getDecantQuantity(mContext);
        if (!TextUtils.isEmpty(qty)) {
            decant_qty = Float.valueOf(qty);
        }
        if (decant_qty > 0) {
            actualDecantQty = decant_qty + Float.valueOf(decantQty);
        } else {
            actualDecantQty = Float.valueOf(decantQty);
        }
        sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_DECANT_QUANTITY, String.valueOf(actualDecantQty));
        editor.apply();
    }

    public static void clearDecantQuantity(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_DECANT_QUANTITY, "0");
        editor.apply();
    }

    public static String getTotalDecantQuantity(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOTAL_DECANT_QUANTITY, "");
    }

    public static void setTotalDecantQuantity(Context mContext, String decantQty) {
        float actualTotalDecantQty;
        float total_decant_qty = 0;
        String qty = getTotalDecantQuantity(mContext);
        if (!TextUtils.isEmpty(qty)) {
            total_decant_qty = Float.valueOf(qty);
        }
        if (total_decant_qty > 0) {
            actualTotalDecantQty = total_decant_qty + Float.valueOf(decantQty);
        } else {
            actualTotalDecantQty = Float.valueOf(decantQty);
        }
        sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOTAL_DECANT_QUANTITY, String.valueOf(actualTotalDecantQty));
        editor.apply();
    }

    /**
     * get shared preference reference
     */
    public SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }
}
