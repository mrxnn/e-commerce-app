package com.example.e_commerce_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    public static boolean IS_LOGGED_IN = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // hide status bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, IS_LOGGED_IN ? MainActivity.class : LoginActivity.class));
                finish(); // no going back
            }
        }, 2000);
    }
}