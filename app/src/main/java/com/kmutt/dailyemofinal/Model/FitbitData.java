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

public class FitbitData {
    private static final String API_PREFIX = "https://api.fitbit.com";
    private static final String URL_HEART_RATE = "/1/user/-/activities/heart/date/2018-09-20/1d/5min/time/00:00/23:59.json";
    private static final String URL_SLEEP = "/1.2/user/-/sleep/date/2018-09-19.json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2VzdESDQiLCJhdWQiOiIyMkQ2UkYiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd251dCB3cHJvIHdzbGUgd3dlaSB3c29jIHdzZXQgd2FjdCB3bG9jIiwiZXhwIjoxNTM3ODIxODY1LCJpYXQiOjE1Mzc3OTMwNjV9.Wnq67gAcZ1HRz3EmALoEqpx163oIw3LgQa-MjfzBdiM";

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
        final String heartRateValue = (datasetObject.get("value")) + "";
        final String heartRateTime = (datasetObject.get("time")) + "";
        Log.d(TAG, "run: ================ Heart Rate: "+heartRateValue);
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
        final String heartRateValue = (datasetObject.get("value")) + "";
        final String heartRateTime = (datasetObject.get("time")) + "";
        Log.d(TAG, "run: ================ Time: " + heartRateTime );
        return heartRateTime;
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

    public String  getSleepStage() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONObject summary = (JSONObject) responseObject.get("summary");
        JSONObject stages = (JSONObject) summary.get("stages");
        String sleepStage = stages.toJSONString();
        Log.d(TAG, "===========================Sleep level : " +sleepStage);

        return sleepStage;
    }

    public Long getRem() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONObject summary = (JSONObject) responseObject.get("summary");
        JSONObject stages = (JSONObject) summary.get("stages");
        //String heartRateValue = datasetObject.toJSONString();
        long rem = (Long) (stages.get("rem"));

        Log.d(TAG, "run: Date Of Sleep REM : "+ rem );
        return rem;
    }

    public Long  getDeep() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONObject summary = (JSONObject) responseObject.get("summary");
        JSONObject stages = (JSONObject) summary.get("stages");
        //String heartRateValue = datasetObject.toJSONString();
        final long deep = (Long)(stages.get("deep"));

        Log.d(TAG, "run: Date Of Sleep Deep : "+ deep );
        return deep;
    }
    public Long  getlight() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONObject summary = (JSONObject) responseObject.get("summary");
        JSONObject stages = (JSONObject) summary.get("stages");
        final long light = (Long)(stages.get("light"));
        Log.d(TAG, "===========================Sleep level :  LIGHT : "+light);

        return light;
    }

    public Long  getWake() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat(URL_SLEEP)).openConnection();
        connection.setRequestProperty(AUTHORIZATION,BEARER);
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(new InputStreamReader(response, "UTF-8"));
        JSONObject summary = (JSONObject) responseObject.get("summary");
        JSONObject stages = (JSONObject) summary.get("stages");
        final long wake = (Long)(stages.get("wake"));
        Log.d(TAG, "===========================Sleep level : AWAKE : "+ wake);

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