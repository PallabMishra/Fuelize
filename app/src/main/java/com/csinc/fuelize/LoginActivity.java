package com.csinc.fuelize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.csinc.fuelize.appdata.SharedPref;
import com.csinc.fuelize.service_call.VolleyCall;
import com.csinc.fuelize.utility.MainApplication;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fb, google, twitter;
    float v = 0;
    private static final String TAG = "LOGIN";
    AppCompatActivity mActivity;
    EditText etUsername, etPassword;
    TextView tvPassTimer;
    SharedPref sharedPref;
    Button btnLogin;
    int LoginAttempts = 1;
    boolean IsTimerRunning = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mActivity = this;
        getPermissions();
        setView();

        fb = findViewById(R.id.fab_fb);
        google = findViewById(R.id.fab_google);
        twitter = findViewById(R.id.fab_twitter);


        fb.setTranslationY(300);
        google.setTranslationY(300);
        twitter.setTranslationY(300);


        fb.setAlpha(v);
        google.setAlpha(v);
        twitter.setAlpha(v);

        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {

    }

    private boolean getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean permission = checkPermissions();
            if (!permission) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                }, 110);
                return false;
            } else {
                //telephoneInfo();
                return true;
            }
        } else {
            // telephoneInfo();
            return true;
        }
    }

    private boolean checkPermissions() {
        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int result3 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
        int result4 = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int result5 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int result6 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result7 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        return result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED &&
                result3 == PackageManager.PERMISSION_GRANTED && result4 == PackageManager.PERMISSION_GRANTED &&
                result5 == PackageManager.PERMISSION_GRANTED && result6 == PackageManager.PERMISSION_GRANTED &&
                result7 == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    public void telephoneInfo() {
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        Log.e("Phone info", "telephoneInfo: " + telephonyManager.getDeviceId());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e("Phone info", "telephoneInfo: " + telephonyManager.getImei());
        }
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 110:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    telephoneInfo();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }*/

    private void setView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        tvPassTimer = findViewById(R.id.tv_pass_timer);
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    public void onClick(View view) {
        if (!MainApplication.isConnectingToInternet(mActivity)) {
            startActivity(new Intent(mActivity, InternetActivity.class));
            return;
        }
        switch (view.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(etUsername.getText())) {
                    Toast.makeText(mActivity, "Please Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(etPassword.getText())) {
                    Toast.makeText(mActivity, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!checkPermissions()) {
                    Toast.makeText(mActivity, "Please Allow all Permissions to Use App", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (LoginAttempts <= 5) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("OperatorUserName", etUsername.getText().toString().trim());
                    params.put("OperatorPassword", etPassword.getText().toString().trim());
                    VolleyCall.sendLogIn(this, params, new VolleyCall.MDUNetworkListener() {
                        @Override
                        public void onSuccess(JSONObject object) {
                            setLoginStatus(object);
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Toast.makeText(LoginActivity.this, "Attempt: " + LoginAttempts + "  - Invalid Credentials!", Toast.LENGTH_SHORT).show();
                            if (LoginAttempts == 5) {
                                if (!IsTimerRunning) {
                                    tvPassTimer.setVisibility(View.VISIBLE);
                                    LogInAttemptTimer(1000, 180000);
                                }
                            }
                            LoginAttempts++;
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Your access has been block for some time \n due to Invalid Login Attempts.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void setLoginStatus(JSONObject jsonDataO) {
        Log.e(TAG, "setLoginStatus: " + jsonDataO);
        try {
            if (jsonDataO.getBoolean("status")) {
                JSONObject mObject = jsonDataO.getJSONObject("data");
                if (mObject != null) {
                    sharedPref = new SharedPref();
                    SharedPreferences.Editor editor = sharedPref.getPref(mActivity).edit();
                    editor.putString("driverId", mObject.getString("driverID"));
                    editor.putString("driverName", mObject.getString("driverName"));
                    editor.putString("vehicleNumber", mObject.getString("driverVehicleRegNumber"));
                    editor.putString("mobileNumber", mObject.getString("driverMobileNumber"));
                    editor.putString("vehicleID", mObject.getString("driverVehicleRegNumber"));
                    editor.apply();
                }
                Toast.makeText(mActivity, "Login Successfully!", Toast.LENGTH_SHORT).show();
                /* navigating to password activity */
                Intent intent = new Intent(this, DownloadActivity.class); //--------------------------------------------- class not present-----
                startActivity(intent); //----------------------------------------------------
                SharedPref.setLoginStatus(mActivity, 1);
                finish();
            } else {
                Log.e("Login Error Message", jsonDataO.getString("message"));
                Toast.makeText(mActivity, "Attempt : " + LoginAttempts + " - Invalid Credentials!", Toast.LENGTH_LONG).show();
                if (LoginAttempts >= 5) {
                    if (!IsTimerRunning) {
                        tvPassTimer.setVisibility(View.VISIBLE);
                        LogInAttemptTimer(1000, 180000);
                    }
                }
                LoginAttempts++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void LogInAttemptTimer(final int timer, final int min) {
        countDownTimer = new CountDownTimer(min, timer) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                IsTimerRunning = true;
                String LoginTimeMin = "0";
                String LoginTimeSec;
                Log.e("LoginTimer:", " " + millisUntilFinished);
                if (millisUntilFinished > 60000) {
                    LoginTimeMin = String.valueOf(millisUntilFinished / 60000);
                    LoginTimeSec = String.valueOf(millisUntilFinished % 60000 / 1000);
                } else {
                    LoginTimeSec = String.valueOf(millisUntilFinished % 60000 / 1000);
                }
                tvPassTimer.setText(" " + LoginTimeMin + " : " + LoginTimeSec);
            }

            @Override
            public void onFinish() {
                IsTimerRunning = false;
                LoginAttempts = 1;
                tvPassTimer.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Try Login with Valid Credentials!", Toast.LENGTH_SHORT).show();
            }
        };
        countDownTimer.start();
    }
}