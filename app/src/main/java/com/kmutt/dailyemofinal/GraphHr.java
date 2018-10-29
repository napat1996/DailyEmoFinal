package com.kmutt.dailyemofinal;

import android.content.Intent;
import android.graphics.Color;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kmutt.dailyemofinal.Model.User;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class GraphHr extends AppCompatActivity {

    private static final String TAG = GraphHr.class.getSimpleName();

    private LineChart mChart,mChart1, mChart2;

    DatabaseReference mRootRef, users;
    FirebaseDatabase database;
    Button btnHome, btnProfile, btnResult, btnSuggesstion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_hr);


//        dateTimeRef.child("Heartrate").setValue(false);
        //mChart = (BarChart) findViewById(R.id.barchart_hr);


        //****LINECHART*****
        mChart = findViewById(R.id.linechart_hr);
//        mChart1 = findViewById(R.id.linechart_hr1);
//        mChart2 = findViewById(R.id.linechart_hr2);


        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

//        mChart1.setDragEnabled(true);
//        mChart1.setScaleEnabled(false);
//
//        mChart2.setDragEnabled(true);
//        mChart2.setScaleEnabled(false);


        LimitLine upper_limit = new LimitLine(65f, "Danger");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(15f);

        LimitLine lower_limit = new LimitLine(45f, "Too low");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        upper_limit.setTextSize(15f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(upper_limit);
        leftAxis.addLimitLine(lower_limit);
        leftAxis.setAxisMaximum(130f);
        leftAxis.setAxisMinimum(40f);
        leftAxis.enableGridDashedLine(10f, 10f, 10);
        leftAxis.setDrawLimitLinesBehindData(true);




        //delete line on the right side
        mChart.getAxisRight().setEnabled(false);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("DailyEmoPref", 0);
        String username = preferences.getString("username", "tk");

        String firebaseUrl = "https://dailyemo-194412.firebaseio.com/Users/" + username;
        Log.d(TAG, "onCreate: debugging firebaseurl " + firebaseUrl);
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReferenceFromUrl(firebaseUrl);
        DatabaseReference dateTimeRef = mRootRef.child("DateTime");

        ValueEventListener valEv = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                dataSnapshot

                Date d = new Date();
                int day = d.getDay();
                int month = d.getMonth();
                int year = d.getYear();

                ArrayList<Entry> yValues = new ArrayList<>();
                int count = 0;
                ArrayList<String> xAxisFormat = new ArrayList<>();
                DataSnapshot snapshot = dataSnapshot.child("2018-10-20").child("HeartRate").child("Timestemp");
                for (DataSnapshot s : snapshot.getChildren()) {
                    Log.d("debugging", snapshot.getKey());
                    String time = s.getKey();
                    xAxisFormat.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

                    Log.d(TAG, "onDataChange: "+s.getValue());

                    if (count % 15 == 0) {
                        yValues.add(new Entry(count, (Long)s.getValue() * 1f));
                    }
                    count++;
                }

                final Object[] xAxis = xAxisFormat.toArray();
                IAxisValueFormatter formatter = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return (String)xAxis[(int)value];
                    }
                };

                LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");
//                set1.setFillAlpha(110);

                set1.setColor(Color.BLUE);
                set1.setLineWidth(3f);
                set1.setValueTextSize(10f);
                set1.setValueTextColor(Color.GREEN);

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);
                LineData data = new LineData(dataSets);
                mChart.setData(data);
                mChart.invalidate();

                ///////////// mChart1////////////
                
//                mChart1.setData(data);
//                mChart1.invalidate();
//
//                mChart2.setData(data);
//                mChart2.invalidate();

                XAxis xData = mChart.getXAxis();
                xData.setGranularity(10f); // minimum axis-step (interval) is 5
                xData.setValueFormatter(formatter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        // Add event listener for Datetime
        dateTimeRef.addValueEventListener(valEv);

        //setData();
        // mChart.setMaxVisibleValueCount(70);

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



    }

}

