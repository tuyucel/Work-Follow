package com.example.turgay.workfollow;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {
    private ProgressBar pBar;
    private TextView textView4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        pBar = findViewById(R.id.progressBar);
        textView4 = findViewById(R.id.textView4);
        registerReceiver(ya, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        Thread mSplashThread;
        mSplashThread = new Thread() {
            public void run() {
                try {
                    synchronized (this) {
                        wait(3000);
                    }
                } catch (InterruptedException ex) {

                } finally {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        mSplashThread.start();
        animasyonCalistir();
    }
    private void animasyonCalistir(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation);
        ImageView logo = findViewById(R.id.imageView3);
        animation.reset();
        logo.clearAnimation();
        logo.startAnimation(animation);
    }
    private BroadcastReceiver ya = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            int seviye = intent.getIntExtra("level", 10);
            textView4.setText("Batarya Seviyesi: " + Integer.toString(seviye)+ "%");
            int sarj_durumum = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
            if (sarj_durumum == BatteryManager.BATTERY_PLUGGED_AC ||
                    sarj_durumum ==BatteryManager.BATTERY_PLUGGED_USB){
                textView4.append(("\nTelefon Şarj Oluyor."));
            }
        }
    };
}