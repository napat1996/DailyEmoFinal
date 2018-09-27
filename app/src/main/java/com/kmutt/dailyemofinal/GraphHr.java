package com.kmutt.dailyemofinal;

import android.graphics.Color;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class GraphHr extends AppCompatActivity {

    //    private static final String TAG = GraphHr.class.getSimpleName();
//    private RelativeLayout mainlayout;
//    private LineChart mchart;
//    private BarChart barChart;
//    DatabaseReference users, mRootRef;
//    FirebaseDatabase database;
//    private ArrayList<String> mUsernames  = new ArrayList<>();
//    private ListView mListView;
    private BarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_hr);
        mChart = (BarChart) findViewById(R.id.barchart_hr);
        setData(7);
        mChart.setMaxVisibleValueCount(70);

    }

    public void setData(int count) {
        ArrayList<BarEntry> yValues = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int val1 = (int) (Math.random() * count) + 50;
            int val2 = (int) (Math.random() * count) + 50;

            yValues.add(new BarEntry(i, new float[]{val1, val2}));
        }
        BarDataSet set1;

        set1 = new BarDataSet(yValues, "Beats of Heart rate");
        set1.setDrawIcons(false);
        set1.setColors(getColors());
        set1.setStackLabels(new String[]{"Higher", "Normal"});
//        set1.setColors(ColorTemplate.PASTEL_COLORS);


        BarData data = new BarData(set1);
        data.setValueFormatter(new MyValueFormatter());

        mChart.getAxisLeft().setAxisMaximum(150);

        mChart.setData(data);
        mChart.setFitBars(true);
        mChart.invalidate();
        mChart.getDescription().setEnabled(false);


    }

    public int[] getColors() {
        int stacksize = 2;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = ColorTemplate.PASTEL_COLORS[i];

        }
        return colors;
    }
}

//    Firebase = FirebseDatabase.getInstance.getReference
//    Firebase firebase = url.child(HeartRate);
//    Firebase.push().setvalue(value);
/////https://www.youtube.com/watch?v=a20EchSQgp




//        database = FirebaseDatabase.getInstance();
//        mRootRef = database.getReference();
//
//        final ArrayAdapter <String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsernames);
//        mListView.setAdapter(arrayAdapter);
//
////        getHRFromFirebase();
//
//
//        DatabaseService.mRootRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSanpshot, String s) {
//                String value = dataSanpshot.getValue(String.class);
//                mUsernames.add(value);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            public void onCancelled(DatabaseError error) {
//
//            }
//        });
//    }
//
//    private void getHRFromFireBase(Context context) {
//        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//        SharedPreferences preferences = context.getSharedPreferences("DailyEmoPref", 0);
//        String username = preferences.getString("username", "");
//
//        database = FirebaseDatabase.getInstance();
//
//        mRootRef = database.getReferenceFromUrl("https://dailyemo-194412.firebaseio.com/Users/"+username);
//
//        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                User heartRate = dataSnapshot.child("DateTime").child(date).child("HeartRate").getValue(User.class);
//                int heartRateValue =
//                Log.d(TAG, "onDataChange: Hea");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//}



//        mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);
//        // create line chart
//        mchart = new LineChart(this);
//        // add to main layout
//        mainlayout.addView(mchart);
//
//        //customize line chart
//        mchart.setDescription("");
//        mchart.setNoDataTextDescription("No data for the moment");
//
//        //enable value highlighting
//        mchart.set



//        barChart = (BarChart) findViewById(R.id.barchart_hr);
//        barChart.setDrawBarShadow(false);
//        barChart.setDrawValueAboveBar(true);
//        barChart.setMaxVisibleValueCount(50);
//        barChart.setPinchZoom(false);
////        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
//
//       // barChart.setDrawGridBackground(true);
//
//
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//
//        barEntries.add(new BarEntry(1, 40f));
//        barEntries.add(new BarEntry(2, 30f));
//        barEntries.add(new BarEntry(3, 20f));
//
//
//        BarDataSet barDataSet = new BarDataSet(barEntries, "Data Set1");
//       // barDataSet.setColor(Color.parseColor("#FFFFFF"));
//
//        BarData data  =  new BarData(barDataSet);
//        data.setBarWidth(0.9f);
//
//        barChart.setData(data) ;
//    }
//}
