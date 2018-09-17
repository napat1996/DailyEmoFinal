package com.kmutt.dailyemofinal;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kmutt.dailyemofinal.Model.Data;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class DatabaseService {
    FirebaseDatabase database;
    DatabaseReference mRootRef, users;
    Data data = new Data();
    Calendar calendar = Calendar.getInstance();

    private void updateSleepDataToDB() throws IOException, ParseException {
        int heartRateValue = data.getHeartRateValue();
        String heartRateTime = data.getHeartRateTime();
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

    private void updateHeartRateDataToDB() throws IOException, ParseException {
        int heartRateValue = data.getHeartRateValue();
        String heartRateTime = data.getHeartRateTime();

//        DatabaseReference time = mRootRef.child("Time")
        DatabaseReference heartRate = mRootRef.child("HeartRate");
        heartRate.child(heartRateTime).setValue(heartRateValue);


    }
}
