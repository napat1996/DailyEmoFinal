package com.kmutt.dailyemofinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kmutt.dailyemofinal.Model.FitbitData;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity{
    private String TAG = Calendar.class.getSimpleName();
    FitbitData data = new FitbitData();
    MainActivity mainActivity = new MainActivity();
    TextView txtStress;
    CalendarView calendarView;
    Button btnHome,btnProfile,btnResult,btnSuggesstion;

    DatabaseReference mRootRef, users;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

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

        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.child("process").getValue();
                Boolean isStress = (Boolean)value.get("Stress");
                txtStress.setText(isStress + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//                    final String  isStress = mainActivity.isStress();
//                    Log.e(TAG, "onCreate: Stress is "+ isStress);
//                    txtStress = (TextView) findViewById(R.id.isStress);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            txtStress.setText(isStress + "");
//                        }
//                    });
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();



    }
}
