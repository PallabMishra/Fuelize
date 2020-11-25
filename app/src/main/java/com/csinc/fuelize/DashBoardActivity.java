package com.csinc.fuelize;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.csinc.fuelize.appdata.SharedPref;
import com.csinc.fuelize.db.DatabaseHelper;
import com.csinc.fuelize.model.OrderData;
import com.csinc.fuelize.service.LocationService;
import com.csinc.fuelize.service.UpdateOrderLogToServer;
import com.csinc.fuelize.service.UpdateOrders;
import com.csinc.fuelize.service.UpdateTripLog;
import com.csinc.fuelize.service_call.VolleyCall;
import com.csinc.fuelize.utility.Constants;
import com.csinc.fuelize.utility.MainApplication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    Context mContext;
    AppCompatActivity mActivity;
    String driverId, driverName;
    TextView welcomeMsg;
    TextView Odometer, QtyTotalDecant, OrderQty, QtyDecant, QtyRemain;
    SharedPref sharedPref;
    DatabaseHelper helper;
    int IDLE_DELAY_MINUTES = 30;
    Handler _idleHandler = new Handler();
    Runnable _idleRunnable = new Runnable() {
        @Override
        public void run() {
            //handle your IDLE state
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setMessage("Kindly Proceed to Use this App?");
            alertDialogBuilder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        dialog.cancel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setTitle("Inactive User!");
            if (!alertDialog.isShowing()) {
                alertDialog.show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        mContext = this;
        mActivity = this;
        sharedPref = new SharedPref();
        driverId = sharedPref.getPref(mContext).getString("driverId", "0");
        driverName = sharedPref.getPref(mContext).getString("driverName", "0");
        startService();
        setView();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onResume() {
        try {
            //setConnection(true);
            helper = new DatabaseHelper(mContext);

            //Set Odometer Reading
            String odometer = SharedPref.getOdometerReading(mContext);
            int OdometerReading;
            if (!TextUtils.isEmpty(odometer)) {
                OdometerReading = Math.round(Float.valueOf(odometer));
            } else {
                OdometerReading = 0;
            }
            Odometer.setText("" + OdometerReading);

            //Set Total Decant Quantity
            String totalDecantQty = SharedPref.getTotalDecantQuantity(mContext);
            float TotalDecantQTY;
            if (!TextUtils.isEmpty(totalDecantQty)) {
                TotalDecantQTY = Float.valueOf(totalDecantQty);
            } else {
                TotalDecantQTY = 0;
            }
            QtyTotalDecant.setText("" + String.format("%.2f", TotalDecantQTY));

            //Set Order Quantity
            float Qty = helper.getOrderQty();
            OrderQty.setText("Your Today's Order is " + Qty + " Litter.");

            //Set Decant Quantity
            if (helper.IsDecantQtyClear()) {
                SharedPref.clearDecantQuantity(mContext);
            }
            String decantQty = SharedPref.getDecantQuantity(mContext);
            float DecantQTY;
            if (!TextUtils.isEmpty(decantQty)) {
                DecantQTY = Float.valueOf(decantQty);
            } else {
                DecantQTY = 0;
            }
            QtyDecant.setText("" + String.format("%.2f", DecantQTY));

            //Set Remaining Quantity
            if (Qty > 0) {
                float RemainQty = Qty - DecantQTY;
                QtyRemain.setText("" + String.format("%.2f", RemainQty));
            } else {
                QtyRemain.setText("0");
            }
            super.onResume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

    }

    private void startService() {
        startService(new Intent(this, LocationService.class));
        startService(new Intent(this, UpdateTripLog.class));
        startService(new Intent(this, UpdateOrders.class));
        startService(new Intent(this, UpdateOrderLogToServer.class));
    }

    @SuppressLint("SetTextI18n")
    private void setView() {

        welcomeMsg = findViewById(R.id.driverName);
        welcomeMsg.setText(driverName);

        Odometer = findViewById(R.id.tv_Odometer_Value);
        QtyTotalDecant = findViewById(R.id.tv_dispense_qty);
        QtyTotalDecant.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //   Intent intentOrder = new Intent(DashboardActivity.this, HectroDiagnoseToolActivity.class);
                //  startActivity(intentOrder);
                return true;
            }
        });
        OrderQty = findViewById(R.id.tv_total_order_Qty);
        QtyDecant = findViewById(R.id.tv_total_dispense_qty);
        QtyRemain = findViewById(R.id.tv_total_qty_remaining);
    }

    private void getDashboardData() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("driverId", driverId);
        VolleyCall.sendDashboard(mContext, params, new VolleyCall.MDUNetworkListener() {
            @Override
            public void onSuccess(JSONObject object) {

            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(DashBoardActivity.this, "Error in Loading dashboard!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.my_orders:
                    if (MainApplication.isConnectingToInternet(mContext)) {
                        Intent intentOrder = new Intent(DashBoardActivity.this, OrdersActivity.class);//  ------------------------------
                        startActivity(intentOrder);
                    } else {
                        startActivity(new Intent(mContext, InternetActivity.class));
                    }
                    break;

                case R.id.return_to_ro:
                    DatabaseHelper helper = new DatabaseHelper(DashBoardActivity.this);
                    OrderData orderData = helper.getRODetails();
                    if (orderData.getROLatitude() != null && orderData.getROLongitude() != null) {
                        double ROLatitude = Double.parseDouble(orderData.getROLatitude());
                        double ROLongitude = Double.parseDouble(orderData.getROLongitude());
                        double distance = MainApplication.distance(Constants.latitude, Constants.longitude, ROLatitude, ROLongitude);
                        if (helper.getOrderQty() > 0) {
                            if (helper.IsAllOrdersExecuted()) {
                                if (distance > 0.100) {
                                    //startActivity(new Intent(DashBoardActivity.this, ReturnToRO.class));-----------------------------
                                } else {
                                    Toast.makeText(DashBoardActivity.this, "You are already near to RO!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(DashBoardActivity.this, "Please Complete All Today Orders to Return RO!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DashBoardActivity.this, "No Orders Found for Today!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DashBoardActivity.this, "RO Details Not Found!", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.log_out:
                    final DatabaseHelper helper1 = new DatabaseHelper(DashBoardActivity.this);
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    if (!helper1.IsAllOrdersExecuted()) {
                        alertDialogBuilder.setMessage("You have not Completed Today Orders , You will loose unsaved data from Device after Logout ! \nDo you still want to Logout ?");
                        alertDialogBuilder.setNeutralButton("PROCEED", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPref.setLoginStatus(mContext, 0);
                                helper1.ClearTablesForLogout();
                                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                    } else {
                        alertDialogBuilder.setMessage("Are you sure you want to Logout ?");
                        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPref.setLoginStatus(mContext, 0);
                                helper1.ClearTablesForLogout();
                                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                    }
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.setTitle("MDU - Logout");
                    if (!alertDialog.isShowing()) {
                        alertDialog.show();
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        delayedIdle(IDLE_DELAY_MINUTES);
    }

    private void delayedIdle(int delayMinutes) {
        if (_idleHandler != null && _idleRunnable != null) {
            _idleHandler.removeCallbacks(_idleRunnable);
            _idleHandler.postDelayed(_idleRunnable, (delayMinutes * 1000 * 60));
        }
    }

}