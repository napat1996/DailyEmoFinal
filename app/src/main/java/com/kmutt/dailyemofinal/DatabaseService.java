package com.kmutt.dailyemofinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kmutt.dailyemofinal.Model.FitbitData;
import com.kmutt.dailyemofinal.Model.User;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class DatabaseService {
    FirebaseDatabase database;
    public static DatabaseReference mRootRef;
    FitbitData data = new FitbitData();
    Calendar calendar = Calendar.getInstance();

    private void updateSleepDataToDB() throws IOException, ParseException {

        long sleepMinute = data.getMinutesAsleep();
        long rem = data.getRem();
        long deep = data.getDeep();
        long light = data.getlight();
        long wake = data.getWake();
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
        Log.e(TAG, "REM =  : "+ rem );
    }

    public void updateHeartRateDataToDB(Context context) throws IOException, ParseException {

        SharedPreferences preferences = context.getSharedPreferences("DailyEmoPref", 0);
        String username = preferences.getString("username", "");

        int heartRateValue = data.getHeartRateValue();
        String heartRateTime = data.getHeartRateTime();
        database = FirebaseDatabase.getInstance();

        mRootRef = database.getReferenceFromUrl("https://dailyemo-194412.firebaseio.com/Users/"+username);

        DatabaseReference heartRate = mRootRef.child("HeartRate");
        heartRate.child(heartRateTime).setValue(heartRateValue);
    }
}
