package com.kmutt.dailyemofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
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

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GraphSleep extends AppCompatActivity {

    private BarChart mChart;
    HorizontalBarChart bChart;
    Button btnHome, btnProfile, btnResult, btnSuggesstion;
    DatabaseReference mRootRef, users;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_sleep);


        mChart = findViewById(R.id.barchart_sleep);
//        bChart = findViewById(R.id.barchart1) ;

//        setData2(7, 12);



        setData(7);
        mChart.setMaxVisibleValueCount(10);

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



    public void setData(int count) {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("DailyEmoPref", 0);
        String username = preferences.getString("username", "tk");

        String firebaseUrl = "https://dailyemo-194412.firebaseio.com/Users/" + username;
        Log.d("", "onCreate: debugging firebaseurl " + firebaseUrl);
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
                String today =  year+"-"+month+"-"+day;

                ArrayList<BarEntry> yValues = new ArrayList<>();

                for (int i = 1; i <= 7; i++) {
                    String dateBefore = today;
                    DataSnapshot snapshot = dataSnapshot.child(dateBefore).child("Sleep").child("TotalMinute");
//                    Integer dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                    Log.d("", "debugging: "+dateBefore);
                    long val1 = (long)snapshot.getValue();
                    Log.d("", "debugging: "+val1);

                    yValues.add(new BarEntry(i, new float[]{val1}));

                    cal.add(Calendar.DATE, -1);
                    day = cal.get(Calendar.DATE);
                    dateBefore = year+"-"+month+"-"+day;
                    today = dateBefore;
                }
                BarDataSet set1;

                set1 = new BarDataSet(yValues, "Hours of sleep");
                set1.setDrawIcons(false);
                set1.setColors(ColorTemplate.PASTEL_COLORS[0]);
                set1.setStackLabels(new String[]{"Higher", "Normal"});
                set1.setColors(ColorTemplate.PASTEL_COLORS);


                BarData data = new BarData(set1);
                data.setValueFormatter(new MyValueFormatter());

                mChart.getAxisLeft().setAxisMaximum(1000);

                mChart.setData(data);
                mChart.setFitBars(true);
                mChart.invalidate();
                mChart.getDescription().setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        dateTimeRef.addValueEventListener(valEv);



    }

    public int[] getColors() {
        int stacksize = 1;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = ColorTemplate.PASTEL_COLORS[0];

        }
        return colors;
    }
}
