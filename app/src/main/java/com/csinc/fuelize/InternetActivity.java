package com.csinc.fuelize;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.csinc.fuelize.utility.MainApplication;


public class InternetActivity extends AppCompatActivity {
    Button checkI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
        checkI = findViewById(R.id.reconnect);
        checkI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean internet = MainApplication.isConnectingToInternet(com.csinc.fuelize.InternetActivity.this);
                if (internet) {
                    finish();
                } else {
                    Toast.makeText(com.csinc.fuelize.InternetActivity.this, "No Internet Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
