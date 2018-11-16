package com.kmutt.dailyemofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;


public class MyValue implements IValueFormatter {

   private DecimalFormat mFormat;
   public void Myvalue() {
       mFormat = new DecimalFormat("######.0");
   }
   @Override
    public  String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler){
       return mFormat.format(value) +"mins";
   }
}
