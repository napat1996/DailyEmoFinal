package com.kmutt.dailyemofinal.Model;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kmutt.dailyemofinal.R;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FitbitData {
    private static final String API_PREFIX = "https://api.fitbit.com";
    private static final String URL_HEART_RATE = "/1/user/-/activities/heart/date/2018-11-15/1d/1min/time/00:00/23:59.json";
    private static final String URL_SLEEP = "/1.2/user/-/sleep/date/2018-11-01.json";
    private static final String URL_STEPS = "/1/user/-/activities/steps/date/2018-11-01/1d.json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMkQ0QkYiLCJzdWIiOiI2WFc3MzMiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd3BybyB3bnV0IHdzbGUgd3dlaSB3c29jIHdzZXQgd2FjdCB3bG9jIiwiZXhwIjoxNTQ1NTU5NDA4LCJpYXQiOjE1NDU1MzA2MDh9.cT0pecgXTGpghMuuXygitsBvuCgUytyZFU-VXAFawnY";
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());


    String firebaseUrl = "https://dailyemo-194412.firebaseio.com/Users/Tangkwa" ;
    DatabaseReference mRootRef = database.getReferenceFromUrl(firebaseUrl);
    DatabaseReference dateTimeRef = mRootRef.child("DateTime");

    public long getHeartRateValue() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_HEART_RATE)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject)jsonParser.parse(
                new InputStreamReader(response, "UTF-8"));
        JSONObject activities = (JSONObject) responseObject.get("activities-heart-intraday");
        JSONArray dataset = (JSONArray) activities.get("dataset");
        int i = 0;
//        while(i<dataset.size()-1){
//            JSONObject datasetObject = (JSONObject) dataset.get(i);
//        final String heartRateValue = (datasetObject.get("value")) + "";
//        final String heartRateTime = (datasetObject.get("time")) + "";
//        }

//        ValueEventListener valEv = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:00");
//                String currentTime = sdf.format(new Date());
//                Log.d(TAG, "Debugging: String "+currentTime);
//                ;
//                DataSnapshot snapshot = dataSnapshot.child("2018-11-15").child("HeartRate").child("Timestemp");
//
//                for (DataSnapshot s : snapshot.getChildren()) {
//                    String time = s.getKey();
////                                                Log.d(TAG, currentTime);
////                                                Log.d(TAG, time);
//                    if(time.equals( currentTime)){
//                        final long heartRate = (long)s.getValue();
//                        Log.d(TAG, "heartrate " + heartRate);
//                        Log.e(TAG, "debugging: HeartRate = " + heartRate);
//
//                    }
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//        dateTimeRef.addValueEventListener(valEv);


        Log.d(TAG, "getHeartRateValue: dataset size" + dataset.size());
        JSONObject datasetObject = (JSONObject) dataset.get(dataset.size() - 1);
        final long heartRateValue = (Long) (datasetObject.get("value"));
        Log.d(TAG, "run: ================ Heart Rate: "+heartRateValue);
        return heartRateValue;
    }

    public String getHeartRateTime() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_HEART_RATE)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject)jsonParser.parse(
                new InputStreamReader(response, "UTF-8"));
        JSONObject activities = (JSONObject) responseObject.get("activities-heart-intraday");
        JSONArray dataset = (JSONArray) activities.get("dataset");
        JSONObject datasetObject = (JSONObject) dataset.get(dataset.size() - 1);
        final String heartRateTime = (datasetObject.get("time")) + "";
        Log.d(TAG, "run: ================ Time: " + heartRateTime );
        return heartRateTime;
    }

    public void upAllHeartRateTimeToDB() throws IOException, ParseException {
        int more = 0,less =0;
        URLConnection connection = new URL(API_PREFIX.concat(URL_HEART_RATE)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject)jsonParser.parse(
                new InputStreamReader(response, "UTF-8"));
        JSONObject activities = (JSONObject) responseObject.get("activities-heart-intraday");
        JSONArray dataset = (JSONArray) activities.get("dataset");
        Log.d(TAG, "Debuggung: in size" + dataset.size());
        int i = 0;
        int lv1 = 0, lv2 = 0, lv3 = 0, lv0 = 0;
        int high = 0, low = 0;
        while(i<dataset.size()-1){
            Log.d(TAG, "Debuggung: in while");
            JSONObject datasetObject = (JSONObject) dataset.get(i);
            final Long heartRateValue = (Long) (datasetObject.get("value"));
            final String heartRateTime = (datasetObject.get("time")) + "";
            Log.d(TAG, "run: Time : " + heartRateTime + "Heart Rate: "+heartRateValue);

            database = FirebaseDatabase.getInstance();

            mRootRef = database.getReferenceFromUrl("https://dailyemo-194412.firebaseio.com/Users/Tangkwa");

            DatabaseReference heartRateDate = mRootRef.child("DateTime").child(date);
            heartRateDate.child("HeartRate").child("Timestemp").child(heartRateTime).setValue(heartRateValue);

//            if (heartRateValue >= 74 && heartRateValue < 82) {
//                Map<String, Object> stressLevel = new HashMap<>();
//                stressLevel.put("level", 1);
//                stressLevel.put("time", heartRateTime);
//                 heartRateDate.child("StressLevel").push().setValue(stressLevel);
//
//                 lv1++;
//                 heartRateDate.child("StressLevel").child("Level1").setValue(lv1);
//                 low++;
//                 heartRateDate.child("HeartRate").child("Low").setValue(low);
//            }
//            else if (heartRateValue >= 82 && heartRateValue < 100) {
//                Map<String, Object> stressLevel = new HashMap<>();
//                stressLevel.put("level", 2);
//                stressLevel.put("time", heartRateTime);
//                heartRateDate.child("StressLevel").push().setValue(stressLevel);
//                lv2++;
//                heartRateDate.child("StressLevel").child("Level2").setValue(lv2);
//                high++;
//                heartRateDate.child("HeartRate").child("High").setValue(low);
//            }
//                else if(heartRateValue > 100){
//                Map<String, Object> stressLevel = new HashMap<>();
//                stressLevel.put("level", 3);
//                stressLevel.put("time", heartRateTime);
//
//                heartRateDate.child("StressLevel").push().setValue(stressLevel);
//
//                lv3++;
//                heartRateDate.child("StressLevel").child("Level3").setValue(lv3);
//                high++;
//                heartRateDate.child("HeartRate").child("High").setValue(low);
//            }
//            else{
//                Map<String, Object> stressLevel = new HashMap<>();
//                stressLevel.put("level", 0);
//                stressLevel.put("time", heartRateTime);
//
//                heartRateDate.child("StressLevel").push().setValue(stressLevel);
//
//                lv0++;
//                heartRateDate.child("StressLevel").child("Level0").setValue(lv0);
//                low++;
//                heartRateDate.child("HeartRate").child("Low").setValue(low);
//            }

            if(heartRateValue>100){
                high++;
                heartRateDate.child("HeartRate").child("Low").setValue(high);
            }
            else {
                low++;
                heartRateDate.child("HeartRate").child("Low").setValue(low);
            }
            Log.d(TAG, "updateHeartRatetoDB: "+date+ " Time : " + heartRateTime + " : " + heartRateValue);
            i++;
        }

    }



    public long getMinutesAsleep() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONObject summary = (JSONObject) responseObject.get("summary");
        long minuteAsleep = (Long)summary.get("totalMinutesAsleep");
        Log.d(TAG, "getMinutesAsleep: "+ minuteAsleep );
        return minuteAsleep;
    }

//    public String  getSleepStage() throws IOException, ParseException {
//        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
//        connection.setRequestProperty(AUTHORIZATION,BEARER);
//        InputStream response = connection.getInputStream();
//        JSONParser jsonParser = new JSONParser();
//        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
//        JSONObject summary = (JSONObject) responseObject.get("summary");
//        JSONObject stages = (JSONObject) summary.get("stages");
//        String sleepStage = stages.toJSONString();
//        Log.d(TAG, "===========================Sleep level : " +sleepStage);
//
//        return sleepStage;
//    }
//
//    public Long getRem() throws IOException, ParseException {
//        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
//        connection.setRequestProperty(AUTHORIZATION,BEARER);
//        InputStream response = connection.getInputStream();
//        JSONParser jsonParser = new JSONParser();
//        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
//        JSONObject summary = (JSONObject) responseObject.get("summary");
//        JSONObject stages = (JSONObject) summary.get("stages");
//        //String heartRateValue = datasetObject.toJSONString();
//        long rem = (Long) (stages.get("rem"));
//
//        Log.d(TAG, "run: Date Of Sleep REM : "+ rem );
//        return rem;
//    }
//
//    public Long  getDeep() throws IOException, ParseException {
//        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
//        connection.setRequestProperty(AUTHORIZATION,BEARER);
//        InputStream response = connection.getInputStream();
//        JSONParser jsonParser = new JSONParser();
//        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
//        JSONObject summary = (JSONObject) responseObject.get("summary");
//        JSONObject stages = (JSONObject) summary.get("stages");
//        //String heartRateValue = datasetObject.toJSONString();
//        final long deep = (Long)(stages.get("deep"));
//
//        Log.d(TAG, "run: Date Of Sleep Deep : "+ deep );
//        return deep;
//    }
//    public Long  getlight() throws IOException, ParseException {
//        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
//        connection.setRequestProperty(AUTHORIZATION,BEARER);
//        InputStream response = connection.getInputStream();
//        JSONParser jsonParser = new JSONParser();
//        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
//        JSONObject summary = (JSONObject) responseObject.get("summary");
//        JSONObject stages = (JSONObject) summary.get("stages");
//        final long light = (Long)(stages.get("light"));
//        Log.d(TAG, "===========================Sleep level :  LIGHT : "+light);
//
//        return light;
//    }
//
//    public Long  getWake() throws IOException, ParseException {
//        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
//        connection.setRequestProperty(AUTHORIZATION,BEARER);
//        InputStream response = connection.getInputStream();
//        JSONParser jsonParser = new JSONParser();
//        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
//        JSONObject summary = (JSONObject) responseObject.get("summary");
//        JSONObject stages = (JSONObject) summary.get("stages");
//        final long wake = (Long)(stages.get("wake"));
//        Log.d(TAG, "===========================Sleep level : AWAKE : "+ wake);
//
//        return wake;
//    }
//    public String  getDateOfSleep() throws IOException, ParseException {
//        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
//        connection.setRequestProperty(AUTHORIZATION,BEARER);
//        InputStream response = connection.getInputStream();
//        JSONParser jsonParser = new JSONParser();
//        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
//        JSONArray sleep = (JSONArray) responseObject.get("sleep");
//        JSONObject sleepObject = (JSONObject) sleep.get(0);
//        String dateOfSleep = (String) sleepObject.get("dateOfSleep") ;
//        System.out.println(dateOfSleep);
//
//        Log.e(TAG, "run: Date Of Sleep Deep : "+ dateOfSleep );
//        return dateOfSleep;
//    }

//    public String  getEndTimeOfSleep() throws IOException, ParseException {
//        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
//        connection.setRequestProperty(AUTHORIZATION,BEARER);
//        InputStream response = connection.getInputStream();
//        JSONParser jsonParser = new JSONParser();
//        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
//        JSONArray sleep = (JSONArray) responseObject.get("sleep");
//        JSONObject sleepObject = (JSONObject) sleep.get(0);
//        String endTime = (String) sleepObject.get("endTime") ;
//        System.out.println(endTime);
//
//        return endTime;
//    }
//    public String  getStartTimeOfSleep() throws IOException, ParseException {
//        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
//        connection.setRequestProperty(AUTHORIZATION,BEARER);
//        InputStream response = connection.getInputStream();
//        JSONParser jsonParser = new JSONParser();
//        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
//        JSONArray sleep = (JSONArray) responseObject.get("sleep");
//        JSONObject sleepObject = (JSONObject) sleep.get(0);
//        String startTime = (String) sleepObject.get("startTime") ;
//        System.out.println(startTime);
//
//        return startTime;
//    }

    public long getStepsValue() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_STEPS)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONArray activities = (JSONArray) responseObject.get("activities-steps");
        JSONObject activitiesObject = (JSONObject)activities.get(0);
        long value = Long.parseLong((String)activitiesObject.get("value"));
        Log.d(TAG, "getStepsValue: "+value);
        return value;

    }

}
