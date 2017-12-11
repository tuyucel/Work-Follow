package com.example.turgay.workfollow;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Alarm extends AppCompatActivity implements View.OnClickListener {

    Button setAlarm;
    EditText eText;
    AlarmManager am;
    PendingIntent pi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        eText = findViewById(R.id.eText);
        setAlarm = findViewById(R.id.setAlarm);

        setAlarm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setAlarm:
                int time = Integer.parseInt(eText.getText().toString());
                Intent alarm = new Intent(Alarm.this,AlarmService.class);
                pi = PendingIntent.getBroadcast(getApplicationContext(),0,alarm,0);
                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+time*1000, pi);
        }
    }
    }


