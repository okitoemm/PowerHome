package com.g205emmanuelbryanhugo.powerhome;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Starting SplashActivity");
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            Log.d(TAG, "Starting LoginActivity");
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 2000); // 2 secondes
    }
}