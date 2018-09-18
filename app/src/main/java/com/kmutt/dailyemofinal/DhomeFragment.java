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
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kmutt.dailyemofinal.Model.Data;

import org.json.simple.parser.ParseException;

import java.io.IOException;

import static android.content.ContentValues.TAG;


public class DhomeFragment extends Fragment {

    private TextView txtHeartRate, txtSleep;
    View view;
    private Button btnHeartRate, btnSleep, btnStep, btnMap;
    private static final String API_PREFIX = "https://api.fitbit.com";



    public DhomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_dhome, container, false);

        (new Thread(new Runnable() {
            @Override
            public void run() {
                Data data = new Data();

                try {
                    final int heartRate = data.getHeartRateValue();
                    final long sleepMinute = data.getMinutesAsleep();




                    Log.e(TAG, "onCreateView: sleep : "+ sleepMinute );

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DatabaseService db = new DatabaseService();
                            btnHeartRate = getActivity().findViewById(R.id.buttom_hr);
                            txtHeartRate = getActivity().findViewById(R.id.heart_rate);
                            txtHeartRate.setText(heartRate + "");
//                            try {
//                                db.updateHeartRateDataToDB();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }

                            btnSleep = getActivity().findViewById(R.id.buttom_sleep);
                            txtSleep = getActivity().findViewById(R.id.text_sleep);
                            txtSleep.setText(sleepMinute + "");

                            btnStep = getActivity().findViewById(R.id.buttom_step);

                            btnMap = getActivity().findViewById(R.id.buttom_map2);

                            btnHeartRate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    System.out.println("go to heart rate graph page");
                                    Intent myIntent = new Intent(getActivity(), HomelinkHr.class);
                                    startActivity(myIntent);

                                }
                            });

                            btnSleep.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    System.out.println("go to heart rate graph page");
                                    Intent myIntent = new Intent(getActivity(), HomelinkSleep.class);
                                    startActivity(myIntent);

                                }
                            });

                            btnStep.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    System.out.println("go to heart rate graph page");
                                    Intent myIntent = new Intent(getActivity(), HomelinkStep.class);
                                    startActivity(myIntent);

                                }
                            });

                            btnMap.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    System.out.println("go to heart rate graph page");
                                    Intent myIntent = new Intent(getActivity(), HomelinkMap.class);
                                    startActivity(myIntent);

                                }
                            });
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        })).start();

//        MainActivity mainActivity = (MainActivity) getActivity();
//        return inflater.inflate(R.layout.fragment_dhome, container, false);
        return view;
    }


}
