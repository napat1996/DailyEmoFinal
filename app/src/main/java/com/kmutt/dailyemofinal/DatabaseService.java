package com.kmutt.dailyemofinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kmutt.dailyemofinal.Model.Data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class DatabaseService extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference mRootRef, users;
    Data data = new Data();
    java.util.Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        java.util.Date now = calendar.getTime();
        String currentTime = now.toString();
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference("Users");
        try {
            updateHeartRateDataToDB();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            if(data.getDateOfSleep() == currentTime){
                updateSleepDataToDB();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void updateSleepDataToDB() throws IOException, ParseException {
        int heartRateValue = data.getHeartRateValue();
        String heartRateTime = data.getHeartRateTime();
        int sleepMinute = data.getMinutesAsleep();
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
