package com.kmutt.dailyemofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kmutt.dailyemofinal.Model.FitbitData;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class CalendarActivity extends AppCompatActivity{
    private String TAG = CalendarActivity.class.getSimpleName();
    FitbitData data = new FitbitData();
    Button btnHome,btnProfile,btnResult,btnSuggesstion;
    private PieChart mChart,mChart1,mChart2,mChart3,mChart4,mChart5,mChart6,mChart7;

    DatabaseReference mRootRef, users;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_status);

        mChart = findViewById(R.id.pieChart);
        mChart1 = findViewById(R.id.pieChart1);
        mChart2 = findViewById(R.id.pieChart2);
        mChart3 = findViewById(R.id.pieChart3);
        mChart4 = findViewById(R.id.pieChart4);
        mChart5 = findViewById(R.id.pieChart5);
        mChart6 = findViewById(R.id.pieChart6);
        mChart7 = findViewById(R.id.pieChart7);



//        mChart.setBackgroundColor(Color.WHITE);
//        moveOffscreen();
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setDrawHoleEnabled(true);
        mChart.setCenterTextOffset(0, -20);
//        mChart.setMaxAngle(180);
//        mChart.setRotationAngle(180);

        mChart1.setUsePercentValues(true);
        mChart1.getDescription().setEnabled(false);
        mChart1.setDrawHoleEnabled(false);
        mChart1.setCenterTextOffset(0, -20);
//        mChart1.setMaxAngle(180);
//        mChart1.setRotationAngle(180);

        mChart2.setUsePercentValues(true);
        mChart2.getDescription().setEnabled(false);
        mChart2.setDrawHoleEnabled(true);
        mChart2.setCenterTextOffset(0, -20);
//        mChart2.setMaxAngle(180);
//        mChart2.setRotationAngle(180);

        mChart3.setUsePercentValues(true);
        mChart3.getDescription().setEnabled(false);
        mChart3.setDrawHoleEnabled(true);
        mChart3.setCenterTextOffset(0, -20);
//        mChart3.setMaxAngle(180);
//        mChart3.setRotationAngle(180);

        mChart4.setUsePercentValues(true);
        mChart4.getDescription().setEnabled(false);
        mChart4.setDrawHoleEnabled(true);
        mChart4.setCenterTextOffset(0, -20);
//        mChart4.setMaxAngle(180);
//        mChart4.setRotationAngle(180);

        mChart5.setUsePercentValues(true);
        mChart5.getDescription().setEnabled(false);
        mChart5.setDrawHoleEnabled(true);
        mChart5.setCenterTextOffset(0, -20);
//        mChart5.setMaxAngle(180);
//        mChart5.setRotationAngle(180);

        mChart6.setUsePercentValues(true);
        mChart6.getDescription().setEnabled(false);
        mChart6.setDrawHoleEnabled(true);
        mChart6.setCenterTextOffset(0, -20);
//        mChart6.setMaxAngle(180);
//        mChart6.setRotationAngle(180);

        mChart7.setUsePercentValues(true);
        mChart7.getDescription().setEnabled(false);
        mChart7.setDrawHoleEnabled(true);
        mChart7.setCenterTextOffset(0, -20);
//        mChart7.setMaxAngle(180);
//        mChart7.setRotationAngle(180);




        // คลิกสไลด์กางออกมา
        mChart.animateY(1000, Easing.EasingOption.EaseInCubic);
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setYOffset(5);
        mChart.setEntryLabelColor(Color.WHITE);//color level text
        mChart.setEntryLabelTextSize(12f);

        setData(4, 100);


        mChart1.animateY(1000, Easing.EasingOption.EaseInCubic);
        Legend a = mChart1.getLegend();
        a.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        a.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        a.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        a.setDrawInside(false);
        a.setYOffset(5);
        mChart1.setEntryLabelColor(Color.WHITE);//color level text
        mChart1.setEntryLabelTextSize(12f);
        setData1(4, 100);

        mChart2.animateY(1000, Easing.EasingOption.EaseInCubic);
        Legend b = mChart2.getLegend();
        b.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        b.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        b.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        b.setDrawInside(false);
        b.setYOffset(5);
        mChart2.setEntryLabelColor(Color.WHITE);//color level text
        mChart2.setEntryLabelTextSize(12f);
        setData2(4, 100);

        mChart3.animateY(1000, Easing.EasingOption.EaseInCubic);
        Legend c = mChart3.getLegend();
        c.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        c.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        c.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        c.setDrawInside(false);
        c.setYOffset(5);
        mChart3.setEntryLabelColor(Color.WHITE);//color level text
        mChart3.setEntryLabelTextSize(12f);
        setData3(4, 100);

        mChart4.animateY(1000, Easing.EasingOption.EaseInCubic);
        Legend d = mChart4.getLegend();
        d.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        d.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        d.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        d.setDrawInside(false);
        d.setYOffset(5);
        mChart4.setEntryLabelColor(Color.WHITE);//color level text
        mChart4.setEntryLabelTextSize(12f);
        setData4(4, 100);

        mChart5.animateY(1000, Easing.EasingOption.EaseInCubic);
        Legend e = mChart5.getLegend();
        e.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        e.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        e.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        e.setDrawInside(false);
        e.setYOffset(5);
        mChart5.setEntryLabelColor(Color.WHITE);//color level text
        mChart5.setEntryLabelTextSize(12f);
        setData5(4, 100);

        mChart6.animateY(1000, Easing.EasingOption.EaseInCubic);
        Legend f = mChart6.getLegend();
        f.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        f.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        f.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        f.setDrawInside(false);
        f.setYOffset(5);
        mChart6.setEntryLabelColor(Color.WHITE);//color level text
        mChart6.setEntryLabelTextSize(12f);
        setData6(4, 100);

        mChart7.animateY(1000, Easing.EasingOption.EaseInCubic);
        Legend g = mChart7.getLegend();
        g.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        g.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        g.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        g.setDrawInside(false);
        g.setYOffset(5);
        mChart7.setEntryLabelColor(Color.WHITE);//color level text
        mChart7.setEntryLabelTextSize(12f);
        setData7(4, 100);
        ////////nav bar
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
    }


    String[] stress = new String[]{"level1", "level2","level3","level4"};

    private void setData(int count, int range) {
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
                Date dateInstance = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateInstance);
                Integer day = cal.get(Calendar.DATE);
                Integer month = cal.get(Calendar.MONTH) + 1;
                Integer year = cal.get(Calendar.YEAR);
//                String today =  year+"-"+month+"-"+day;
                String today = "2018-10-29";
                ArrayList<PieEntry> values = new ArrayList<>();

                for (int i = 0; i < 4; i++) {

                    DataSnapshot snapshot = dataSnapshot.child(today).child("StressLevel");
                    float val =  (long) snapshot.child("level"+i).getValue()*1f;
                    values.add(new PieEntry(val, stress[i]));
                }
                PieDataSet dataSet = new PieDataSet(values, "Partner ");
                dataSet.setSelectionShift(5f);
                dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
                dataSet.setColors(ColorTemplate.PASTEL_COLORS);
                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(15f);
                data.setValueTextColor(Color.WHITE);

                mChart.setData(data);
                mChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dateTimeRef.addValueEventListener(valEv);
    }


    String[] stress1 = new String[]{"level1", "level2","level3","level4"};


    private void setData1(int count, int range) {

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
                Date dateInstance = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateInstance);
                cal.add(Calendar.DATE,-1);
                Integer day = cal.get(Calendar.DATE);
                Integer month = cal.get(Calendar.MONTH) + 1;
                Integer year = cal.get(Calendar.YEAR);
//                String today = year + "-" + month + "-" + day;
                String today = "2018-10-28";
                ArrayList<PieEntry> values = new ArrayList<>();

                for (int i = 0; i < 4; i++) {

//                    DataSnapshot snapshot = dataSnapshot.child(today).child("StressLevel");
//                    int val =  (int) snapshot.child("level"+i).getValue();
//                    values.add(new PieEntry(val, stress1[i]));

                        float val = (float) ((Math.random() * 100) + 100 / 5);
                        values.add(new PieEntry(val, stress7[i]));

                }

                PieDataSet dataSet = new PieDataSet(values, "Partner ");
                dataSet.setSelectionShift(5f);
                dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
                dataSet.setColors(ColorTemplate.PASTEL_COLORS);
                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(15f);
                data.setValueTextColor(Color.WHITE);

                mChart1.setData(data);
                mChart1.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dateTimeRef.addValueEventListener(valEv);

    }

    String[] stress2 = new String[]{"level1", "level2","level3","level4"};

    private void setData2(int count, int range) {
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
                Date dateInstance = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateInstance);
                cal.add(Calendar.DATE,-2);
                Integer day = cal.get(Calendar.DATE);
                Integer month = cal.get(Calendar.MONTH) + 1;
                Integer year = cal.get(Calendar.YEAR);
//                String today = year + "-" + month + "-" + day;
                String today = "2018-10-27";
                ArrayList<PieEntry> values = new ArrayList<>();

                for (int i = 0; i < 4; i++) {

                    DataSnapshot snapshot = dataSnapshot.child(today).child("StressLevel");
                    int val =  (int) snapshot.child("level"+i).getValue();
                    values.add(new PieEntry(val, stress2[i]));
                }
                PieDataSet dataSet = new PieDataSet(values, "Partner ");
                dataSet.setSelectionShift(5f);
                dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
                dataSet.setColors(ColorTemplate.PASTEL_COLORS);
                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(15f);
                data.setValueTextColor(Color.WHITE);

                mChart2.setData(data);
                mChart2.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dateTimeRef.addValueEventListener(valEv);

    }

    String[] stress3 = new String[]{"level1", "level2","level3","level4"};

    private void setData3(int count, int range) {
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
                Date dateInstance = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateInstance);
                cal.add(Calendar.DATE,-3);
                Integer day = cal.get(Calendar.DATE);
                Integer month = cal.get(Calendar.MONTH) + 1;
                Integer year = cal.get(Calendar.YEAR);
//                String today = year + "-" + month + "-" + day;
                String today = "2018-10-26";
                ArrayList<PieEntry> values = new ArrayList<>();

                for (int i = 0; i < 4; i++) {

                    DataSnapshot snapshot = dataSnapshot.child(today).child("StressLevel");
                    int val =  (int) snapshot.child("level"+i).getValue();
                    values.add(new PieEntry(val, stress3[i]));
                }
                PieDataSet dataSet = new PieDataSet(values, "Partner ");
                dataSet.setSelectionShift(5f);
                dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
                dataSet.setColors(ColorTemplate.PASTEL_COLORS);
                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(15f);
                data.setValueTextColor(Color.WHITE);

                mChart3.setData(data);
                mChart3.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dateTimeRef.addValueEventListener(valEv);
    }

    String[] stress4 = new String[]{"level1", "level2","level3","level4"};

    private void setData4(int count, int range) {
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
                Date dateInstance = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateInstance);
                cal.add(Calendar.DATE,-5);
                Integer day = cal.get(Calendar.DATE);
                Integer month = cal.get(Calendar.MONTH) + 1;
                Integer year = cal.get(Calendar.YEAR);
//                String today = year + "-" + month + "-" + day;
                String today = "2018-10-24";
                ArrayList<PieEntry> values = new ArrayList<>();

                for (int i = 0; i < 4; i++) {

                    DataSnapshot snapshot = dataSnapshot.child(today).child("StressLevel");
                    int val =  (int) snapshot.child("level"+i).getValue();
                    values.add(new PieEntry(val, stress4[i]));
                }
                PieDataSet dataSet = new PieDataSet(values, "Partner ");
                dataSet.setSelectionShift(5f);
                dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
                dataSet.setColors(ColorTemplate.PASTEL_COLORS);
                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(15f);
                data.setValueTextColor(Color.WHITE);

                mChart4.setData(data);
                mChart4.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dateTimeRef.addValueEventListener(valEv);
    }

    String[] stress5 = new String[]{"level1", "level2","level3","level4"};

    private void setData5(int count, int range) {
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
                Date dateInstance = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateInstance);
                cal.add(Calendar.DATE,-5);
                Integer day = cal.get(Calendar.DATE);
                Integer month = cal.get(Calendar.MONTH) + 1;
                Integer year = cal.get(Calendar.YEAR);
//                String today = year + "-" + month + "-" + day;
                String today = "2018-10-23";
                ArrayList<PieEntry> values = new ArrayList<>();

                for (int i = 0; i < 4; i++) {

                    DataSnapshot snapshot = dataSnapshot.child(today).child("StressLevel");
                    int val = (int) snapshot.child("level" + i).getValue();
                    values.add(new PieEntry(val, stress5[i]));
                }
                PieDataSet dataSet = new PieDataSet(values, "Partner ");
                dataSet.setSelectionShift(5f);
                dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
                dataSet.setColors(ColorTemplate.PASTEL_COLORS);
                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(15f);
                data.setValueTextColor(Color.WHITE);

                mChart5.setData(data);
                mChart5.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dateTimeRef.addValueEventListener(valEv);
    }

    String[] stress6 = new String[]{"level1", "level2","level3","level4"};

    private void setData6(int count, int range) {
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
                Date dateInstance = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateInstance);
                cal.add(Calendar.DATE,-6);
                Integer day = cal.get(Calendar.DATE);
                Integer month = cal.get(Calendar.MONTH) + 1;
                Integer year = cal.get(Calendar.YEAR);
//                String today = year + "-" + month + "-" + day;
                String today = "2018-10-22";
                ArrayList<PieEntry> values = new ArrayList<>();

                for (int i = 0; i < 4; i++) {

                    DataSnapshot snapshot = dataSnapshot.child(today).child("StressLevel");
                    int val =  (int) snapshot.child("level"+i).getValue();
                    values.add(new PieEntry(val, stress6[i]));
                }
                PieDataSet dataSet = new PieDataSet(values, "Partner ");
                dataSet.setSelectionShift(5f);
                dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
                dataSet.setColors(ColorTemplate.PASTEL_COLORS);
                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(15f);
                data.setValueTextColor(Color.WHITE);

                mChart1.setData(data);
                mChart1.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dateTimeRef.addValueEventListener(valEv);
    }


    String[] stress7 = new String[]{"level1", "level2","level3","level4"};

    private void setData7(int count, int range) {
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
                Date dateInstance = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateInstance);
                cal.add(Calendar.DATE,-7);
                Integer day = cal.get(Calendar.DATE);
                Integer month = cal.get(Calendar.MONTH) + 1;
                Integer year = cal.get(Calendar.YEAR);
//                String today = year + "-" + month + "-" + day;
                String today = "2018-10-21";

                ArrayList<PieEntry> values = new ArrayList<>();

                for (int i = 0; i < 4; i++) {


                    DataSnapshot snapshot = dataSnapshot.child(today).child("StressLevel");
                    int val = (int) snapshot.child("level" + i).getValue();
                    values.add(new PieEntry(val, stress7[i]));
                }
                PieDataSet dataSet = new PieDataSet(values, "Partner ");
                dataSet.setSelectionShift(5f);
                dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
                dataSet.setColors(ColorTemplate.PASTEL_COLORS);
                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(15f);
                data.setValueTextColor(Color.WHITE);

                mChart7.setData(data);
                mChart7.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dateTimeRef.addValueEventListener(valEv);
    }
}
