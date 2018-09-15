package com.kmutt.dailyemofinal;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.security.auth.login.LoginException;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class DsleepFragment extends Fragment {

    private static final String API_PREFIX = "https://api.fitbit.com";
    View view;

    private MainActivity mainActivity = new MainActivity();

    public DsleepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_dsleep, null, false);

        sleepCheck();
        return view;
    }

    private void sleepCheck() {
        Thread urlConnectionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URLConnection connection = new URL(API_PREFIX.concat("/1.2/user/-/sleep/date/2018-09-13.json")).openConnection();
                    connection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2VEpaWEYiLCJhdWQiOiIyMkNaUE4iLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd3BybyB3bnV0IHdzbGUgd3dlaSB3c29jIHdzZXQgd2FjdCB3bG9jIiwiZXhwIjoxNTM2ODg2NzQ3LCJpYXQiOjE1MzY4NTc5NDd9.UGZ9x0pBbcTGcI2rCUdp1iVeS2MF0dGpy2pU8CHC4ko");
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
                    mainActivity.database.getReference();
                    JSONArray sleep = (JSONArray) responseObject.get("sleep");
                    JSONObject dateOfSleep = (JSONObject) sleep.get(Integer.parseInt("dateOfSleep"));

                    Log.e(TAG, "run: Date Of Sleep"+ dateOfSleep.toJSONString() );

//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.e(TAG, "run: ================"+heartRateTime+" " + heartRateValue );
//                            //txtHeartRate.setText(heartRateValue);
//                       }
//                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        urlConnectionThread.start();
    }

}
