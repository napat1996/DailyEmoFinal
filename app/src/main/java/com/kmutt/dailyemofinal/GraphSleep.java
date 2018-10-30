package com.kmutt.dailyemofinal;

import android.content.Intent;
import android.graphics.Color;
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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kmutt.dailyemofinal.Model.FitbitData;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class GraphSleep extends AppCompatActivity {

    private BarChart mChart;
    HorizontalBarChart bChart;
    Button btnHome, btnProfile, btnResult, btnSuggesstion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_sleep);


        mChart = findViewById(R.id.barchart_sleep);
        bChart = findViewById(R.id.barchart1) ;

        setData2(7, 10);
        bChart.setMaxVisibleValueCount(10);



        setData(7);
        mChart.setMaxVisibleValueCount(70);

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
        private void setData2 (int count, int range){

            ArrayList<BarEntry> yVals = new ArrayList<>();
            float barWidth = 10f ;
            float spaceForBar= 10f;

            for (int i = 0; i<count; i++){
                float val  = (float) (Math.random()*range);
                yVals.add(new BarEntry(i*spaceForBar, val));
            }
            BarDataSet set1 ;
            set1 = new BarDataSet(yVals, "Data1");
            BarData data = new BarData(set1);
            data.setBarWidth(barWidth);
            bChart.setData(data);

        }


    public void setData(int count) {
        ArrayList<BarEntry> yValues = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int val1 = (int) (Math.random() * count) + 3;

            yValues.add(new BarEntry(i, new float[]{val1}));
        }
        BarDataSet set1;

        set1 = new BarDataSet(yValues, "Hours of sleep");
        set1.setDrawIcons(false);
        set1.setColors(getColors());
        set1.setStackLabels(new String[]{"Higher", "Normal"});
        set1.setColors(ColorTemplate.PASTEL_COLORS);


        BarData data = new BarData(set1);
        data.setValueFormatter(new MyValueFormatter());

        mChart.getAxisLeft().setAxisMaximum(12);

        mChart.setData(data);
        mChart.setFitBars(true);
        mChart.invalidate();
        mChart.getDescription().setEnabled(false);


    }

    public int[] getColors() {
        int stacksize = 1;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = ColorTemplate.PASTEL_COLORS[i];

        }
        return colors;
    }
}

//    private PieChart mChart;
//    private String TAG = GraphSleep.class.getSimpleName();
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_graph_sleep);
//
//
//        mChart = (PieChart)findViewById(R.id.chart1);
//       // mChart.setBackgroundColor(Color.WHITE);
//        moveOffscreen();
//
//        mChart.setUsePercentValues(true);
//        mChart.getDescription().setEnabled(false);
//        mChart.setDrawHoleEnabled(true);
//
//        mChart.setMaxAngle(180);
//        mChart.setRotationAngle(180);
//        mChart.setCenterTextOffset(0, -20);
//
//        // คลิกสไลด์กางออกมา
//        mChart.animateY(1000, Easing.EasingOption.EaseInCubic);
//
//        Legend l = mChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setYOffset(5);
//
//
//        mChart.setEntryLabelColor(Color.WHITE);
//        mChart.setEntryLabelTextSize(12f);
//
//        try {
//            setData(4, 100);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//    String[] sleep = new String[] {"DEEP", "LIGHT", "REM", "WAKE"};
//
//
//    private void setData (int count, final int range) throws IOException, ParseException {
//
////        Log.d(TAG, "setData: Rang = "+ range);
////        for (int i = 0 ; i<count; i ++ ){
////            float val = (float)((Math.random()*range)+ range/5);
////            Log.e(TAG, "setData: Val = "+ val );
////            values.add(new PieEntry(val, sleep[i]));
////        }
//
//        (new Thread(new Runnable() {
//            @Override
//            public void run() {
//                FitbitData sData = new FitbitData();
//                ArrayList<PieEntry> values = new ArrayList<>();
////                try {
////                    long valRem = sData.getRem()+ range / 5;
////                    long valDeep = sData.getDeep()+ range / 5;
////                    long valLight = sData.getlight()+ range / 5;
////                    long valWake = sData.getWake()+ range / 5;
//
////                    values.add(new PieEntry(valDeep, sleep[0]));
////                    Log.d(TAG, "setData: Deep = "+ valDeep);
////                    values.add(new PieEntry(valRem, sleep[1]));
////                    Log.d(TAG, "setData: Rem = "+ valRem);
////                    values.add(new PieEntry(valLight, sleep[2]));
////                    Log.d(TAG, "setData: Light = "+ valLight);
////                    values.add(new PieEntry(valWake, sleep[3]));
////                    Log.d(TAG, "setData: Wake = "+ valWake);
////                } catch (IOException e) {
////                    e.printStackTrace();
////                } catch (ParseException e) {
////                    e.printStackTrace();
////                }
//
//                PieDataSet dataSet = new PieDataSet(values, "Partner ");
//                dataSet.setSelectionShift(5f);
//                dataSet.setSliceSpace(3f);
//                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//
//
//                PieData data = new PieData(dataSet);
//                data.setValueFormatter(new PercentFormatter());
//                data.setValueTextSize(15f);
//                data.setValueTextColor(Color.WHITE);
//
//                mChart.setData(data);
////                mChart.invalidate();
//            }
//        }) ).start();
//
//    }
//    private void moveOffscreen(){
//        Display display = getWindowManager().getDefaultDisplay();
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int height = metrics.heightPixels;
//
//        // วางให้อยู่ตรงกลาง
//        int offset = (int)(height * 0.5);
//
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mChart.getLayoutParams();
//        params.setMargins(0,0,0,-offset);
//        mChart.setLayoutParams(params);
//    }
//}
