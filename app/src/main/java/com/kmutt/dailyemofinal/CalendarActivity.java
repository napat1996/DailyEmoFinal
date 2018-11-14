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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
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

public class CalendarActivity extends AppCompatActivity {
    private String TAG = CalendarActivity.class.getSimpleName();
    FitbitData data = new FitbitData();
    Button btnHome, btnProfile, btnResult, btnSuggesstion;

    DatabaseReference mRootRef, users;
    FirebaseDatabase database;
    TextView txt_sDay1, txt_sDay2, txt_sDay3, txt_sDay4, txt_sDay5, txt_sDay6, txt_sDay7;
    private LineChart sChart0, sChart1, sChart2, sChart3, sChart4, sChart5, sChart6, sChart7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_status);


        txt_sDay1 = findViewById(R.id.day_s1);
        txt_sDay2 = findViewById(R.id.day_s2);
        txt_sDay3 = findViewById(R.id.day_s3);
        txt_sDay4 = findViewById(R.id.day_s4);
        txt_sDay5 = findViewById(R.id.day_s5);
        txt_sDay6 = findViewById(R.id.day_s6);
        txt_sDay7 = findViewById(R.id.day_s7);

        //****LINECHART*****
        sChart0 = findViewById(R.id.pieChart);
        sChart1 = findViewById(R.id.pieChart1);
        sChart2 = findViewById(R.id.pieChart2);
        sChart3 = findViewById(R.id.pieChart3);
        sChart4 = findViewById(R.id.pieChart4);
        sChart5 = findViewById(R.id.pieChart5);
        sChart6 = findViewById(R.id.pieChart6);
        sChart7 = findViewById(R.id.pieChart7);


        sChart0.setDragEnabled(true);
        sChart0.setScaleEnabled(false);

        sChart1.setDragEnabled(true);
        sChart1.setScaleEnabled(false);

        sChart2.setDragEnabled(true);
        sChart2.setScaleEnabled(false);

        sChart3.setDragEnabled(true);
        sChart3.setScaleEnabled(false);

        sChart4.setDragEnabled(true);
        sChart4.setScaleEnabled(false);

        sChart5.setDragEnabled(true);
        sChart5.setScaleEnabled(false);

        sChart6.setDragEnabled(true);
        sChart6.setScaleEnabled(false);

        sChart7.setDragEnabled(true);
        sChart7.setScaleEnabled(false);


        LimitLine upper_limit3 = new LimitLine(3f, "Level3");
        upper_limit3.setLineWidth(4f);
        upper_limit3.enableDashedLine(10f, 10f, 0f);
        upper_limit3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit3.setTextSize(15f);

        LimitLine upper_limit2 = new LimitLine(2f, "Level2");
        upper_limit3.setLineWidth(4f);
        upper_limit3.enableDashedLine(10f, 10f, 0f);
        upper_limit3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit3.setTextSize(15f);

        LimitLine upper_limit1 = new LimitLine(1f, "Level1");
        upper_limit3.setLineWidth(4f);
        upper_limit3.enableDashedLine(10f, 10f, 0f);
        upper_limit3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit3.setTextSize(15f);


        LimitLine lower_limit = new LimitLine(0f, "Normal");
        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit.setTextSize(15f);

        YAxis leftAxis0 = sChart0.getAxisLeft();
        leftAxis0.removeAllLimitLines();
        leftAxis0.addLimitLine(upper_limit3);
        leftAxis0.addLimitLine(upper_limit2);
        leftAxis0.addLimitLine(upper_limit1);
        leftAxis0.addLimitLine(lower_limit);
        leftAxis0.setAxisMaximum(4f);
        leftAxis0.setAxisMinimum(0f);
        leftAxis0.enableGridDashedLine(1f, 1f, 1);
        leftAxis0.setDrawLimitLinesBehindData(true);


        YAxis leftAxis1 = sChart1.getAxisLeft();
        leftAxis0.removeAllLimitLines();
        leftAxis0.addLimitLine(upper_limit3);
        leftAxis0.addLimitLine(upper_limit2);
        leftAxis0.addLimitLine(upper_limit1);
        leftAxis0.addLimitLine(lower_limit);
        leftAxis0.setAxisMaximum(4f);
        leftAxis0.setAxisMinimum(0f);
        leftAxis0.enableGridDashedLine(1f, 1f, 1);
        leftAxis1.setDrawLimitLinesBehindData(true);

        YAxis leftAxis2 = sChart2.getAxisLeft();
        leftAxis0.removeAllLimitLines();
        leftAxis0.addLimitLine(upper_limit3);
        leftAxis0.addLimitLine(upper_limit2);
        leftAxis0.addLimitLine(upper_limit1);
        leftAxis0.addLimitLine(lower_limit);
        leftAxis0.setAxisMaximum(4f);
        leftAxis0.setAxisMinimum(0f);
        leftAxis0.enableGridDashedLine(1f, 1f, 1);
        leftAxis2.setDrawLimitLinesBehindData(true);

        YAxis leftAxis3 = sChart3.getAxisLeft();
        leftAxis0.removeAllLimitLines();
        leftAxis0.addLimitLine(upper_limit3);
        leftAxis0.addLimitLine(upper_limit2);
        leftAxis0.addLimitLine(upper_limit1);
        leftAxis0.addLimitLine(lower_limit);
        leftAxis0.setAxisMaximum(4f);
        leftAxis0.setAxisMinimum(0f);
        leftAxis0.enableGridDashedLine(1f, 1f, 1);
        leftAxis3.setDrawLimitLinesBehindData(true);

        YAxis leftAxis4 = sChart4.getAxisLeft();
        leftAxis0.removeAllLimitLines();
        leftAxis0.addLimitLine(upper_limit3);
        leftAxis0.addLimitLine(upper_limit2);
        leftAxis0.addLimitLine(upper_limit1);
        leftAxis0.addLimitLine(lower_limit);
        leftAxis0.setAxisMaximum(5f);
        leftAxis0.setAxisMinimum(0f);
        leftAxis0.enableGridDashedLine(1f, 1f, 1);
        leftAxis4.setDrawLimitLinesBehindData(true);

        YAxis leftAxis5 = sChart5.getAxisLeft();
        leftAxis0.removeAllLimitLines();
        leftAxis0.addLimitLine(upper_limit3);
        leftAxis0.addLimitLine(upper_limit2);
        leftAxis0.addLimitLine(upper_limit1);
        leftAxis0.addLimitLine(lower_limit);
        leftAxis0.setAxisMaximum(5f);
        leftAxis0.setAxisMinimum(0f);
        leftAxis0.enableGridDashedLine(1f, 1f, 1);
        leftAxis5.setDrawLimitLinesBehindData(true);

        YAxis leftAxis6 = sChart6.getAxisLeft();
        leftAxis0.removeAllLimitLines();
        leftAxis0.addLimitLine(upper_limit3);
        leftAxis0.addLimitLine(upper_limit2);
        leftAxis0.addLimitLine(upper_limit1);
        leftAxis0.addLimitLine(lower_limit);
        leftAxis0.setAxisMaximum(5f);
        leftAxis0.setAxisMinimum(0f);
        leftAxis0.enableGridDashedLine(1f, 1f, 1);
        leftAxis6.setDrawLimitLinesBehindData(true);

        YAxis leftAxis7 = sChart7.getAxisLeft();
        leftAxis0.removeAllLimitLines();
        leftAxis0.addLimitLine(upper_limit3);
        leftAxis0.addLimitLine(upper_limit2);
        leftAxis0.addLimitLine(upper_limit1);
        leftAxis0.addLimitLine(lower_limit);
        leftAxis0.setAxisMaximum(5f);
        leftAxis0.setAxisMinimum(0f);
        leftAxis0.enableGridDashedLine(1f, 1f, 1);
        leftAxis7.setDrawLimitLinesBehindData(true);

        //delete line on the right side
        sChart0.getAxisRight().setEnabled(false);
        sChart1.getAxisRight().setEnabled(false);
        sChart2.getAxisRight().setEnabled(false);
        sChart3.getAxisRight().setEnabled(false);
        sChart4.getAxisRight().setEnabled(false);
        sChart5.getAxisRight().setEnabled(false);
        sChart6.getAxisRight().setEnabled(false);
        sChart7.getAxisRight().setEnabled(false);

        //delete line on the right side
        sChart0.getAxisRight().setEnabled(false);

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
                Date dateInstance = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateInstance);
                Integer day = cal.get(Calendar.DATE);
                Integer month = cal.get(Calendar.MONTH) + 1;
                Integer year = cal.get(Calendar.YEAR);
//                String today =  year+"-"+month+"-"+day;
                String today = "2018-11-07";
//                Date d = new Date();
//                int day = d.getDay();
//                int month = d.getMonth();
//                int year = d.getYear();
                ArrayList<Entry> yValues0 = new ArrayList<>();
                int count = 0;
                ArrayList<String> xAxisFormat0 = new ArrayList<>();
                Log.d(TAG, "Debugging in mChart0 : "+today);
                //today
                DataSnapshot snapshot0 = dataSnapshot.child(today).child("Stress");
                for (DataSnapshot s : snapshot0.getChildren()) {
                    Log.d("debugging in Stress", s.getKey());
                    String time = s.getKey();
                    xAxisFormat0.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

                    Log.d(TAG, "Debugging : StressLevel :"+s.getValue());

//                    if (count % 5 == 0) {

                        yValues0.add(new Entry(count, (Long)s.getValue() * 1f));
//                    }
                    count++;
                }

                final Object[] xAxis0 = xAxisFormat0.toArray();
                IAxisValueFormatter formatter0 = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return (String)xAxis0[(int)value];
                    }
                };

                LineDataSet set0 = new LineDataSet(yValues0, "Data Set 0");
//                set1.setFillAlpha(110);

                set0.setColor(Color.rgb(36,71,143));
                set0.setLineWidth(3f);
                set0.setValueTextSize(10f);
                set0.setValueTextColor(Color.rgb(255,255,255));

                ArrayList<ILineDataSet> dataSets0 = new ArrayList<>();
                dataSets0.add(set0);
                LineData data0 = new LineData(dataSets0);
                sChart0.setData(data0);
                sChart0.invalidate();

                XAxis xData0 = sChart0.getXAxis();
                xData0.setGranularity(5f); // minimum axis-step (interval) is 5
                xData0.setValueFormatter(formatter0);

                txt_sDay1.setText(today);

                cal.set(2018, 11, 07);
                cal.add(Calendar.DATE, -1);
                day = cal.get(Calendar.DATE);
//                String dateBeforeString = year+"-"+month+"-"+day;
                String dateBeforeString = "2018-11-07";
//                String dateBeforeString = year + "-" + month + "-" + day;


                ///////////// mChart1////////////
                ArrayList<Entry> yValues1 = new ArrayList<>();
                count = 0;
                ArrayList<String> xAxisFormat1 = new ArrayList<>();
                Log.d(TAG, "Debugging in mChart1 : "+dateBeforeString);
                //today
                DataSnapshot snapshot1 = dataSnapshot.child(dateBeforeString).child("Stress");
                for (DataSnapshot s : snapshot1.getChildren()) {
                    Log.d("debugging in Stress", s.getKey());
                    String time = s.getKey();
                    xAxisFormat1.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

                    Log.d(TAG, "Debugging : StressLevel :"+s.getValue());

                    if (count % 5 == 0) {

                    yValues1.add(new Entry(count, (Long)s.getValue() * 1f));
                    }
                    count++;
                }

                final Object[] xAxis1 = xAxisFormat1.toArray();
                IAxisValueFormatter formatter1 = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return (String)xAxis1[(int)value];
                    }
                };

                LineDataSet set1 = new LineDataSet(yValues1, "Data Set 0");
//                set1.setFillAlpha(110);

                set1.setColor(Color.rgb(36,71,143));
                set1.setLineWidth(3f);
                set1.setValueTextSize(10f);
                set1.setValueTextColor(Color.rgb(255,255,255));

                ArrayList<ILineDataSet> dataSets1 = new ArrayList<>();
                dataSets1.add(set1);
                LineData data1 = new LineData(dataSets1);
                sChart1.setData(data1);
                sChart1.invalidate();

                XAxis xData1 = sChart1.getXAxis();
                xData1.setGranularity(5f); // minimum axis-step (interval) is 5
                xData1.setValueFormatter(formatter1);

                txt_sDay1.setText(dateBeforeString);

                cal.add(Calendar.DATE, -1);
                day = cal.get(Calendar.DATE);
//                String dateBeforeString = year+"-"+month+"-"+day;
                dateBeforeString = "2018-11-06";
//                String dateBeforeString = year + "-" + month + "-" + day;

                ///////////// mChart2////////////
                ArrayList<Entry> yValues2 = new ArrayList<>();
                count = 0;
                ArrayList<String> xAxisFormat2 = new ArrayList<>();
                Log.d(TAG, "Debugging in sChart2 : "+dateBeforeString);
                //today
                DataSnapshot snapshot2 = dataSnapshot.child(dateBeforeString).child("Stress");
                for (DataSnapshot s : snapshot2.getChildren()) {
                    Log.d("debugging in Stress", s.getKey());
                    String time = s.getKey();
                    xAxisFormat2.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

                    Log.d(TAG, "Debugging : StressLevel :"+s.getValue());

                    if (count % 5 == 0) {

                        yValues2.add(new Entry(count, (Long) s.getValue() * 1f));
                    }
                    count++;
                }

                final Object[] xAxis2 = xAxisFormat2.toArray();
                IAxisValueFormatter formatter2 = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return (String)xAxis2[(int)value];
                    }
                };

                LineDataSet set2 = new LineDataSet(yValues2, "Data Set 2");
//                set1.setFillAlpha(110);

                set2.setColor(Color.rgb(36,71,143));
                set2.setLineWidth(3f);
                set2.setValueTextSize(10f);
                set2.setValueTextColor(Color.rgb(255,255,255));

                ArrayList<ILineDataSet> dataSets2 = new ArrayList<>();
                dataSets2.add(set2);
                LineData data2 = new LineData(dataSets2);
                sChart2.setData(data2);
                sChart2.invalidate();

                XAxis xData2 = sChart2.getXAxis();
                xData2.setGranularity(5f); // minimum axis-step (interval) is 5
                xData2.setValueFormatter(formatter2);

                txt_sDay2.setText(dateBeforeString);

                cal.add(Calendar.DATE, -1);
                day = cal.get(Calendar.DATE);
//                String dateBeforeString = year+"-"+month+"-"+day;
                dateBeforeString = "2018-11-05";
//                String dateBeforeString = year + "-" + month + "-" + day;

                ///////////// mChart3////////////
                ArrayList<Entry> yValues3 = new ArrayList<>();
                count = 0;
                ArrayList<String> xAxisFormat3 = new ArrayList<>();
                Log.d(TAG, "Debugging in msChart3 : "+dateBeforeString);
                //today
                DataSnapshot snapshot3 = dataSnapshot.child(dateBeforeString).child("Stress");
                for (DataSnapshot s : snapshot3.getChildren()) {
                    Log.d("debugging in Stress", s.getKey());
                    String time = s.getKey();
                    xAxisFormat3.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

                    Log.d(TAG, "Debugging : StressLevel :"+s.getValue());

                    if (count % 5 == 0) {

                    yValues3.add(new Entry(count, (Long)s.getValue() * 1f));
                    }
                    count++;
                }

                final Object[] xAxis3 = xAxisFormat3.toArray();
                IAxisValueFormatter formatter3 = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return (String)xAxis3[(int)value];
                    }
                };

                LineDataSet set3 = new LineDataSet(yValues3, "Data Set 3");
//                set1.setFillAlpha(110);

                set3.setColor(Color.rgb(36,71,143));
                set3.setLineWidth(3f);
                set3.setValueTextSize(10f);
                set3.setValueTextColor(Color.rgb(255,255,255));

                ArrayList<ILineDataSet> dataSets3 = new ArrayList<>();
                dataSets3.add(set3);
                LineData data3 = new LineData(dataSets3);
                sChart3.setData(data3);
                sChart3.invalidate();

                XAxis xData3 = sChart3.getXAxis();
                xData3.setGranularity(5f); // minimum axis-step (interval) is 5
                xData3.setValueFormatter(formatter3);

                txt_sDay3.setText(dateBeforeString);

                cal.add(Calendar.DATE, -1);
                day = cal.get(Calendar.DATE);
//                String dateBeforeString = year+"-"+month+"-"+day;
                dateBeforeString = "2018-11-04";
//                String dateBeforeString = year + "-" + month + "-" + day;

                ///////////// mChart4////////////
                ArrayList<Entry> yValues4 = new ArrayList<>();
                count = 0;
                ArrayList<String> xAxisFormat4 = new ArrayList<>();
                Log.d(TAG, "Debugging in sChart4 : "+dateBeforeString);
                //today
                DataSnapshot snapshot4 = dataSnapshot.child(dateBeforeString).child("Stress");
                for (DataSnapshot s : snapshot4.getChildren()) {
                    Log.d("debugging in Stress", s.getKey());
                    String time = s.getKey();
                    xAxisFormat4.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

                    Log.d(TAG, "Debugging : StressLevel :"+s.getValue());

                    if (count % 5 == 0) {

                    yValues4.add(new Entry(count, (Long)s.getValue() * 1f));
                    }
                    count++;
                }

                final Object[] xAxis4 = xAxisFormat4.toArray();
                IAxisValueFormatter formatter4 = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return (String)xAxis4[(int)value];
                    }
                };

                LineDataSet set4 = new LineDataSet(yValues4, "Data Set 4");
//                set1.setFillAlpha(110);

                set4.setColor(Color.rgb(36,71,143));
                set4.setLineWidth(3f);
                set4.setValueTextSize(10f);
                set4.setValueTextColor(Color.rgb(255,255,255));

                ArrayList<ILineDataSet> dataSets4 = new ArrayList<>();
                dataSets4.add(set4);
                LineData data4 = new LineData(dataSets4);
                sChart4.setData(data4);
                sChart4.invalidate();

                XAxis xData4 = sChart4.getXAxis();
                xData4.setGranularity(5f); // minimum axis-step (interval) is 5
                xData4.setValueFormatter(formatter2);

                txt_sDay4.setText(dateBeforeString);

                cal.add(Calendar.DATE, -1);
                day = cal.get(Calendar.DATE);
//                String dateBeforeString = year+"-"+month+"-"+day;
                dateBeforeString = "2018-11-03";
//                String dateBeforeString = year + "-" + month + "-" + day;

                ///////////// mChart5////////////
                ArrayList<Entry> yValues5 = new ArrayList<>();
                count = 0;
                ArrayList<String> xAxisFormat5 = new ArrayList<>();
                Log.d(TAG, "Debugging in sChart5 : "+dateBeforeString);
                //today
                DataSnapshot snapshot5 = dataSnapshot.child(dateBeforeString).child("Stress");
                for (DataSnapshot s : snapshot5.getChildren()) {
                    Log.d("debugging in Stress", s.getKey());
                    String time = s.getKey();
                    xAxisFormat5.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

                    Log.d(TAG, "Debugging : StressLevel :"+s.getValue());

                    if (count % 2 == 0) {

                        yValues5.add(new Entry(count, (Long) s.getValue() * 1f));
                    }
                        count++;

                }

                final Object[] xAxis5 = xAxisFormat5.toArray();
                IAxisValueFormatter formatter5 = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return (String)xAxis5[(int)value];
                    }
                };

                LineDataSet set5 = new LineDataSet(yValues5, "Data Set 5");
//                set1.setFillAlpha(110);

                set5.setColor(Color.rgb(36,71,143));
                set5.setLineWidth(3f);
                set5.setValueTextSize(10f);
                set5.setValueTextColor(Color.rgb(255,255,255));

                ArrayList<ILineDataSet> dataSets5 = new ArrayList<>();
                dataSets5.add(set5);
                LineData data5 = new LineData(dataSets5);
                sChart5.setData(data5);
                sChart5.invalidate();

                XAxis xData5 = sChart5.getXAxis();
                xData5.setGranularity(5f); // minimum axis-step (interval) is 5
                xData5.setValueFormatter(formatter5);

                txt_sDay5.setText(dateBeforeString);

                cal.set(2018, 11, 03);
                cal.add(Calendar.DATE, -1);
                day = cal.get(Calendar.DATE);
//                String dateBeforeString = year+"-"+month+"-"+day;
                dateBeforeString = "2018-11-02";
//                String dateBeforeString = year + "-" + month + "-" + day;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        // Add event listener for Datetime
        dateTimeRef.addValueEventListener(valEv);


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
