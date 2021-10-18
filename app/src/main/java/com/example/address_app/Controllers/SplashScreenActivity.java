package com.example.address_app.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.address_app.R;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // splash screen
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_screen_splash);
        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashScreenActivity.this, AddressDisplayActivity.class);
            startActivity(i);
            finish();
        }, SPLASH_SCREEN_TIME_OUT);
    }


}