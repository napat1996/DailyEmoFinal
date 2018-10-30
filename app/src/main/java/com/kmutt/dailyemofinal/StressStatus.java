package com.kmutt.dailyemofinal;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.RelativeLayout;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
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
import com.kmutt.dailyemofinal.R;

import java.util.ArrayList;

public class StressStatus extends AppCompatActivity {


    private PieChart mChart;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_status);

        mChart = findViewById(R.id.pieChart);
//        mChart.setBackgroundColor(Color.WHITE);
//        moveOffscreen();
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setDrawHoleEnabled(true);
//        mChart.setMaxAngle(180);
//        mChart.setRotationAngle(180);
        mChart.setCenterTextOffset(0, -20);


        /// barChart - Daily
//        barChart = findViewById(R.id.stresschart);
//        barChart.setMaxVisibleValueCount(40);
//        setData2(4);


        // คลิกสไลด์กางออกมา
        mChart.animateY(1000, Easing.EasingOption.EaseInCubic);
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setYOffset(5);

        mChart.setEntryLabelColor(Color.WHITE);//color level text
        mChart.setEntryLabelTextSize(12f);
        setData(4, 100);
    }


    String[] stress = new String[]{"level1", "level2","level3","level4"};

    private void setData(int count, int range) {
        ArrayList<PieEntry> values = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float val = (float) ((Math.random() * range) + range / 5);
            values.add(new PieEntry(val, stress[i]));
        }
        PieDataSet dataSet = new PieDataSet(values, "Partner ");
        dataSet.setSelectionShift(5f);
        dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);
        mChart.invalidate();
    }

//    private void moveOffscreen() {
//        Display display = getWindowManager().getDefaultDisplay();
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int height = metrics.heightPixels;
//        // วางให้อยู่ตรงกลาง
//        int offset = (int) (height * 0.5);
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mChart.getLayoutParams();
//        params.setMargins(0, 0, 0, -offset);
//    }

//    public void setData2(int count) {
//        ArrayList<BarEntry> yValues = new ArrayList<>();
//
//        for (int i = 0; i < count; i++) {
//            float val1 = (float) (Math.random() * count) + 20;
//            float val2 = (float) (Math.random() * count) + 20;
//            float val3 = (float) (Math.random() * count) + 20;
//            float val4 = (float) (Math.random() * count) + 20;
//
//
//            yValues.add(new BarEntry(i, new float[]{val1, val2, val3,val4}));
//        }
//        BarDataSet set1;
//        set1 = new BarDataSet(yValues, "Mood");
//        set1.setDrawIcons(false);
//        set1.setStackLabels(new String[] {"level1, level2,level,3, level4"});
//
//        BarData data = new BarData(set1);
//        data.setValueFormatter(new MyValueFormatter());
//
//        barChart.setData(data);
//        barChart.setFitBars(true);
//        barChart.invalidate();
//
//
//
//    }
}