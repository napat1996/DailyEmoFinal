package com.kmutt.dailyemofinal.Model;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

import static android.content.ContentValues.TAG;

public class Data extends AppCompatActivity{
    private static final String API_PREFIX = "https://api.fitbit.com";
    private static final String URL_HEART_RATE = "/1/user/-/activities/heart/date/today/1d/5minute/time/00:00/23:59.json";
    private static final String URL_SLEEP = "/1.2/user/-/sleep/date/2018-09-15.json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2VzdESDQiLCJhdWQiOiIyMkQ2UkYiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd3BybyB3bnV0IHdzbGUgd3dlaSB3c29jIHdzZXQgd2FjdCB3bG9jIiwiZXhwIjoxNTM3MTI3MzI0LCJpYXQiOjE1MzcwOTg1MjR9.5LNz5OLV9Jjk52udWuB9PNjXQspE6gDQwcy2CnLUUAY";

    FirebaseDatabase database;
    DatabaseReference mRootRef, users;
//    private void heartrateCheck() {
//        Thread urlConnectionThread = new Thread(new Runnable() {
////            mRootRef.addListenerForSingleValueEvent(new ValueEventListener()
//
//
//            @Override
//            public void run() {
//                try {
//                    URLConnection connection = new URL(API_PREFIX.concat("/1/user/-/activities/heart/date/2018-09-13/1d/5min/time/00:00/23:59.json")).openConnection();
//                    connection.setRequestProperty("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2VEpaWEYiLCJhdWQiOiIyMkNaUE4iLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd3BybyB3bnV0IHdzbGUgd3dlaSB3c29jIHdzZXQgd2FjdCB3bG9jIiwiZXhwIjoxNTM2ODg2NzQ3LCJpYXQiOjE1MzY4NTc5NDd9.UGZ9x0pBbcTGcI2rCUdp1iVeS2MF0dGpy2pU8CHC4ko");
//                    InputStream response = connection.getInputStream();
//                    JSONParser jsonParser = new JSONParser();
//                    JSONObject responseObject = (JSONObject)jsonParser.parse(
//                            new InputStreamReader(response, "UTF-8"));
//                    JSONObject activities = (JSONObject) responseObject.get("activities-heart-intraday");
//                    JSONArray dataset = (JSONArray) activities.get("dataset");
//                    JSONObject datasetObject = (JSONObject) dataset.get(dataset.size() - 1);
////                    String heartRateValue = datasetObject.toJSONString();
////                    Log.e(TAG, "===========================run: "+ heartRateValue);
//                    final String heartRateValue = (datasetObject.get("value")) + "";
//                    final String heartRateTime = (datasetObject.get("time")) + "";
//                    Log.e(TAG, "run: ================ Heart Rate: "+heartRateValue+" Time: " + heartRateTime );
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        urlConnectionThread.start();
//    }

    public Integer getHeartRateValue() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_HEART_RATE)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject)jsonParser.parse(
                new InputStreamReader(response, "UTF-8"));
        JSONObject activities = (JSONObject) responseObject.get("activities-heart-intraday");
        JSONArray dataset = (JSONArray) activities.get("dataset");
        JSONObject datasetObject = (JSONObject) dataset.get(dataset.size() - 1);
//                    String heartRateValue = datasetObject.toJSONString();
//                    Log.e(TAG, "===========================run: "+ heartRateValue);
        final String heartRateValue = (datasetObject.get("value")) + "";
        final String heartRateTime = (datasetObject.get("time")) + "";
        Log.e(TAG, "run: ================ Heart Rate: "+heartRateValue);
        return Integer.parseInt(""+datasetObject.get("value"));
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
//                    String heartRateValue = datasetObject.toJSONString();
//                    Log.e(TAG, "===========================run: "+ heartRateValue);
        final String heartRateValue = (datasetObject.get("value")) + "";
        final String heartRateTime = (datasetObject.get("time")) + "";
        Log.e(TAG, "run: ================ Time: " + heartRateTime );
        return heartRateTime;
    }

    public int getMinutesAsleep() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONObject summary = (JSONObject) responseObject.get("summary");
        int minuteAsleep = (Integer)summary.get("totalMinutesAsleep");
        return minuteAsleep;
    }

    public String  getSleepStage() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONObject summary = (JSONObject) responseObject.get("summary");
        JSONObject stages = (JSONObject) summary.get("stages");
        String sleepStage = stages.toJSONString();
        Log.e(TAG, "===========================Sleep level : REM : " +sleepStage);

        return sleepStage;
    }

    public String  getRem() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONObject summary = (JSONObject) responseObject.get("summary");
        JSONObject stages = (JSONObject) summary.get("stages");
        //String heartRateValue = datasetObject.toJSONString();
        String rem = (stages.get("rem")) + "";
        final String deep = (stages.get("deep")) + "";
        final String light = (stages.get("light")) + "";
        final String wake = (stages.get("wake")) + "";
        Log.e(TAG, "===========================Sleep level : REM : " + rem + " DEEP : "+deep+" LIGHT : "+light+" AWAKE : "+ wake);
        JSONArray sleep = (JSONArray) responseObject.get("sleep");

        Log.e(TAG, "run: Date Of Sleep REM : "+ rem );
        return rem;
    }

    public String  getDeep() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONObject summary = (JSONObject) responseObject.get("summary");
        JSONObject stages = (JSONObject) summary.get("stages");
        //String heartRateValue = datasetObject.toJSONString();
        String rem = (stages.get("rem")) + "";
        final String deep = (stages.get("deep")) + "";
        final String light = (stages.get("light")) + "";
        final String wake = (stages.get("wake")) + "";
        Log.e(TAG, "===========================Sleep level : REM : " + rem + " DEEP : "+deep+" LIGHT : "+light+" AWAKE : "+ wake);
        JSONArray sleep = (JSONArray) responseObject.get("sleep");

        Log.e(TAG, "run: Date Of Sleep Deep : "+ deep );
        return deep;
    }
    public String  getlight() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONObject summary = (JSONObject) responseObject.get("summary");
        JSONObject stages = (JSONObject) summary.get("stages");
        final String light = (stages.get("light")) + "";
        Log.e(TAG, "===========================Sleep level :  LIGHT : "+light);

        return light;
    }

    public String  getWake() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONObject summary = (JSONObject) responseObject.get("summary");
        JSONObject stages = (JSONObject) summary.get("stages");
        final String wake = (stages.get("wake")) + "";
        Log.e(TAG, "===========================Sleep level : REM : AWAKE : "+ wake);

        return wake;
    }
    public String  getDateOfSleep() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONArray sleep = (JSONArray) responseObject.get("sleep");
        JSONObject sleepObject = (JSONObject) sleep.get(0);
        JSONObject dateOfSleep = (JSONObject) sleepObject.get("dateOfSleep") ;
        String dateStr = dateOfSleep.toJSONString();
        System.out.println(dateStr);

        Log.e(TAG, "run: Date Of Sleep Deep : "+ dateStr );
        return dateStr;
    }

    public String  getEndTimeOfSleep() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONArray sleep = (JSONArray) responseObject.get("sleep");
        JSONObject sleepObject = (JSONObject) sleep.get(0);
        JSONObject endTime = (JSONObject) sleepObject.get("endTime") ;
        String endTimeStr = endTime.toJSONString();
        System.out.println(endTimeStr);

        return endTimeStr;
    }
    public String  getStartTimeOfSleep() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONArray sleep = (JSONArray) responseObject.get("sleep");
        JSONObject sleepObject = (JSONObject) sleep.get(0);
        JSONObject startTime = (JSONObject) sleepObject.get("startTime") ;
        String startTimeStr = startTime.toJSONString();
        System.out.println(startTimeStr);

        return startTimeStr;
    }
}
