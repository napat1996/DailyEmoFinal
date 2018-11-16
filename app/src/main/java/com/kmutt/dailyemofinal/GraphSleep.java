package com.kmutt.dailyemofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.Tag;
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
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
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
        String username = preferences.getString("username", "");

        String firebaseUrl = "https://dailyemo-194412.firebaseio.com/Users/" + username;
        Log.d("", "onCreate: debugging firebaseurl " + firebaseUrl);
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReferenceFromUrl(firebaseUrl);
        DatabaseReference dateTimeRef = mRootRef.child("DateTime");

        ValueEventListener valEv = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateInstance = new Date();

                ArrayList<BarEntry> yValues = new ArrayList<>();
                String[] date = new String[7];

                for (int i = 0; i <= 6; i++) {
                    dateInstance.setDate(15 - i);
                    String dateBefore = sdf.format(dateInstance);
                    date[i] = dateBefore;

                    Log.d("Debugging dateBefore", dateBefore);

                    DataSnapshot snapshot = dataSnapshot.child(dateBefore).child("Sleep").child("TotalMinute");
//                    Integer dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                    long val1 = 0;
                    if(snapshot.getValue()!=null) {
                        val1 = (long)snapshot.getValue();
                    }
                    Log.d("", "debugging val: "+val1);

                    yValues.add(new BarEntry(i, new float[]{val1}));
                }
                BarDataSet set1;

                XAxis xAxis = mChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(date));
                xAxis.setLabelRotationAngle(-50);
                xAxis.setTextSize(7f);
                mChart.animateXY(1000,1000);

                set1 = new BarDataSet(yValues, "Hours of sleep");
                set1.setDrawIcons(false);
                set1.setColors(ColorTemplate.PASTEL_COLORS[0]);
                set1.setStackLabels(new String[]{"Higher", "Normal"});
                set1.setColors(ColorTemplate.PASTEL_COLORS);



                BarData data = new BarData(set1);
                data.setValueFormatter(new MyValueFormatter());

                mChart.getAxisLeft().setAxisMaximum(1000f);

                mChart.setData(data);
                mChart.setFitBars(true);

                mChart.setDragEnabled(true);
                mChart.setScaleEnabled(false);
                mChart.getDescription().setEnabled(false);
//                mChart.getLegend().setEnabled(false);
                mChart.invalidate();
//                mChart.getHighlightByTouchPoint(5,5);
                mChart.getAxisRight().setDrawLabels(false); //ลบคำด้านขวามือ
                mChart.getAxisRight().setDrawGridLines(false);// ลบเส้นขวามือ

                LimitLine upper_limit = new LimitLine(550f, "higher");
                upper_limit.setLineWidth(3f);
                upper_limit.enableDashedLine(10f, 10f, 0f);
                upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
                upper_limit.setTextSize(10f);
                upper_limit.setTextColor(Color.rgb(177,11,8));
                upper_limit.setLineColor(Color.rgb(177,11,8));

                LimitLine lower_limit = new LimitLine(200f, "Less");
                lower_limit.setLineWidth(3f);
                lower_limit.enableDashedLine(10f, 10f, 0f);
                lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
                lower_limit.setTextSize(10f);
                lower_limit.setTextColor(Color.rgb(177,11,8));
                lower_limit.setLineColor(Color.rgb(177,11,8));


                YAxis leftAxis0 = mChart.getAxisLeft();
                leftAxis0.removeAllLimitLines();
                leftAxis0.addLimitLine(upper_limit);
                leftAxis0.addLimitLine(lower_limit);
//        leftAxis0.addLimitLine(lower_limit);;
//                leftAxis0.enableGridDashedLine(10f, 10f, 10);
                leftAxis0.setDrawLimitLinesBehindData(true);

//
//                mChart.getAxisLeft().setDrawGridLines(false);
//                mChart.getXAxis().setDrawGridLines(false); // แนวนอน
//









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
