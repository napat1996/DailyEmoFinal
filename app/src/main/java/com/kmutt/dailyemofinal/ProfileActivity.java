package com.kmutt.dailyemofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kmutt.dailyemofinal.Model.FitbitData;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ProfileActivity extends AppCompatActivity {
    private Button btnHome,btnProfile,btnResult,btnSuggesstion,btnEditProfile;
    TextView txtName, txtUsername, txtEmail, txtHeight, txtAge, txtWeight, txtSex, txtPassword;

    private static final String API_PREFIX = "https://api.fitbit.com";
    private static final String URL_HEART_RATE = "/1/user/-/activities/heart/date/2018-11-07/1d/5min/time/00:00/23:59.json";
    private static final String URL_SLEEP = "/1.2/user/-/sleep/date/2018-11-07.json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMkQ0QkYiLCJzdWIiOiI2WFc3MzMiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd251dCB3cHJvIHdzbGUgd3dlaSB3c29jIHdzZXQgd2FjdCB3bG9jIiwiZXhwIjoxNTQxNzU4OTQ1LCJpYXQiOjE1NDE3MzAxNDV9.yD9515M_qUIrOpqpTXJ21RuyijW6mDzZKvHuU8VBoZ8";

    FirebaseDatabase database;
    DatabaseReference mRootRef;
    FitbitData data = new FitbitData();
    java.util.Calendar calendar = Calendar.getInstance();
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("DailyEmoPref", 0);
        String username = preferences.getString("username", "tk");

        String firebaseUrl = "https://dailyemo-194412.firebaseio.com/Users/" + username;
        Log.d("", "onCreate: debugging firebaseurl " + firebaseUrl);
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReferenceFromUrl(firebaseUrl);

        txtAge = findViewById(R.id.textView_age1);
        txtEmail = findViewById(R.id.textView_email1);
        txtHeight = findViewById(R.id.textView_high1);
        txtSex = findViewById(R.id.textView_gender1);
        txtWeight = findViewById(R.id.textView_weight1);
        txtPassword = findViewById(R.id.textView_password1);
        txtUsername = findViewById(R.id.textView_username1);
        ValueEventListener valEv = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                txtUsername.setText((String)dataSnapshot.child("username").getValue());
                txtAge.setText(dataSnapshot.child("age").getValue().toString());
                txtPassword.setText("xxxxxxxxxx");
                txtEmail.setText((String)dataSnapshot.child("email").getValue());
                txtHeight.setText(dataSnapshot.child("height").getValue().toString());
                txtSex.setText((String)dataSnapshot.child("sex").getValue());
                txtWeight.setText(dataSnapshot.child("weight").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        mRootRef.addValueEventListener(valEv);


        btnHome = findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(s);
            }
        });

        btnProfile = findViewById(R.id.btn_profile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(s);
            }
        });

        btnResult = findViewById(R.id.btn_summary);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(s);
            }
        });

        btnSuggesstion = findViewById(R.id.btn_suggestion);
        btnSuggesstion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(), SuggestionBackgroundActivity.class);
                startActivity(s);
            }
        });

        btnEditProfile = findViewById(R.id.button_edit_profile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(s);
            }
        });

//
//
//        ///////////ADD DATA TO FIREBASE////////////
//        SharedPreferences preferences = getApplicationContext().getSharedPreferences("DailyEmoPref", 0);
//        String username = preferences.getString("username", "tk");
//
//        String firebaseUrl = "https://dailyemo-194412.firebaseio.com/Users/tk";
//        Log.d(TAG, "onCreate: debugging firebaseurl "+firebaseUrl);
//        database = FirebaseDatabase.getInstance();
//        mRootRef = database.getReferenceFromUrl(firebaseUrl);
//        DatabaseReference dateTimeRef = mRootRef.child("DateTime");
//        dateTimeRef.child("2018-11-07").removeValue();
//
//        (new Thread(new Runnable() {
//            @Override
//            public void run() {
//                URLConnection connection = null;
//                URLConnection connectionS = null;
//                Log.d(TAG, "Debuggung: in Thread");
//                try {
//                    Log.d(TAG, "Debuggung: in Try");
//                    connection = new URL(API_PREFIX + URL_HEART_RATE).openConnection();
//                    connection.setRequestProperty(AUTHORIZATION,BEARER);
//                    connectionS = new URL(API_PREFIX + URL_SLEEP).openConnection();
//                    connectionS.setRequestProperty(AUTHORIZATION,BEARER);
//                    Log.d(TAG, "Debuggung: test");
////                    BufferedReader response = new BufferedReader(new InputStreamReader(
////                            connection.getInputStream()));
//                    InputStream response = connection.getInputStream();
//                    InputStream responseS = connectionS.getInputStream();
//
//                    JSONParser jsonParser = new JSONParser();
//                    JSONObject responseObject = (JSONObject)jsonParser.parse(
//                            new InputStreamReader(response, "UTF-8"));
//                    JSONObject activities = (JSONObject) responseObject.get("activities-heart-intraday");
//                    JSONArray dataset = (JSONArray) activities.get("dataset");
//                    Log.d(TAG, "Debuggung: in size" + dataset.size());
//                    int i = 0;
//                    int lv1 = 0, lv2 = 0, lv3 = 0, lv0 = 0;
//                    int high = 0, low = 0;
//                    while(i<dataset.size()-1){
//                        Log.d(TAG, "Debuggung: in while");
//                        JSONObject datasetObject = (JSONObject) dataset.get(i);
//                        final Long heartRateValue = (Long) (datasetObject.get("value"));
//                        final String heartRateTime = (datasetObject.get("time")) + "";
//                        Log.d(TAG, "run: Time : " + heartRateTime + "Heart Rate: "+heartRateValue);
//
//                        database = FirebaseDatabase.getInstance();
//
//                        mRootRef = database.getReferenceFromUrl("https://dailyemo-194412.firebaseio.com/Users/tk");
//
//                        DatabaseReference heartRateDate = mRootRef.child("DateTime").child("2018-11-07");
//                        heartRateDate.child("HeartRate").child("Timestemp").child(heartRateTime).setValue(heartRateValue);
//
//                        if (heartRateValue >= 74 && heartRateValue < 82) {
////                            Map<String, Object> stressLevel = new HashMap<>();
//                            int stressLevel = 1;
////                            stressLevel.put("level", 1);
////                            stressLevel.put("time", heartRateTime);
//
//                            heartRateDate.child("Stress").child(heartRateTime).setValue(stressLevel);
//                            Log.d(TAG, "Debugging in Stress: "+heartRateTime+" :" +stressLevel);
//                            lv1++;
//                            heartRateDate.child("StressLevel").child("Level1").setValue(lv1);
//                            low++;
//                            heartRateDate.child("HeartRate").child("Low").setValue(low);
//                        }
//                        else if (heartRateValue >= 82 && heartRateValue < 100) {
//                            int stressLevel = 2;
////                            Map<String, Object> stressLevel = new HashMap<>();
////                            stressLevel.put("level", 2);
////                            stressLevel.put("time", heartRateTime);
//
//                            heartRateDate.child("Stress").child(heartRateTime).setValue(stressLevel);
//                            Log.d(TAG, "Debugging in Stress: "+heartRateTime+" :" +stressLevel);
//                            lv2++;
//                            heartRateDate.child("StressLevel").child("Level2").setValue(lv2);
//                            high++;
//                            heartRateDate.child("HeartRate").child("High").setValue(low);
//                        }
//                        else if(heartRateValue > 100){
//                            int stressLevel = 3;
////                            Map<String, Object> stressLevel = new HashMap<>();
////                            stressLevel.put("level", 3);
////                            stressLevel.put("time", heartRateTime);
//
//                            heartRateDate.child("Stress").child(heartRateTime).setValue(stressLevel);
//                            Log.d(TAG, "Debugging in Stress: "+heartRateTime+" :" +stressLevel);
//                            lv3++;
//                            heartRateDate.child("StressLevel").child("Level3").setValue(lv3);
//                            high++;
//                            heartRateDate.child("HeartRate").child("High").setValue(low);
//                        }
//                        else{
//                            int stressLevel = 0;
////                            Map<String, Object> stressLevel = new HashMap<>();
////                            stressLevel.put("level", 0);
////                            stressLevel.put("time", heartRateTime);
//
//                            heartRateDate.child("Stress").child(heartRateTime).setValue(stressLevel);
//                            Log.d(TAG, "Debugging in Stress: "+heartRateTime+" :" +stressLevel);
//                            lv0++;
//                            heartRateDate.child("StressLevel").child("Level0").setValue(lv0);
//                            low++;
//                            heartRateDate.child("HeartRate").child("Low").setValue(low);
//                        }
//
//                        Log.d(TAG, "updateHeartRatetoDB: "+date+ " Time : " + heartRateTime + " : " + heartRateValue);
//                        i++;
//                    }
//
//                    JSONObject responseObjectS = (JSONObject)jsonParser.parse(
//                            new InputStreamReader(responseS, "UTF-8"));
//                    JSONObject summary = (JSONObject) responseObjectS.get("summary");
//                    long minuteAsleep = (Long)summary.get("totalMinutesAsleep");
//                    Log.d(TAG, "getMinutesAsleep: "+ minuteAsleep );
//                    DatabaseReference sleepDate = mRootRef.child("DateTime").child("2018-11-07");
//                    sleepDate.child("Sleep").child("TotalMinute").setValue(minuteAsleep);
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//        })).start();
//        Toast.makeText(getApplicationContext(),   "Added to db", Toast.LENGTH_LONG).show();



    }
}
