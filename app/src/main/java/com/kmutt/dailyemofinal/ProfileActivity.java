package com.kmutt.dailyemofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class ProfileActivity extends AppCompatActivity {
    private Button btnHome,btnProfile,btnResult,btnSuggesstion;

    private static final String API_PREFIX = "https://api.fitbit.com";
    private static final String URL_HEART_RATE = "/1/user/-/activities/heart/date/2018-09-27/1d/5min/time/00:00/23:59.json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer eeyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2VzdESDQiLCJhdWQiOiIyMkQ2UkYiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd251dCB3cHJvIHdzbGUgd3dlaSB3c29jIHdzZXQgd2FjdCB3bG9jIiwiZXhwIjoxNTM4MDc2OTI5LCJpYXQiOjE1MzgwNDgxMjl9.T3mio8RbEQ7R-tjE7dCDJt7RwsMHAL_G3ks_6UcJCOM";

    FirebaseDatabase database;
    DatabaseReference mRootRef;
    FitbitData data = new FitbitData();
    java.util.Calendar calendar = Calendar.getInstance();
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
                Intent s = new Intent(getApplicationContext(), SummaryActivity.class);
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

        (new Thread(new Runnable() {
            @Override
            public void run() {
                URLConnection connection = null;
                Log.d(TAG, "Debuggung: in Thread");
                try {
                    Log.d(TAG, "Debuggung: in Try");
                    connection = new URL(API_PREFIX + URL_HEART_RATE).openConnection();
                    connection.setRequestProperty(AUTHORIZATION,BEARER);
                    Log.d(TAG, "Debuggung: test");
//                    BufferedReader response = new BufferedReader(new InputStreamReader(
//                            connection.getInputStream()));
                    InputStream response = connection.getInputStream();

                    JSONParser jsonParser = new JSONParser();
                    JSONObject responseObject = (JSONObject)jsonParser.parse(
                            new InputStreamReader(response, "UTF-8"));
                    JSONObject activities = (JSONObject) responseObject.get("activities-heart-intraday");
                    JSONArray dataset = (JSONArray) activities.get("dataset");
                    Log.d(TAG, "Debuggung: in size" + dataset.size());
                    int i = 0;
                    while(i<dataset.size()-1){
                        Log.d(TAG, "Debuggung: in while");
                        JSONObject datasetObject = (JSONObject) dataset.get(i);
                    final String heartRateValue = (String) (datasetObject.get("value"));
                    final String heartRateTime = (datasetObject.get("time")) + "";
                        Log.d(TAG, "run: Time : " + heartRateTime + "Heart Rate: "+heartRateValue);

                        database = FirebaseDatabase.getInstance();

                        mRootRef = database.getReferenceFromUrl("https://dailyemo-194412.firebaseio.com/Users/tk");

                        DatabaseReference heartRateDate = mRootRef.child("DateTime").child("2018-09-27");
                        heartRateDate.child("HeartRate").child("Timestemp").child(heartRateTime).setValue(heartRateValue);
                        Toast.makeText(getApplicationContext(),   "Added"+heartRateTime, Toast.LENGTH_LONG).show();
                        Log.d(TAG, "updateHeartRatetoDB: "+date+ " Time : " + heartRateTime + " : " + heartRateValue);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        })).start();


    }
}
