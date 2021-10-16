package com.example.address_app.Controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.address_app.R;
import com.example.address_app.RetrofitBuilder;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait!!");
        progressDialog.setMessage("Wait!!");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressDialog.show();
        RetrofitBuilder.getAddress(progressDialog);
        // splash screen
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_screen_splash);
        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashScreenActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }, SPLASH_SCREEN_TIME_OUT);
    }


}