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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class DatabaseService {
    FirebaseDatabase database;
    DatabaseReference mRootRef;
    FitbitData data = new FitbitData();
    Calendar calendar = Calendar.getInstance();
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    public void updateSleepDataToDB(Context context) throws IOException, ParseException {

        long sleepMinute = data.getMinutesAsleep();
        long rem = data.getRem();
        long deep = data.getDeep();
        long light = data.getlight();
        long wake = data.getWake();
        String dateOfSleep = data.getDateOfSleep();
        String startTime = data.getStartTimeOfSleep();
        String endTime = data.getEndTimeOfSleep();
        String stages = data.getSleepStage();

        SharedPreferences preferences = context.getSharedPreferences("DailyEmoPref", 0);
        String username = preferences.getString("username", "");
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReferenceFromUrl("https://dailyemo-194412.firebaseio.com/Users/"+username);

        DatabaseReference heartRateDate = mRootRef.child("DateTime").child(dateOfSleep);
        heartRateDate.child("Sleep").child("TotalMinute").setValue(sleepMinute);
        Log.d(TAG, "updateSleepDB: "+dateOfSleep+ " : " + sleepMinute);


    }


    public void updateHeartRatetoDB(Context context) throws IOException, ParseException {

        SharedPreferences preferences = context.getSharedPreferences("DailyEmoPref", 0);
        String username = preferences.getString("username", "");

        int heartRateValue = data.getHeartRateValue();
        String heartRateTime = data.getHeartRateTime();
        database = FirebaseDatabase.getInstance();

        mRootRef = database.getReferenceFromUrl("https://dailyemo-194412.firebaseio.com/Users/"+username);

        DatabaseReference heartRateDate = mRootRef.child("DateTime").child(date);
        heartRateDate.child("HeartRate").child("Timestemp").child(heartRateTime).setValue(heartRateValue);
        Log.d(TAG, "updateHeartRatetoDB: "+date+ " Time : " + heartRateTime + " : " + heartRateValue);
    }

    public void updateSteptoDB(Context context) throws IOException, ParseException {
        SharedPreferences preferences = context.getSharedPreferences("DailyEmoPref", 0);
        String username = preferences.getString("username", "");

        int stepValue = data.getStepsValue();
        String stepsDateTime = data.getStepsDateTime();
        database = FirebaseDatabase.getInstance();

        mRootRef = database.getReferenceFromUrl("https://dailyemo-194412.firebaseio.com/Users/"+username);

        DatabaseReference stepsDate = mRootRef.child("DateTime").child(stepsDateTime);
        stepsDate.child("Steps").setValue(stepValue);
        Log.d(TAG, "updateSteptoDB: "+stepsDateTime+" : "+stepValue);
    }

}
