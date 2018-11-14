package com.kmutt.dailyemofinal;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kmutt.dailyemofinal.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class StressStatus extends AppCompatActivity {


//    private PieChart mChart,mChart1,mChart2,mChart3,mChart4,mChart5,mChart6,mChart7;
//    private BarChart barChart;

    TextView txt_sDay1, txt_sDay2, txt_sDay3, txt_sDay4, txt_sDay5, txt_sDay6, txt_sDay7;
    FirebaseDatabase database;
    DatabaseReference mRootRef, users;
    private LineChart sChart0,sChart1, sChart2, sChart3, sChart4, sChart5, sChart6, sChart7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_status);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.text_date);
        textViewDate.setText(currentDate);



        txt_sDay1 = findViewById(R.id.day_s1);
        txt_sDay2 = findViewById(R.id.day_s2);
        txt_sDay3 = findViewById(R.id.day_s3);
        txt_sDay4 = findViewById(R.id.day_s4);
        txt_sDay5 = findViewById(R.id.day_s5);
        txt_sDay6 = findViewById(R.id.day_s6);
        txt_sDay7 = findViewById(R.id.day_s7);

        //****LINECHART*****
        sChart0 = findViewById(R.id.pieChart);
        sChart1 = findViewById(R.id.pieChart1);
        sChart2 = findViewById(R.id.pieChart2);
        sChart3 = findViewById(R.id.pieChart3);
        sChart4 = findViewById(R.id.pieChart4);
        sChart5 = findViewById(R.id.pieChart5);
        sChart6 = findViewById(R.id.pieChart6);
        sChart7 = findViewById(R.id.pieChart7);


        sChart0.setDragEnabled(true);
        sChart0.setScaleEnabled(false);

        sChart1.setDragEnabled(true);
        sChart1.setScaleEnabled(false);

        sChart2.setDragEnabled(true);
        sChart2.setScaleEnabled(false);

        sChart3.setDragEnabled(true);
        sChart3.setScaleEnabled(false);

        sChart4.setDragEnabled(true);
        sChart4.setScaleEnabled(false);

        sChart5.setDragEnabled(true);
        sChart5.setScaleEnabled(false);

        sChart6.setDragEnabled(true);
        sChart6.setScaleEnabled(false);

        sChart7.setDragEnabled(true);
        sChart7.setScaleEnabled(false);


//        LimitLine upper_limit = new LimitLine(65f, "Danger");
//        upper_limit.setLineWidth(4f);
//        upper_limit.enableDashedLine(10f, 10f, 0f);
//        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        upper_limit.setTextSize(15f);
//
//
//        LimitLine lower_limit = new LimitLine(45f, "Too low");
//        upper_limit.setLineWidth(4f);
//        upper_limit.enableDashedLine(10f, 10f, 0f);
//        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        upper_limit.setTextSize(15f);

        YAxis leftAxis0 = sChart0.getAxisLeft();
        leftAxis0.removeAllLimitLines();
//        leftAxis0.addLimitLine(upper_limit);
//        leftAxis0.addLimitLine(lower_limit);
        leftAxis0.setAxisMaximum(130f);
        leftAxis0.setAxisMinimum(40f);
        leftAxis0.enableGridDashedLine(10f, 10f, 10);
        leftAxis0.setDrawLimitLinesBehindData(true);


        YAxis leftAxis1 = sChart1.getAxisLeft();
        leftAxis1.removeAllLimitLines();
//        leftAxis1.addLimitLine(upper_limit);
//        leftAxis1.addLimitLine(lower_limit);
        leftAxis1.setAxisMaximum(130f);
        leftAxis1.setAxisMinimum(40f);
        leftAxis1.enableGridDashedLine(10f, 10f, 10);
        leftAxis1.setDrawLimitLinesBehindData(true);

        YAxis leftAxis2 = sChart2.getAxisLeft();
        leftAxis2.removeAllLimitLines();
//        leftAxis2.addLimitLine(upper_limit);
//        leftAxis2.addLimitLine(lower_limit);
        leftAxis2.setAxisMaximum(130f);
        leftAxis2.setAxisMinimum(40f);
        leftAxis2.enableGridDashedLine(10f, 10f, 10);
        leftAxis2.setDrawLimitLinesBehindData(true);

        YAxis leftAxis3 = sChart3.getAxisLeft();
        leftAxis3.removeAllLimitLines();
//        leftAxis3.addLimitLine(upper_limit);
//        leftAxis3.addLimitLine(lower_limit);
        leftAxis3.setAxisMaximum(130f);
        leftAxis3.setAxisMinimum(40f);
        leftAxis3.enableGridDashedLine(10f, 10f, 10);
        leftAxis3.setDrawLimitLinesBehindData(true);

        YAxis leftAxis4 = sChart4.getAxisLeft();
        leftAxis4.removeAllLimitLines();
//        leftAxis4.addLimitLine(upper_limit);
//        leftAxis4.addLimitLine(lower_limit);
        leftAxis4.setAxisMaximum(130f);
        leftAxis4.setAxisMinimum(40f);
        leftAxis4.enableGridDashedLine(10f, 10f, 10);
        leftAxis4.setDrawLimitLinesBehindData(true);

        YAxis leftAxis5 = sChart5.getAxisLeft();
        leftAxis5.removeAllLimitLines();
//        leftAxis5.addLimitLine(upper_limit);
//        leftAxis5.addLimitLine(lower_limit);
        leftAxis5.setAxisMaximum(130f);
        leftAxis5.setAxisMinimum(40f);
        leftAxis5.enableGridDashedLine(10f, 10f, 10);
        leftAxis5.setDrawLimitLinesBehindData(true);

        YAxis leftAxis6 = sChart6.getAxisLeft();
        leftAxis6.removeAllLimitLines();
//        leftAxis6.addLimitLine(upper_limit);
//        leftAxis6.addLimitLine(lower_limit);
        leftAxis6.setAxisMaximum(130f);
        leftAxis6.setAxisMinimum(40f);
        leftAxis6.enableGridDashedLine(10f, 10f, 10);
        leftAxis6.setDrawLimitLinesBehindData(true);

        YAxis leftAxis7 = sChart7.getAxisLeft();
        leftAxis7.removeAllLimitLines();
//        leftAxis7.addLimitLine(upper_limit);
//        leftAxis7.addLimitLine(lower_limit);
        leftAxis7.setAxisMaximum(130f);
        leftAxis7.setAxisMinimum(40f);
        leftAxis7.enableGridDashedLine(10f, 10f, 10);
        leftAxis7.setDrawLimitLinesBehindData(true);

        //delete line on the right side
        sChart0.getAxisRight().setEnabled(false);
        sChart1.getAxisRight().setEnabled(false);
        sChart2.getAxisRight().setEnabled(false);
        sChart3.getAxisRight().setEnabled(false);
        sChart4.getAxisRight().setEnabled(false);
        sChart5.getAxisRight().setEnabled(false);
        sChart6.getAxisRight().setEnabled(false);
        sChart7.getAxisRight().setEnabled(false);







//      Half Pie Chart
//
//        mChart = findViewById(R.id.pieChart);
//        mChart1 = findViewById(R.id.pieChart1);
//        mChart2 = findViewById(R.id.pieChart2);
//        mChart3 = findViewById(R.id.pieChart3);
//        mChart4 = findViewById(R.id.pieChart4);
//        mChart5 = findViewById(R.id.pieChart5);
//        mChart6 = findViewById(R.id.pieChart6);
//        mChart7 = findViewById(R.id.pieChart7);
//
//
//
////        mChart.setBackgroundColor(Color.WHITE);
////        moveOffscreen();
//        mChart.setUsePercentValues(true);
//        mChart.getDescription().setEnabled(false);
//        mChart.setDrawHoleEnabled(true);
//        mChart.setCenterTextOffset(0, -20);
////        mChart.setMaxAngle(180);
////        mChart.setRotationAngle(180);
//
//        mChart1.setUsePercentValues(true);
//        mChart1.getDescription().setEnabled(false);
//        mChart1.setDrawHoleEnabled(false);
//        mChart1.setCenterTextOffset(0, -20);
//        mChart1.setMaxAngle(180);
//        mChart1.setRotationAngle(180);
//
//        mChart2.setUsePercentValues(true);
//        mChart2.getDescription().setEnabled(false);
//        mChart2.setDrawHoleEnabled(false);
//        mChart2.setCenterTextOffset(0, -20);
//        mChart2.setMaxAngle(180);
//        mChart2.setRotationAngle(180);
//
//        mChart3.setUsePercentValues(true);
//        mChart3.getDescription().setEnabled(false);
//        mChart3.setDrawHoleEnabled(false);
//        mChart3.setCenterTextOffset(0, -20);
//        mChart3.setMaxAngle(180);
//        mChart3.setRotationAngle(180);
//
//        mChart4.setUsePercentValues(true);
//        mChart4.getDescription().setEnabled(false);
//        mChart4.setDrawHoleEnabled(false);
//        mChart4.setCenterTextOffset(0, -20);
//        mChart4.setMaxAngle(180);
//        mChart4.setRotationAngle(180);
//
//        mChart5.setUsePercentValues(true);
//        mChart5.getDescription().setEnabled(false);
//        mChart5.setDrawHoleEnabled(false);
//        mChart5.setCenterTextOffset(0, -20);
//        mChart5.setMaxAngle(180);
//        mChart5.setRotationAngle(180);
//
//        mChart6.setUsePercentValues(true);
//        mChart6.getDescription().setEnabled(false);
//        mChart6.setDrawHoleEnabled(false);
//        mChart6.setCenterTextOffset(0, -20);
//        mChart6.setMaxAngle(180);
//        mChart6.setRotationAngle(180);
//
//        mChart7.setUsePercentValues(true);
//        mChart7.getDescription().setEnabled(false);
//        mChart7.setDrawHoleEnabled(false);
//        mChart7.setCenterTextOffset(0, -20);
//        mChart7.setMaxAngle(180);
//        mChart7.setRotationAngle(180);

//
//        // คลิกสไลด์กางออกมา
//        mChart.animateY(1000, Easing.EasingOption.EaseInCubic);
//        Legend l = mChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setYOffset(5);
//        mChart.setEntryLabelColor(Color.WHITE);//color level text
//        mChart.setEntryLabelTextSize(12f);
//        setData(4, 100);
//
//
//        mChart1.animateY(1000, Easing.EasingOption.EaseInCubic);
//        Legend a = mChart1.getLegend();
//        a.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        a.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        a.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        a.setDrawInside(false);
//        a.setYOffset(5);
//        mChart1.setEntryLabelColor(Color.WHITE);//color level text
//        mChart1.setEntryLabelTextSize(12f);
//        setData1(4, 100);
//
//        mChart2.animateY(1000, Easing.EasingOption.EaseInCubic);
//        Legend b = mChart2.getLegend();
//        b.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        b.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        b.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        b.setDrawInside(false);
//        b.setYOffset(5);
//        mChart2.setEntryLabelColor(Color.WHITE);//color level text
//        mChart2.setEntryLabelTextSize(12f);
//        setData2(4, 100);
//
//        mChart3.animateY(1000, Easing.EasingOption.EaseInCubic);
//        Legend c = mChart3.getLegend();
//        c.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        c.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        c.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        c.setDrawInside(false);
//        c.setYOffset(5);
//        mChart3.setEntryLabelColor(Color.WHITE);//color level text
//        mChart3.setEntryLabelTextSize(12f);
//        setData3(4, 100);
//
//        mChart4.animateY(1000, Easing.EasingOption.EaseInCubic);
//        Legend d = mChart4.getLegend();
//        d.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        d.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        d.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        d.setDrawInside(false);
//        d.setYOffset(5);
//        mChart4.setEntryLabelColor(Color.WHITE);//color level text
//        mChart4.setEntryLabelTextSize(12f);
//        setData4(4, 100);
//
//        mChart5.animateY(1000, Easing.EasingOption.EaseInCubic);
//        Legend e = mChart5.getLegend();
//        e.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        e.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        e.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        e.setDrawInside(false);
//        e.setYOffset(5);
//        mChart5.setEntryLabelColor(Color.WHITE);//color level text
//        mChart5.setEntryLabelTextSize(12f);
//        setData5(4, 100);
//
//        mChart6.animateY(1000, Easing.EasingOption.EaseInCubic);
//        Legend f = mChart6.getLegend();
//        f.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        f.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        f.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        f.setDrawInside(false);
//        f.setYOffset(5);
//        mChart6.setEntryLabelColor(Color.WHITE);//color level text
//        mChart6.setEntryLabelTextSize(12f);
//        setData6(4, 100);
//
//        mChart7.animateY(1000, Easing.EasingOption.EaseInCubic);
//        Legend g = mChart7.getLegend();
//        g.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        g.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        g.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        g.setDrawInside(false);
//        g.setYOffset(5);
//        mChart7.setEntryLabelColor(Color.WHITE);//color level text
//        mChart7.setEntryLabelTextSize(12f);
//        setData7(4, 100);
//
//
//
//    String[] stress = new String[]{"level1", "level2","level3","level4"};
//
//    private void setData(int count, int range) {
//        ArrayList<PieEntry> values = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            float val = (float) ((Math.random() * range) + range / 5);
//            values.add(new PieEntry(val, stress[i]));
//        }
//        PieDataSet dataSet = new PieDataSet(values, "Partner ");
//        dataSet.setSelectionShift(5f);
//        dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
//        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(15f);
//        data.setValueTextColor(Color.WHITE);
//
//
//        // สีแต่ละอันในพาย
//        final int[] MY_COLORS = {   Color.rgb(132,139,91),
//                                    Color.rgb(230,197,112),
//                                    Color.rgb(232,153,74),
//                                    Color.rgb(197,95,78), };
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        for(int c: MY_COLORS) colors.add(c);
//        dataSet.setColors(colors);
//
//        mChart.setData(data);
//        mChart.invalidate();
//    }
//
//
//    String[] stress1 = new String[]{"level1", "level2","level3","level4"};
//
//    private void setData1(int count, int range) {
//        ArrayList<PieEntry> values = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            float val = (float) ((Math.random() * range) + range / 5);
//            values.add(new PieEntry(val, stress1[i]));
//        }
//        PieDataSet dataSet = new PieDataSet(values, "Partner ");
//        dataSet.setSelectionShift(5f);
//        dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
//        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(15f);
//        data.setValueTextColor(Color.WHITE);
//
//
//
//
//
//        mChart1.setData(data);
//        mChart1.invalidate();
//    }
//
//    String[] stress2 = new String[]{"level1", "level2","level3","level4"};
//
//    private void setData2(int count, int range) {
//        ArrayList<PieEntry> values = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            float val = (float) ((Math.random() * range) + range / 5);
//            values.add(new PieEntry(val, stress2[i]));
//        }
//        PieDataSet dataSet = new PieDataSet(values, "Partner ");
//        dataSet.setSelectionShift(5f);
//        dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
//        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
//
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(15f);
//        data.setValueTextColor(Color.WHITE);
//
//        mChart2.setData(data);
//        mChart2.invalidate();
//    }
//
//    String[] stress3 = new String[]{"level1", "level2","level3","level4"};
//
//    private void setData3(int count, int range) {
//        ArrayList<PieEntry> values = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            float val = (float) ((Math.random() * range) + range / 5);
//            values.add(new PieEntry(val, stress3[i]));
//        }
//        PieDataSet dataSet = new PieDataSet(values, "Partner ");
//        dataSet.setSelectionShift(5f);
//        dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
//        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(15f);
//        data.setValueTextColor(Color.WHITE);
//
//        mChart3.setData(data);
//        mChart3.invalidate();
//    }
//
//    String[] stress4 = new String[]{"level1", "level2","level3","level4"};
//
//    private void setData4(int count, int range) {
//        ArrayList<PieEntry> values = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            float val = (float) ((Math.random() * range) + range / 5);
//            values.add(new PieEntry(val, stress4[i]));
//        }
//        PieDataSet dataSet = new PieDataSet(values, "Partner ");
//        dataSet.setSelectionShift(5f);
//        dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
//        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(15f);
//        data.setValueTextColor(Color.WHITE);
//
//        mChart4.setData(data);
//        mChart4.invalidate();
//    }
//
//    String[] stress5 = new String[]{"level1", "level2","level3","level4"};
//
//    private void setData5(int count, int range) {
//        ArrayList<PieEntry> values = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            float val = (float) ((Math.random() * range) + range / 5);
//            values.add(new PieEntry(val, stress5[i]));
//        }
//        PieDataSet dataSet = new PieDataSet(values, "Partner ");
//        dataSet.setSelectionShift(5f);
//        dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
//        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(15f);
//        data.setValueTextColor(Color.WHITE);
//
//        mChart5.setData(data);
//        mChart5.invalidate();
//    }
//
//    String[] stress6 = new String[]{"level1", "level2","level3","level4"};
//
//    private void setData6(int count, int range) {
//        ArrayList<PieEntry> values = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            float val = (float) ((Math.random() * range) + range / 5);
//            values.add(new PieEntry(val, stress6[i]));
//        }
//        PieDataSet dataSet = new PieDataSet(values, "Partner ");
//        dataSet.setSelectionShift(5f);
//        dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
//        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(15f);
//        data.setValueTextColor(Color.WHITE);
//
//        mChart6.setData(data);
//        mChart6.invalidate();
//    }
//
//
//    String[] stress7 = new String[]{"level1", "level2","level3","level4"};
//
//    private void setData7(int count, int range) {
//        ArrayList<PieEntry> values = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            float val = (float) ((Math.random() * range) + range / 5);
//            values.add(new PieEntry(val, stress7[i]));
//        }
//        PieDataSet dataSet = new PieDataSet(values, "Partner ");
//        dataSet.setSelectionShift(5f);
//        dataSet.setSliceSpace(3f); //เส้นห่างระหว่างตัวแบ่ง
//        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(15f);
//        data.setValueTextColor(Color.WHITE);
//
//        mChart7.setData(data);
//        mChart7.invalidate();
//    }
//
////    private void moveOffscreen() {
////        Display display = getWindowManager().getDefaultDisplay();
////        DisplayMetrics metrics = new DisplayMetrics();
////        getWindowManager().getDefaultDisplay().getMetrics(metrics);
////        int height = metrics.heightPixels;
////        // วางให้อยู่ตรงกลาง
////        int offset = (int) (height * 0.5);
////        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mChart.getLayoutParams();
////        params.setMargins(0, 0, 0, -offset);
////    }
//
//
    }
}