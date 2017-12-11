package com.example.turgay.workfollow;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dd.morphingbutton.MorphingButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class Tasks extends AppCompatActivity implements View.OnClickListener{

    private TextView tv,dateText,timeText;
    private EditText addText, nameText;
    private Button viewBtn, tarihAdd, timeAdd,addBtn;
    FirebaseDatabase db1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        init();

    }

    private void init(){
        db1=FirebaseDatabase.getInstance();
        tv = findViewById(R.id.tv);
        addBtn = findViewById(R.id.addBtn);
        addText = findViewById(R.id.addText);
        viewBtn = findViewById(R.id.viewBtn);
        nameText = findViewById(R.id.nameText);
        dateText = findViewById(R.id.dateText);
        tarihAdd = findViewById(R.id.tarihAdd);
        timeText = findViewById(R.id.timeText);
        timeAdd = findViewById(R.id.timeAdd);

        addBtn.setOnClickListener(this);
        viewBtn.setOnClickListener(this);
        tarihAdd.setOnClickListener(this);
        timeAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBtn:
                String task, name,time,date;
                time = timeText.getText().toString().trim();
                date = dateText.getText().toString().trim();
                task = addText.getText().toString().trim();
                name = nameText.getText().toString().trim();
                taskSave(task, name, time, date);
                break;
            case R.id.viewBtn:
                tasksGo();
                break;
            case R.id.tarihAdd:
                goDate();
                break;
            case R.id.timeAdd:
                goTime();
                break;
        }
    }

    public void goDate() {
            Calendar mCurrentTime = Calendar.getInstance();
            int year = mCurrentTime.get(Calendar.YEAR);
            int month = mCurrentTime.get(Calendar.MONTH);
            int dayOfMonth = mCurrentTime.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker;
            datePicker = new DatePickerDialog(Tasks.this, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dateText.setText(dayOfMonth +"/"+month +"/"+ year);
                }
            }, year, month, dayOfMonth);
            datePicker.setTitle("Tarih Seçiniz.");
            datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", datePicker);
            datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", datePicker);
            datePicker.show();

        }

    public void goTime(){
        Calendar mcurrentTime = Calendar.getInstance();//
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog timePicker;

        timePicker = new TimePickerDialog(Tasks.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeText.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);
        timePicker.setTitle("Saat Seçiniz");
        timePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", timePicker);
        timePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", timePicker);

        timePicker.show();
    }

    private void taskSave(String task, String name, String timeText, String dateText){

        DatabaseReference dbRef = db1.getReference("Görevler");
        String key = dbRef.push().getKey();
        DatabaseReference dbRefKeyli = db1.getReference("Görevler/"+key);
        dbRefKeyli.setValue(new TasksAdd(task, name, timeText, dateText));
    }

    private void tasksGo() {
        tv.setText("");

        DatabaseReference db1arrivals = db1.getReference("Görevler");
        db1arrivals.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot arrivals : dataSnapshot.getChildren()) {
                    tv.append(arrivals.getValue(TasksAdd.class).getTask() + "\n");
                    tv.append(arrivals.getValue(TasksAdd.class).getName() + "\n");
                    tv.append(arrivals.getValue(TasksAdd.class).getTime() + "\n");
                    tv.append(arrivals.getValue(TasksAdd.class).getDate() + "\n"
                            +"------------------------------------------------"+"\n");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("hata","patladı");
            }
        });
    }
}