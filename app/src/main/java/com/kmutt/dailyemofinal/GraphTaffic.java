package com.kmutt.dailyemofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class GraphTaffic extends AppCompatActivity {
    private BarChart mChart;
    Button btnHome, btnProfile, btnResult, btnSuggesstion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_hr);
        mChart = (BarChart) findViewById(R.id.bar_taf);
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

    public void setData(int count) {
        ArrayList<BarEntry> yValues = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int val1 = (int) (Math.random() * count) + 15;

            yValues.add(new BarEntry(i, new float[]{val1}));
        }
        BarDataSet set1;

        set1 = new BarDataSet(yValues, "minutes of traffic");
        set1.setDrawIcons(false);
        set1.setColors(getColors());
//        set1.setColors(ColorTemplate.PASTEL_COLORS);


        BarData data = new BarData(set1);
        data.setValueFormatter(new MyValueFormatter());

        mChart.getAxisLeft().setAxisMaximum(60);

        mChart.setData(data);
        mChart.setFitBars(true);
        mChart.invalidate();
        mChart.getDescription().setEnabled(false);
        mChart.getXAxis().setDrawGridLines(false);

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
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_graph_taffic);
//    }
//}
