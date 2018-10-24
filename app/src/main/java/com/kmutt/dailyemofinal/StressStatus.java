package com.kmutt.dailyemofinal;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
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
import com.kmutt.dailyemofinal.R;

import java.util.ArrayList;

public class StressStatus extends AppCompatActivity {


    private PieChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setBackgroundColor(Color.WHITE);
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
        setData(4, 100);
    }

    String[] sleep = new String[]{"2hr", "7hr", "9hr", "10hr"};

    private void setData(int count, int range) {
        ArrayList<PieEntry> values = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float val = (float) ((Math.random() * range) + range / 5);
            values.add(new PieEntry(val, sleep[i]));
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
        mChart.invalidate();
    }

    private void moveOffscreen() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        // วางให้อยู่ตรงกลาง
        int offset = (int) (height * 0.5);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mChart.getLayoutParams();
        params.setMargins(0, 0, 0, -offset);
    }
}