package com.kmutt.dailyemofinal;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
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

    private PieChart mChart;
    private String TAG = GraphSleep.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_sleep);


        mChart = (PieChart)findViewById(R.id.chart1);
       // mChart.setBackgroundColor(Color.WHITE);
        moveOffscreen();

        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setDrawHoleEnabled(true);

        mChart.setMaxAngle(180);
        mChart.setRotationAngle(180);
        mChart.setCenterTextOffset(0, -20);

        // คลิกสไลด์กางออกมา
        mChart.animateY(1000, Easing.EasingOption.EaseInCubic);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setYOffset(5);


        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);

        try {
            setData(4, 100);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
    String[] sleep = new String[] {"DEEP", "LIGHT", "REM", "WAKE"};


    private void setData (int count, final int range) throws IOException, ParseException {

//        Log.d(TAG, "setData: Rang = "+ range);
//        for (int i = 0 ; i<count; i ++ ){
//            float val = (float)((Math.random()*range)+ range/5);
//            Log.e(TAG, "setData: Val = "+ val );
//            values.add(new PieEntry(val, sleep[i]));
//        }

        (new Thread(new Runnable() {
            @Override
            public void run() {
                FitbitData sData = new FitbitData();
                ArrayList<PieEntry> values = new ArrayList<>();
                try {
                    long valRem = sData.getRem()+ range / 5;
                    long valDeep = sData.getDeep()+ range / 5;
                    long valLight = sData.getlight()+ range / 5;
                    long valWake = sData.getWake()+ range / 5;

                    values.add(new PieEntry(valDeep, sleep[0]));
                    Log.d(TAG, "setData: Deep = "+ valDeep);
                    values.add(new PieEntry(valRem, sleep[1]));
                    Log.d(TAG, "setData: Rem = "+ valRem);
                    values.add(new PieEntry(valLight, sleep[2]));
                    Log.d(TAG, "setData: Light = "+ valLight);
                    values.add(new PieEntry(valWake, sleep[3]));
                    Log.d(TAG, "setData: Wake = "+ valWake);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                PieDataSet dataSet = new PieDataSet(values, "Partner ");
                dataSet.setSelectionShift(5f);
                dataSet.setSliceSpace(3f);
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);


                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(15f);
                data.setValueTextColor(Color.WHITE);

                mChart.setData(data);
//                mChart.invalidate();



            }
        }) ).start();




    }

    private void moveOffscreen(){
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;

        // วางให้อยู่ตรงกลาง
        int offset = (int)(height * 0.5);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mChart.getLayoutParams();
        params.setMargins(0,0,0,-offset);
        mChart.setLayoutParams(params);
    }
}
