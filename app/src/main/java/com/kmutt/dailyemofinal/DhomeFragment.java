package com.kmutt.dailyemofinal;


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

import com.kmutt.dailyemofinal.Model.Data;

import org.json.simple.parser.ParseException;

import java.io.IOException;

import static android.content.ContentValues.TAG;


public class DhomeFragment extends Fragment {

    private TextView txtHeartRate, txtSleep, txtActivity, txtTraffic;
    View view;
    private Button btnHeartRate, btnSleep, btnStep, btnMap, btnEmo;



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
//                    final String activity = trackActivity.setActivity();
//                    Log.d(TAG, "run: Activity : "+activity);

                    DatabaseService db = new DatabaseService();

                    Log.e(TAG, "onCreateView: sleep : "+ sleepMinute );
                    try {
                        db.updateHeartRateDataToDB(getActivity().getApplicationContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            btnEmo = getActivity().findViewById(R.id.button_mood);

                            btnHeartRate = getActivity().findViewById(R.id.buttom_hr);
                            txtHeartRate = getActivity().findViewById(R.id.heart_rate);
                            txtHeartRate.setText(heartRate + "");


                            btnSleep = getActivity().findViewById(R.id.buttom_sleep);
                            txtSleep = getActivity().findViewById(R.id.text_sleep);
                            txtSleep.setText(sleepMinute + "");

                            btnStep = getActivity().findViewById(R.id.buttom_step);
//                            txtActivity = getActivity().findViewById(R.id.text_steps);
//                            txtActivity.setText(activity);

                            btnMap = getActivity().findViewById(R.id.buttom_map2);
//                            txtTraffic = getActivity().findViewById(R.id.buttom_map2);
//                            txtTraffic.setText(traffic+"");


                            btnEmo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    System.out.println("go to Mood graph page");
                                    Intent myIntent = new Intent(getActivity(), Calendar.class);
                                    startActivity(myIntent);

                                }
                            });

                            btnHeartRate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    System.out.println("go to heart rate graph page");
                                    Intent myIntent = new Intent(getActivity(), GraphHr.class);
                                    startActivity(myIntent);

                                }
                            });

                            btnSleep.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    System.out.println("go to sleep graph page");
                                    Intent myIntent = new Intent(getActivity(), GraphSleep.class);
                                    startActivity(myIntent);

                                }
                            });

                            btnStep.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    System.out.println("go to step graph page");
                                    Intent myIntent = new Intent(getActivity(), HomelinkStep.class);
                                    startActivity(myIntent);

                                }
                            });

                            btnMap.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    System.out.println("go to Traffic graph page");
                                    Intent myIntent = new Intent(getActivity(), GraphTaffic.class);
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
