package com.kmutt.dailyemofinal;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class DhomeFragment extends Fragment {

    private TextView txtHeartRate;
    View view;
    private static final String API_PREFIX = "https://api.fitbit.com";

    FirebaseDatabase database;
    DatabaseReference mRootRef, users;

    public DhomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_dhome, null, false);

        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference("Users");

        txtHeartRate = view.findViewById(R.id.heart_rate);
        txtHeartRate.setText("0");
        heartrateCheck();

//        MainActivity mainActivity = (MainActivity) getActivity();
//        return inflater.inflate(R.layout.fragment_dhome, container, false);
        return view;
    }


    private void heartrateCheck() {
        Thread urlConnectionThread = new Thread(new Runnable() {
//            mRootRef.addListenerForSingleValueEvent(new ValueEventListener()


            @Override
            public void run() {
                try {
                    URLConnection connection = new URL(API_PREFIX.concat("/1/user/-/activities/heart/date/2018-09-13/1d/5min/time/00:00/23:59.json")).openConnection();
                    connection.setRequestProperty("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2VEpaWEYiLCJhdWQiOiIyMkNaUE4iLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd3BybyB3bnV0IHdzbGUgd3dlaSB3c29jIHdzZXQgd2FjdCB3bG9jIiwiZXhwIjoxNTM2ODg2NzQ3LCJpYXQiOjE1MzY4NTc5NDd9.UGZ9x0pBbcTGcI2rCUdp1iVeS2MF0dGpy2pU8CHC4ko");
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "run: ================ Heart Rate: "+heartRateValue+" Time: " + heartRateTime );
                            txtHeartRate.setText(heartRateValue);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        urlConnectionThread.start();
    }

    public Integer getHeartRate() throws IOException, ParseException {
        URLConnection connection = new URL(API_PREFIX.concat("/1/user/-/activities/heart/date/2018-09-13/1d/5min/time/00:00/23:59.json")).openConnection();
        connection.setRequestProperty("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2VEpaWEYiLCJhdWQiOiIyMkNaUE4iLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd3BybyB3bnV0IHdzbGUgd3dlaSB3c29jIHdzZXQgd2FjdCB3bG9jIiwiZXhwIjoxNTM2ODg2NzQ3LCJpYXQiOjE1MzY4NTc5NDd9.UGZ9x0pBbcTGcI2rCUdp1iVeS2MF0dGpy2pU8CHC4ko");
        InputStream response = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject)jsonParser.parse(
                new InputStreamReader(response, "UTF-8"));
        JSONObject activities = (JSONObject) responseObject.get("activities-heart-intraday");
        JSONArray dataset = (JSONArray) activities.get("dataset");
        JSONObject datasetObject = (JSONObject) dataset.get(dataset.size() - 1);
//                    String heartRateValue = datasetObject.toJSONString();
//                    Log.e(TAG, "===========================run: "+ heartRateValue);
        return Integer.parseInt(""+datasetObject.get("value"));
    }

}
