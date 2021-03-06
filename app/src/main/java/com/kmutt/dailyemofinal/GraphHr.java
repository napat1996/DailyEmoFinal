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
import android.widget.TextView;


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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class GraphHr extends AppCompatActivity {

    private static final String TAG = GraphHr.class.getSimpleName();

    private LineChart mChart0,mChart1, mChart2,mChart3,mChart4, mChart5, mChart6,mChart7;

    DatabaseReference mRootRef, users;
    FirebaseDatabase database;
    Button btnHome, btnProfile, btnResult, btnSuggesstion;
    TextView txtDay1, txtDay2, txtDay3, txtDay4, txtDay5, txtDay6, txtDay7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_hr);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.text_date);
        textViewDate.setText("19 Nov 2018");


//        dateTimeRef.child("Heartrate").setValue(false);
        //mChart = (BarChart) findViewById(R.id.barchart_hr);


        txtDay1 = findViewById(R.id.day1);
        txtDay2 = findViewById(R.id.day2);
        txtDay3 = findViewById(R.id.day3);
        txtDay4 = findViewById(R.id.day4);
        txtDay5 = findViewById(R.id.day5);
        txtDay6 = findViewById(R.id.day6);
        txtDay7 = findViewById(R.id.day7);

        //****LINECHART*****
        mChart0 = findViewById(R.id.linechart_hr);
        mChart1 = findViewById(R.id.linechart_hr1);
        mChart2 = findViewById(R.id.linechart_hr2);
        mChart3 = findViewById(R.id.linechart_hr3);
        mChart4 = findViewById(R.id.linechart_hr4);
        mChart5 = findViewById(R.id.linechart_hr5);
        mChart6 = findViewById(R.id.linechart_hr6);
        mChart7 = findViewById(R.id.linechart_hr7);


        mChart0.setDragEnabled(true);
        mChart0.setScaleEnabled(false);
        mChart0.getDescription().setEnabled(false);
        mChart0.getLegend().setEnabled(false);


        mChart1.setDragEnabled(true);
        mChart1.setScaleEnabled(false);
        mChart1.getDescription().setEnabled(false);
        mChart1.getLegend().setEnabled(false);
        mChart1.getAxisLeft().setDrawGridLines(false);
        mChart1.getXAxis().setDrawGridLines(false);


        mChart2.setDragEnabled(true);
        mChart2.setScaleEnabled(false);
        mChart2.getDescription().setEnabled(false);
        mChart2.getLegend().setEnabled(false);
        mChart2.getAxisLeft().setDrawGridLines(false);
        mChart2.getXAxis().setDrawGridLines(false);

        mChart3.setDragEnabled(true);
        mChart3.setScaleEnabled(false);
        mChart3.getDescription().setEnabled(false);
        mChart3.getLegend().setEnabled(false);
        mChart3.getAxisLeft().setDrawGridLines(false);
        mChart3.getXAxis().setDrawGridLines(false);

        mChart4.setDragEnabled(true);
        mChart4.setScaleEnabled(false);
        mChart4.getDescription().setEnabled(false);
        mChart4.getLegend().setEnabled(false);
        mChart4.getAxisLeft().setDrawGridLines(false);
        mChart4.getXAxis().setDrawGridLines(false);

        mChart5.setDragEnabled(true);
        mChart5.setScaleEnabled(false);
        mChart5.getDescription().setEnabled(false);
        mChart5.getLegend().setEnabled(false);
        mChart5.getAxisLeft().setDrawGridLines(false);
        mChart5.getXAxis().setDrawGridLines(false);

        mChart6.setDragEnabled(true);
        mChart6.setScaleEnabled(false);
        mChart6.getDescription().setEnabled(false);
        mChart6.getLegend().setEnabled(false);
        mChart6.getAxisLeft().setDrawGridLines(false);
        mChart6.getXAxis().setDrawGridLines(false);

        mChart7.setDragEnabled(true);
        mChart7.setScaleEnabled(false);
        mChart7.getDescription().setEnabled(false);
        mChart7.getLegend().setEnabled(false);
        mChart7.getAxisLeft().setDrawGridLines(false);
        mChart7.getXAxis().setDrawGridLines(false);


        //// DELETE labels on the right slide ////
        mChart0.getAxisRight().setDrawLabels(false);
        mChart1.getAxisRight().setDrawLabels(false);
        mChart2.getAxisRight().setDrawLabels(false);
        mChart3.getAxisRight().setDrawLabels(false);
        mChart4.getAxisRight().setDrawLabels(false);
        mChart5.getAxisRight().setDrawLabels(false);
        mChart6.getAxisRight().setDrawLabels(false);
        mChart7.getAxisRight().setDrawLabels(false);

        LimitLine upper_limit = new LimitLine(100f, "Danger");
        upper_limit.setLineWidth(3f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
        upper_limit.setTextSize(10f);
        upper_limit.setTextColor(Color.rgb(177,11,8));
        upper_limit.setLineColor(Color.rgb(177,11,8));


//        LimitLine lower_limit = new LimitLine(4f, "Too low");
//        upper_limit.setLineWidth(4f);
//        upper_limit.enableDashedLine(10f, 10f, 0f);
//        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        upper_limit.setTextSize(15f);

        YAxis leftAxis0 = mChart0.getAxisLeft();
        leftAxis0.removeAllLimitLines();
        leftAxis0.addLimitLine(upper_limit);
//        leftAxis0.addLimitLine(lower_limit);
        leftAxis0.setAxisMaximum(160f);
        leftAxis0.setAxisMinimum(40f);
        leftAxis0.enableGridDashedLine(10f, 10f, 10);
        leftAxis0.setDrawLimitLinesBehindData(true);



        YAxis leftAxis1 = mChart1.getAxisLeft();
        leftAxis1.removeAllLimitLines();
        leftAxis1.addLimitLine(upper_limit);
//        leftAxis1.addLimitLine(lower_limit);
        leftAxis1.setAxisMaximum(160f);
        leftAxis1.setAxisMinimum(40f);
        leftAxis1.enableGridDashedLine(10f, 10f, 10);
        leftAxis1.setDrawLimitLinesBehindData(true);

        YAxis leftAxis2 = mChart2.getAxisLeft();
        leftAxis2.removeAllLimitLines();
        leftAxis2.addLimitLine(upper_limit);
//        leftAxis2.addLimitLine(lower_limit);
        leftAxis2.setAxisMaximum(160f);
        leftAxis2.setAxisMinimum(40f);
        leftAxis2.enableGridDashedLine(10f, 10f, 10);
        leftAxis2.setDrawLimitLinesBehindData(true);

        YAxis leftAxis3 = mChart3.getAxisLeft();
        leftAxis3.removeAllLimitLines();
        leftAxis3.addLimitLine(upper_limit);
//        leftAxis3.addLimitLine(lower_limit);
        leftAxis3.setAxisMaximum(160f);
        leftAxis3.setAxisMinimum(40f);
        leftAxis3.enableGridDashedLine(10f, 10f, 10);
        leftAxis3.setDrawLimitLinesBehindData(true);

        YAxis leftAxis4 = mChart4.getAxisLeft();
        leftAxis4.removeAllLimitLines();
        leftAxis4.addLimitLine(upper_limit);
//        leftAxis4.addLimitLine(lower_limit);
        leftAxis4.setAxisMaximum(160f);
        leftAxis4.setAxisMinimum(40f);
        leftAxis4.enableGridDashedLine(10f, 10f, 10);
        leftAxis4.setDrawLimitLinesBehindData(true);

        YAxis leftAxis5 = mChart5.getAxisLeft();
        leftAxis5.removeAllLimitLines();
        leftAxis5.addLimitLine(upper_limit);
//        leftAxis5.addLimitLine(lower_limit);
        leftAxis5.setAxisMaximum(200f);
        leftAxis5.setAxisMinimum(40f);
        leftAxis5.enableGridDashedLine(10f, 10f, 10);
        leftAxis5.setDrawLimitLinesBehindData(true);

        YAxis leftAxis6 = mChart6.getAxisLeft();
        leftAxis6.removeAllLimitLines();
        leftAxis6.addLimitLine(upper_limit);
//        leftAxis6.addLimitLine(lower_limit);
        leftAxis6.setAxisMaximum(160f);
        leftAxis6.setAxisMinimum(40f);
        leftAxis6.enableGridDashedLine(10f, 10f, 10);
        leftAxis6.setDrawLimitLinesBehindData(true);

        YAxis leftAxis7 = mChart7.getAxisLeft();
        leftAxis7.removeAllLimitLines();
        leftAxis7.addLimitLine(upper_limit);
//        leftAxis7.addLimitLine(lower_limit);
        leftAxis7.setAxisMaximum(160f);
        leftAxis7.setAxisMinimum(40f);
        leftAxis7.enableGridDashedLine(10f, 10f, 10);
        leftAxis7.setDrawLimitLinesBehindData(true);

        // ลดตัวอักษรขนาดของเวลา
        XAxis xAxis = mChart0.getXAxis();
        xAxis.setTextSize(6f);

        XAxis xAxis1 = mChart1.getXAxis();
        xAxis1.setTextSize(6f);

        XAxis xAxis2 = mChart2.getXAxis();
        xAxis2.setTextSize(6f);

        XAxis xAxis3 = mChart3.getXAxis();
        xAxis3.setTextSize(6f);

        XAxis xAxis4 = mChart4.getXAxis();
        xAxis4.setTextSize(6f);

        XAxis xAxis5 = mChart5.getXAxis();
        xAxis5.setTextSize(6f);

        XAxis xAxis6 = mChart6.getXAxis();
        xAxis6.setTextSize(6f);

        XAxis xAxis7 = mChart7.getXAxis();
        xAxis7.setTextSize(6f);



        //delete line on the right side
        mChart0.getAxisRight().setEnabled(false);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("DailyEmoPref", 0);
        String username = preferences.getString("username", "Tangkwa");

        String firebaseUrl = "https://dailyemo-194412.firebaseio.com/Users/" + username;
        Log.d(TAG, "onCreate: debugging firebaseurl " + firebaseUrl);
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReferenceFromUrl(firebaseUrl);
        DatabaseReference dateTimeRef = mRootRef.child("DateTime");

        ValueEventListener valEv = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                dataSnapshot


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateInstance = new Date();
                int day =19;
                dateInstance.setDate(day-32);
                String today = sdf.format(dateInstance);
//                String today =  year+"-"+month+"-"+day;
//                today = "2018-11-19";
//                Date d = new Date();
//                int day = d.getDay();
//                int month = d.getMonth();
//                int year = d.getYear();

                ArrayList<Entry> yValues0 = new ArrayList<>();
                int count = 0;
                ArrayList<String> xAxisFormat0 = new ArrayList<>();
                Log.d(TAG, "Debugging in mChart0 : "+today);
                //today
                DataSnapshot snapshot0 = dataSnapshot.child(today).child("HeartRate").child("Timestemp");
                for (DataSnapshot s : snapshot0.getChildren()) {
                    Log.d("debugging in TimeStamp", s.getKey());
                    String time = s.getKey();
                    xAxisFormat0.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

//                    Log.d(TAG, "Debugging : "+s.getValue());

                    if (count % 60 == 0) {
                        yValues0.add(new Entry(count, (Long)s.getValue() * 1f));
                    }
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
                set0.setValueTextSize(8f);
                set0.setValueTextColor(Color.rgb(36,71,143));
//                set0.setCircleColor(Color.rgb());

                ArrayList<ILineDataSet> dataSets0 = new ArrayList<>();
                dataSets0.add(set0);
                LineData data0 = new LineData(dataSets0);
                mChart0.setData(data0);
                mChart0.invalidate();

                XAxis xData0 = mChart0.getXAxis();
                xData0.setGranularity(10f); // minimum axis-step (interval) is 5
                xData0.setValueFormatter(formatter0);

                txtDay1.setText(today);

//                cal.set(2018, 10, 29);
//                cal.add(Calendar.DATE, -1);
//                day = cal.get(Calendar.DATE);
                dateInstance.setDate(day-1);
                String dateBeforeString = sdf.format(dateInstance);
//                String dateBeforeString = "2018-10-28";

//                ///////////// mChart1////////////
                ArrayList<Entry> yValues1 = new ArrayList<>();
                int count1 = 0;
                ArrayList<String> xAxisFormat1 = new ArrayList<>();
                DataSnapshot snapshot1 = dataSnapshot.child(dateBeforeString).child("HeartRate").child("Timestemp");
                Log.d(TAG, "Debugging in mChart1 : "+dateBeforeString);
                for (DataSnapshot s : snapshot1.getChildren()) {
                    Log.d("debugging", snapshot1.getKey());
                    String time = s.getKey();
                    xAxisFormat1.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

//                    Log.d(TAG, "onDataChange: "+s.getValue());

                    if (count1 % 60 == 0) {
                        yValues1.add(new Entry(count1, (Long)s.getValue() * 1f));
                    }
                    count1++;
                }

                final Object[] xAxis1 = xAxisFormat1.toArray();
                IAxisValueFormatter formatter1 = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return (String)xAxis1[(int)value];
                    }
                };

                LineDataSet set1 = new LineDataSet(yValues1, "Data Set 1");
//                set1.setFillAlpha(110);

                set1.setColor(Color.rgb(36,71,143));
                set1.setLineWidth(3f);
                set1.setValueTextSize(8f);
                set1.setValueTextColor(Color.rgb(161,134,116));

                ArrayList<ILineDataSet> dataSets1 = new ArrayList<>();
                dataSets1.add(set1);
                LineData data1 = new LineData(dataSets1);

                mChart1.setData(data1);
                mChart1.invalidate();

                XAxis xData1 = mChart1.getXAxis();
                xData1.setGranularity(10f); // minimum axis-step (interval) is 5
                xData1.setValueFormatter(formatter1);

                txtDay1.setText(dateBeforeString);

                dateInstance.setDate(day-2);
                dateBeforeString = sdf.format(dateInstance);
                ///////////// mChart2////////////
                ArrayList<Entry> yValues2 = new ArrayList<>();
                int count2 = 0;
                ArrayList<String> xAxisFormat2 = new ArrayList<>();
                DataSnapshot snapshot2 = dataSnapshot.child(dateBeforeString).child("HeartRate").child("Timestemp");
                for (DataSnapshot s : snapshot2.getChildren()) {
                    Log.d("debugging", snapshot2.getKey());
                    String time = s.getKey();
                    xAxisFormat2.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

//                    Log.d(TAG, "onDataChange: "+s.getValue());

                    if (count2 % 60 == 0) {
                        yValues2.add(new Entry(count2, (Long)s.getValue() * 1f));
                    }
                    count2++;
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
                set2.setValueTextSize(8f);
                set2.setValueTextColor(Color.rgb(161,134,116));

                ArrayList<ILineDataSet> dataSets2 = new ArrayList<>();
                dataSets2.add(set2);
                LineData data2 = new LineData(dataSets2);

                mChart2.setData(data2);
                mChart2.invalidate();

                XAxis xData2 = mChart2.getXAxis();
                xData2.setGranularity(10f); // minimum axis-step (interval) is 5
                xData2.setValueFormatter(formatter2);

                txtDay2.setText(dateBeforeString);

                dateInstance.setDate(day-3);
                dateBeforeString = sdf.format(dateInstance);

                ///////////// mChart3////////////
                ArrayList<Entry> yValues3 = new ArrayList<>();
                int count3 = 0;
                ArrayList<String> xAxisFormat3 = new ArrayList<>();
                DataSnapshot snapshot3 = dataSnapshot.child(dateBeforeString).child("HeartRate").child("Timestemp");
                for (DataSnapshot s : snapshot3.getChildren()) {
                    Log.d("debugging", snapshot3.getKey());
                    String time = s.getKey();
                    xAxisFormat3.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

//                    Log.d(TAG, "onDataChange: "+s.getValue());

                    if (count3 % 60 == 0) {
                        yValues3.add(new Entry(count3, (Long)s.getValue() * 1f));
                    }
                    count3++;
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


                set3.setLineWidth(3f);
                set3.setValueTextSize(8f);
                set3.setValueTextColor(Color.rgb(161,134,116));
                set3.setColor(Color.rgb(36,71,143));

                ArrayList<ILineDataSet> dataSets3 = new ArrayList<>();
                dataSets3.add(set3);
                LineData data3 = new LineData(dataSets3);

                mChart3.setData(data3);
                mChart3.invalidate();

                XAxis xData3 = mChart3.getXAxis();
                xData3.setGranularity(10f); // minimum axis-step (interval) is 5
                xData3.setValueFormatter(formatter3);

                txtDay3.setText(dateBeforeString);

                dateInstance.setDate(day-4);
                dateBeforeString = sdf.format(dateInstance);

                ///////////// mChart4////////////
                ArrayList<Entry> yValues4 = new ArrayList<>();
                int count4 = 0;
                ArrayList<String> xAxisFormat4 = new ArrayList<>();
                DataSnapshot snapshot4 = dataSnapshot.child(dateBeforeString).child("HeartRate").child("Timestemp");
                for (DataSnapshot s : snapshot4.getChildren()) {
                    Log.d("debugging", snapshot4.getKey());
                    String time = s.getKey();
                    xAxisFormat4.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

//                    Log.d(TAG, "onDataChange: "+s.getValue());

                    if (count4 % 60 == 0) {
                        yValues4.add(new Entry(count4, (Long)s.getValue() * 1f));
                    }
                    count4++;
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


                set4.setLineWidth(3f);
                set4.setValueTextSize(8f);
                set4.setValueTextColor(Color.rgb(161,134,116));
                set4.setColor(Color.rgb(36,71,143));

                ArrayList<ILineDataSet> dataSets4 = new ArrayList<>();
                dataSets4.add(set4);
                LineData data4 = new LineData(dataSets4);

                mChart4.setData(data4);
                mChart4.invalidate();

                XAxis xData4 = mChart4.getXAxis();
                xData4.setGranularity(10f); // minimum axis-step (interval) is 5
                xData4.setValueFormatter(formatter4);

                txtDay4.setText(dateBeforeString);

                dateInstance.setDate(day-5);
                dateBeforeString = sdf.format(dateInstance);

                    ///////////// mChart5////////////
                ArrayList<Entry> yValues5 = new ArrayList<>();
                int count5 = 0;
                ArrayList<String> xAxisFormat5 = new ArrayList<>();
                DataSnapshot snapshot5 = dataSnapshot.child(dateBeforeString).child("HeartRate").child("Timestemp");
                for (DataSnapshot s : snapshot5.getChildren()) {
                    Log.d("debugging", snapshot5.getKey());
                    String time = s.getKey();
                    xAxisFormat5.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

                    Log.d(TAG, "onDataChange: "+s.getValue());

                    if (count5 % 60 == 0) {
                        yValues5.add(new Entry(count5, (Long)s.getValue() * 1f));
                    }
                    count5++;
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


                set5.setLineWidth(3f);
                set5.setValueTextSize(8f);
                set5.setValueTextColor(Color.rgb(161,134,116));
                set5.setColor(Color.rgb(36,71,143));

                ArrayList<ILineDataSet> dataSets5 = new ArrayList<>();
                dataSets5.add(set5);
                LineData data5 = new LineData(dataSets5);

                mChart5.setData(data5);
                mChart5.invalidate();

                XAxis xData5 = mChart5.getXAxis();
                xData5.setGranularity(10f); // minimum axis-step (interval) is 5
                xData5.setValueFormatter(formatter5);

                txtDay5.setText(dateBeforeString);

                dateInstance.setDate(day-6);
                dateBeforeString = sdf.format(dateInstance);

                ///////////// mChart6////////////
                ArrayList<Entry> yValues6 = new ArrayList<>();
                int count6 = 0;
                ArrayList<String> xAxisFormat6 = new ArrayList<>();
                DataSnapshot snapshot6 = dataSnapshot.child(dateBeforeString).child("HeartRate").child("Timestemp");
                for (DataSnapshot s : snapshot6.getChildren()) {
                    Log.d("debugging", snapshot6.getKey());
                    String time = s.getKey();
                    xAxisFormat6.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

                    Log.d(TAG, "onDataChange: "+s.getValue());

                    if (count6 % 60 == 0) {
                        yValues6.add(new Entry(count6, (Long)s.getValue() * 1f));
                    }
                    count6++;
                }

                final Object[] xAxis6 = xAxisFormat6.toArray();
                IAxisValueFormatter formatter6 = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return (String)xAxis6[(int)value];
                    }
                };

                LineDataSet set6 = new LineDataSet(yValues6, "Data Set 6");
//                set1.setFillAlpha(110);


                set6.setLineWidth(3f);
                set6.setValueTextSize(8f);
                set6.setValueTextColor(Color.rgb(161,134,116));
                set6.setColor(Color.rgb(36,71,143));

                ArrayList<ILineDataSet> dataSets6 = new ArrayList<>();
                dataSets6.add(set6);
                LineData data6 = new LineData(dataSets6);

                mChart6.setData(data6);
                mChart6.invalidate();

                XAxis xData6 = mChart6.getXAxis();
                xData6.setGranularity(10f); // minimum axis-step (interval) is 5
                xData6.setValueFormatter(formatter6);

                txtDay6.setText(dateBeforeString);

                dateInstance.setDate(day-7);
                dateBeforeString = sdf.format(dateInstance);


                ///////////// mChart7////////////
                ArrayList<Entry> yValues7 = new ArrayList<>();
                int count7 = 0;
                ArrayList<String> xAxisFormat7 = new ArrayList<>();
                DataSnapshot snapshot7 = dataSnapshot.child(dateBeforeString).child("HeartRate").child("Timestemp");
                for (DataSnapshot s : snapshot7.getChildren()) {
                    Log.d("debugging", snapshot7.getKey());
                    String time = s.getKey();
                    xAxisFormat7.add(time);
//                    String hr = (String)snapshot.getValue();
//                    Log.d(TAG, "Debugging: "+hr);

                    Log.d(TAG, "onDataChange: "+s.getValue());

                    if (count7 % 60 == 0) {
                        yValues7.add(new Entry(count7, (Long)s.getValue() * 1f));
                    }
                    count7++;
                }

                final Object[] xAxis7 = xAxisFormat7.toArray();
                IAxisValueFormatter formatter7 = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return (String)xAxis7[(int)value];
                    }
                };

                LineDataSet set7 = new LineDataSet(yValues7, "Data Set 7");
//                set1.setFillAlpha(110);

                set7.setLineWidth(3f);
                set7.setValueTextSize(8f);
                set7.setValueTextColor(Color.rgb(161,134,116));
                set7.setColor(Color.rgb(36,71,143));

                ArrayList<ILineDataSet> dataSets7 = new ArrayList<>();
                dataSets7.add(set7);
                LineData data7 = new LineData(dataSets7);

                mChart7.setData(data7);
                mChart7.invalidate();

                XAxis xData7 = mChart7.getXAxis();
                xData7.setGranularity(10f); // minimum axis-step (interval) is 5
                xData7.setValueFormatter(formatter7);

                txtDay7.setText(dateBeforeString);

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

