package com.kmutt.dailyemofinal;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.kmutt.dailyemofinal.Model.FitbitData;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.kmutt.dailyemofinal.R.id.isStress;

public class Calendar extends AppCompatActivity {

    private String TAG = Calendar.class.getSimpleName();
     FitbitData data = new FitbitData();
     MainActivity mainActivity = new MainActivity();
     TextView txtStress;
     CalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    final String  isStress = mainActivity.isStress();
                    Log.e(TAG, "onCreate: Stress is "+ isStress);
                    txtStress = (TextView) findViewById(R.id.isStress);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            txtStress.setText(isStress + "");
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }

}
