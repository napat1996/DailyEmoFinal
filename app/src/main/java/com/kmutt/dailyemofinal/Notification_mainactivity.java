//package com.kmutt.dailyemofinal;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.app.TimePickerDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.DialogFragment;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.TimePicker;
//
//import java.text.DateFormat;
//import java.util.Calendar;
//
//public class Notification_mainactivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
//private TextView mTextView;
//
//    @Override
//    protected void oncreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notification);
//
//        mTextView = findViewById(R.id.textView_noti);
//
//        Button ButtonTimePicker = findViewById(R.id.button_timepicker);
//        ButtonTimePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment timePicker = new TimePickerFragment();
//                timePicker.show(getSupportFragmentManager(), "Time picker");
//
//            }
//        });
//        Button buttonCancelAlarm = findViewById(R.id.button_cancel);
//        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cancelAlarm();
//            }
//        });
//    }
//
//    @Override
//    public void onTimeSet(TimePicker view, int hourofDay, int minute){
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.HOUR_OF_DAY,hourofDay);
//        c.set(Calendar.MINUTE, minute);
//        c.set(Calendar.SECOND, 0);
//
//        updateTimeText(c);
//        startAlarm();
//    }
//    public void updateTimeText(Calendar c){
//        String timetext = "Alarm set for:";
//        timetext += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
//
//        mTextView.setText(timetext);
//    }
//    private void startAlarm(Calendar c){
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, AlertReciever.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
//
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
//    }
//    private void cancelAlarm(Calendar c){
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, AlertReciever.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
//
//        alarmManager.cancel(pendingIntent);
//        mTextView.setText("Alarm canceled");
//    }
//
//}
