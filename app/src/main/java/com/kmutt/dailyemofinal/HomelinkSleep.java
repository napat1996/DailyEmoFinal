package com.kmutt.dailyemofinal;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.RelativeLayout;

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
    private void moveOffscreen(){
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int offset = (int)(height*0.5);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mChart.getLayoutParams();
        params.setMargins(0,0,0,-offset);
        mChart.setLayoutParams(params);
    }
}
