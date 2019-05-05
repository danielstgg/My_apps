package com.aplicationdaniel;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {
    private int waktu_loading=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // hide the action bar

        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent introActivity=new Intent(SplashScreenActivity.this, IntroActivity.class);
                startActivity(introActivity);
                finish();

            }
        },waktu_loading);
    }
}
