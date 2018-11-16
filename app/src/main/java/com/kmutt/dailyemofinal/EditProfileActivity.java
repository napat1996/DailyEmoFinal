package com.kmutt.dailyemofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kmutt.dailyemofinal.Model.FitbitData;
import com.kmutt.dailyemofinal.Model.User;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

public class EditProfileActivity extends AppCompatActivity {

    Button btnHome, btnProfile, btnResult, btnSuggesstion, btnSaveProfile;
    EditText inputUsername, inputPassword,inputConfPaswd, inputEmail, inputAge,inputWeight,inputHeight,inputSex,inputBD, inputPercent;
    FirebaseDatabase database;
    DatabaseReference mRootRef;
    String mUsername;

    FitbitData data = new FitbitData();
    java.util.Calendar calendar = Calendar.getInstance();
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Log.d(TAG, "debugging: in edit profile");
        //start nav bar
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

        //end nav bar

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("DailyEmoPref", 0);
        final String username = preferences.getString("username", "");

        String firebaseUrl = "https://dailyemo-194412.firebaseio.com/Users/" + username;
        Log.d("", "onCreate: debugging firebaseurl " + firebaseUrl);
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReferenceFromUrl(firebaseUrl);

        DatabaseReference dateTimeRef = mRootRef.child("DateTime");
        dateTimeRef.child("2018-11-09").removeValue();

        inputUsername = findViewById(R.id.edit_username);
        inputPassword = findViewById(R.id.edit_password);
       inputConfPaswd = findViewById(R.id.edit_confirm_password);
        inputEmail = findViewById(R.id.edit_email);
        inputAge = findViewById(R.id.edit_age);
        inputHeight = findViewById(R.id.edit_high);
        inputWeight = findViewById(R.id.edit_weight);
        inputSex = findViewById(R.id.edit_gender);
        inputBD = findViewById(R.id.edit_date);
        inputPercent = findViewById(R.id.edit_percenincrease);


        btnSaveProfile = findViewById(R.id.save_profile);
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dailyemo-194412.firebaseio.com/Users/"+username);
                final User user = new User(inputUsername.getText().toString(),
                        inputPassword.getText().toString(),
                        inputEmail.getText().toString(),
                        inputSex.getText().toString(),
                        inputHeight.getText().toString(),
                        inputAge.getText().toString(),
                        inputBD.getText().toString(),
                        inputWeight.getText().toString(),
                        inputPercent.toString()
                );
                mRootRef.child(user.getUsername()).setValue(user);
                mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(TAG, "debugging onDataChange: edit profile");

                            mRootRef.child(user.getUsername()).setValue(user);
                            Toast.makeText(EditProfileActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "debugging: edit profile successed");

                            mUsername = inputUsername.getText().toString();
                            SharedPreferences preferences = getApplicationContext().getSharedPreferences("DailyEmoPref", 0);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("username", mUsername);
                            editor.commit();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled: !!!!!!!!!!!!N O T   W O R K K K K!!!!!!!!" );
                    }
                });
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);

            }
        });


//        ///////////ADD DATA TO FIREBASE////////////
//         final String API_PREFIX = "https://api.fitbit.com";
//        final String URL_HEART_RATE = "/1/user/-/activities/heart/date/2018-11-09/1d/1min/time/00:00/23:59.json";
//        final String URL_SLEEP = "/1.2/user/-/sleep/date/2018-11-09.json";
//        final String AUTHORIZATION = "Authorization";
//        final String BEARER = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMkQ0QkYiLCJzdWIiOiI2WFc3MzMiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd3BybyB3bnV0IHdzbGUgd3dlaSB3c29jIHdhY3Qgd3NldCB3bG9jIiwiZXhwIjoxNTQyNDAxMDg1LCJpYXQiOjE1NDIzNzIyODV9.ELC7aLjORJmUD8inCoKRV-Bfg6a0zG6DmKr9yJxXWAo";
//
//        final FirebaseDatabase[] database = new FirebaseDatabase[1];
//        final DatabaseReference[] mRootRef = new DatabaseReference[1];
//        FitbitData data = new FitbitData();
//        java.util.Calendar calendar = Calendar.getInstance();
//        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//
//
//        Log.d(TAG, "onCreate: debugging firebaseurl "+firebaseUrl);
//        database[0] = FirebaseDatabase.getInstance();
//        mRootRef[0] = database[0].getReferenceFromUrl(firebaseUrl);
//        DatabaseReference dateTimeRef = mRootRef[0].child("DateTime");
//        dateTimeRef.child("2018-11-16").removeValue();
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
//                        database[0] = FirebaseDatabase.getInstance();
//
//                        mRootRef[0] = database[0].getReferenceFromUrl("https://dailyemo-194412.firebaseio.com/Users/Tangkwa");
//
//                        DatabaseReference heartRateDate = mRootRef[0].child("DateTime").child("2018-11-09");
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
//                    DatabaseReference sleepDate = mRootRef[0].child("DateTime").child("2018-11-09");
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
