package com.example.mykominfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

@SuppressLint("CustomSplashScreen")
public class SplashScreenStart extends AppCompatActivity {

    private static final long SPLASH_DURATION = 5000; // 5000 ms -> 5 s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen_start);
        // Delay selama 5 detik dan kemudian jalankan MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenStart.this, Login.class);
                startActivity(intent);
                finish(); // Menutup splash start activity
            }
        }, SPLASH_DURATION);
    }
}