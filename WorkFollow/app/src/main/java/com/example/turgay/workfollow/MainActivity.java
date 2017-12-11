package com.example.turgay.workfollow;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase db;
    private Button all_tasks,exit,appExit,map,alarmBtn;
    private Switch servisBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        start();
        servisBTN = findViewById(R.id.servisBTN);
        servisBTN.setChecked(false);
        servisBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Vibrator vibrator = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000);
                if(isChecked){
                    startService(new Intent(getApplicationContext(),Servis.class));
                    Toast.makeText(MainActivity.this, "Servis Açıldı.", Toast.LENGTH_LONG).show();
                }else{
                    stopService(new Intent(getApplicationContext(),Servis.class));
                    Toast.makeText(MainActivity.this, "Servis Kapatıldı.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null)
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN)
            Toast.makeText(this,"Giriş Yapıldı", Toast.LENGTH_LONG).show();
        else if (resultCode == RESULT_CANCELED)
            finish();
    }
    private void start() {
        db = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                    Toast.makeText(MainActivity.this, "Giriş Yaptın", Toast.LENGTH_LONG).show();
                else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
        db = FirebaseDatabase.getInstance();
        all_tasks = findViewById(R.id.all_tasks);
        all_tasks.setOnClickListener(this);
        exit = findViewById(R.id.exit);
        exit.setOnClickListener(this);
        alarmBtn= findViewById(R.id.alarmBtn);
        alarmBtn.setOnClickListener(this);
        appExit = findViewById(R.id.appExit);
        appExit.setOnClickListener(this);
        map = findViewById(R.id.map);
        map.setOnClickListener(this);
            }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.all_tasks:
                Intent intent = new Intent(MainActivity.this, Tasks.class);
                startActivity(intent);
                break;
            case R.id.exit:
                AuthUI.getInstance().signOut(this);
                break;
            case R.id.map:
                Intent maps = new Intent(MainActivity.this, GoogleMap.class);
                startActivity(maps);
                break;
            case R.id.alarmBtn:
                Intent alarm = new Intent(MainActivity.this,Alarm.class);
                startActivity(alarm);
                break;
            case R.id.appExit:
                Intent exit = new Intent(Intent.ACTION_MAIN);
                exit.addCategory(Intent.CATEGORY_HOME);
                exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(exit);
                break;
        }
    }
}