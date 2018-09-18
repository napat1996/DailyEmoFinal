package com.kmutt.dailyemofinal;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kmutt.dailyemofinal.Model.Data;
import com.kmutt.dailyemofinal.Model.User;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class DatabaseService {
    FirebaseDatabase database;
    DatabaseReference mRootRef, users;
    Data data = new Data();
    User user = new User();
    Calendar calendar = Calendar.getInstance();
    LoginActivity loginActivity = new LoginActivity();

    private void updateSleepDataToDB() throws IOException, ParseException {

        long sleepMinute = data.getMinutesAsleep();
        String rem = data.getRem();
        String deep = data.getDeep();
        String light = data.getlight();
        String wake = data.getWake();
        String dateOfSleep = data.getDateOfSleep();
        String startTime = data.getStartTimeOfSleep();
        String endTime = data.getEndTimeOfSleep();
        String stages = data.getSleepStage();

//        DatabaseReference time = mRootRef.child("Time")
        DatabaseReference sleep = mRootRef.child("Sleep");
        sleep.child(dateOfSleep).setValue(sleepMinute);
        Log.e(TAG, "updateSleepDataToDB: Sleep Minute : "+ sleepMinute );
        sleep.child(dateOfSleep).setValue(stages);
        Log.e(TAG, "updateSleepDataToDB: Sleep Stages : "+ stages );
    }

    public void updateHeartRateDataToDB() throws IOException, ParseException {
        int heartRateValue = data.getHeartRateValue();
        String heartRateTime = data.getHeartRateTime();
        Log.d(TAG, "updateSleepDataToDB: "+ user.getUsername());
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference("Users/"+ loginActivity.mUsername);

//        DatabaseReference time = mRootRef.child("Time")
        DatabaseReference heartRate = mRootRef.child("HeartRate");
        heartRate.child(heartRateTime).setValue(heartRateValue);


    }
}
