package com.storyview.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.storyview.R;
import com.storyview.Utils.SessionManager;


public class ActivitySplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    //SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createstatus);
        getSupportActionBar().hide();

      //  sessionManager = new SessionManager(ActivitySplashScreen.this);

//
//        new Handler().postDelayed(new Runnable() {
//
//
//            @Override
//            public void run() {
//
//
//                if (sessionManager.isLogin()) {
//
//
//                    Intent i = new Intent(ActivitySplashScreen.this, MainActivity.class);
//                    startActivity(i);
//                    finish();
//                    return;
//
//
//                } else {
//                    Intent i = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
//                    startActivity(i);
//                    finish();
//                    return;
//
//
//                }
//
//
//            }
//        }, SPLASH_TIME_OUT);
    }


}
