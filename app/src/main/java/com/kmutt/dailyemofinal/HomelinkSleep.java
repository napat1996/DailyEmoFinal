package com.kmutt.dailyemofinal;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;

public class HomelinkSleep extends AppCompatActivity {

    private PieChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelink_sleep);


        mChart = (PieChart)findViewById(R.id.chart1);
        mChart.setBackgroundColor(Color.WHITE);
    }
}
