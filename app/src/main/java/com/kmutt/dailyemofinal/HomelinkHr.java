package com.kmutt.dailyemofinal;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class HomelinkHr extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelink_hr);

       /* barChart = (BarChart) findViewById(R.id.barchart_hr);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1, 40f));
        barEntries.add(new BarEntry(2, 30f));
        barEntries.add(new BarEntry(3, 20f));


        BarDataSet barDataSet = new BarDataSet(barEntries, "Data Set1");
        //barDataSet.setColor(ColorTemplate.COLORFUL_COLORS);

        BarData data  =  new BarData(barDataSet);
        data.setBarWidth(0.9f);

        barChart.setData(data) ;*/


    }
}
