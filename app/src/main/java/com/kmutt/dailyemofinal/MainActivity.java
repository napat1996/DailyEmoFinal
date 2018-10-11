package com.kmutt.dailyemofinal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.google.firebase.database.ValueEventListener;
import com.kmutt.dailyemofinal.Model.FitbitData;
import com.kmutt.dailyemofinal.Model.User;


public class MainActivity extends AppCompatActivity {

    private Thread repeatTaskThread;
    private String TAG = MainActivity.class.getSimpleName();


    BroadcastReceiver broadcastReceiver;

    private static final String API_PREFIX = "https://api.fitbit.com";

    EditText inputUsername, inputEmail, inputPassword, confirmPassword;
    private TextView txtHeartRate, txtSleep, txtActivity, txtTraffic, txtDistance, txtStept;
    private Button btnHeartRate, btnSleep, btnStep, btnMap, btnEmo;
    private Button btnHome, btnProfile, btnResult, btnSuggesstion;
    private ImageView imgMood;
    private Switch btnSwitch;

    private int imgInt[] = {R.drawable.emo_desperate, R.drawable.emo_blushing};
    Button btnRegister;
    DatabaseReference mRootRef, users;
    FirebaseDatabase database;


    private FusedLocationProviderClient mFusedLocationClient;
    private boolean mLocationPermissionGranted;

    private Location preLocation;
    private Location thisLocation;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int INTERVAL = 300 * 1000;
    private boolean isSterss = false;

    private int heartRate = 0, asSleep = 0;
    public String stressStr = "Normal";
    public String activity = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("DailyEmoPref", 0);
        String username = preferences.getString("username", "");

        String firebaseUrl = "https://dailyemo-194412.firebaseio.com/Users/"+username;
        Log.d(TAG, "onCreate: debugging firebaseurl "+firebaseUrl);
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReferenceFromUrl(firebaseUrl);

        DatabaseReference process = mRootRef.child("process");
        process.child("Traffic").setValue(false);
        process.child("Stress").setValue(false);
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


//        broadcastReceiver = new BroadcastReceiver() {
////            @Override
////            public void onReceive(Context context, Intent intent) {
////                if (intent.getAction().equals(Constants.BROADCAST_DETECTED_ACTIVITY)) {
////                    int type = intent.getIntExtra("type", -1);
////                    int confidence = intent.getIntExtra("confidence", 0);
////                    handleUserActivity(type, confidence);
////                }
////            }
////        };
////
////        startTracking();

        imgMood = findViewById(R.id.img_mood);
        imgMood.setImageResource(imgInt[1]);

        btnHeartRate = findViewById(R.id.buttom_hr);

        btnSleep = findViewById(R.id.buttom_sleep);

        btnStep = findViewById(R.id.buttom_step);
        txtStept = findViewById(R.id.text_steps);
//                            txtActivity.setText(activity);

        btnMap = findViewById(R.id.buttom_map2);


        btnHeartRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("go to heart rate graph page");
                Intent myIntent = new Intent(getApplicationContext(), GraphHr.class);
                startActivity(myIntent);

            }
        });

        btnSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("go to sleep graph page");
                Intent myIntent = new Intent(getApplicationContext(), GraphSleep.class);
                startActivity(myIntent);

            }
        });

        btnStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("go to step graph page");
                Intent myIntent = new Intent(getApplicationContext(), HomelinkStep.class);
                startActivity(myIntent);

            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("go to Traffic graph page");
                Intent myIntent = new Intent(getApplicationContext(), GraphTaffic.class);
                startActivity(myIntent);

            }
        });

        btnSwitch = findViewById(R.id.activity_switch);

        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Debugging", "btn switch clicked");
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), isChecked + "", Toast.LENGTH_LONG).show();

                    getDeviceLocation("initial");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //หลักจาก get location ขอ current แต่ละ location เรื่อยๆ
                            (new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    getDeviceLocation("service");
                                }
                            })).start();
                            handler.postDelayed(this, INTERVAL);

                        }
                    }, INTERVAL);

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                            SharedPreferences preferences = getApplicationContext().getSharedPreferences("DailyEmoPref", 0);
                            String username = preferences.getString("username", "");

                            database = FirebaseDatabase.getInstance();

                            mRootRef = database.getReferenceFromUrl("https://dailyemo-194412.firebaseio.com/Users/" + username);
                            DatabaseReference process = mRootRef.child("process");
                            process.child("Traffic").setValue(false);
                        }
                    });
                }
            }
        });


        (new Thread(new Runnable() {
            @Override
            public void run() {

                FitbitData data = new FitbitData();

                try {
                    final long heartRate = data.getHeartRateValue();
                    final long sleepMinute = data.getMinutesAsleep();
                    final long steps = data.getStepsValue();

//                    final String activity = trackActivity.setActivity();
//                    Log.d(TAG, "run: Activity : "+activity);

                    DatabaseService db = new DatabaseService();

                    Log.e(TAG, "onCreateView: sleep : " + sleepMinute);
                    try {
                        db.updateHeartRatetoDB(getApplicationContext().getApplicationContext());
                        db.updateSleepDataToDB(getApplicationContext().getApplicationContext());
                        db.updateSteptoDB(getApplicationContext().getApplicationContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //setContentView(R.layout.activity_main);
                    //เปิด service เพื่อขอ current location
                    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
                    //ขอ permission โทรศัพท์
                    getLocationPermission();
//                    data.upAllHeartRateTimeToDB();


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            txtSleep = findViewById(R.id.text_sleep);
                            txtSleep.setText(sleepMinute + "");
                            Log.e(TAG, "run: sleep ="+ sleepMinute);

                            txtHeartRate = findViewById(R.id.heart_rate);
                            txtHeartRate.setText(heartRate + "");
                            Log.e(TAG, "run: HeartRate = "+heartRate );

                            txtStept.setText(steps+"");


                        }
                    });
                    try {
                        isStress();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } catch (Exception ex) {
                    Log.e("Debugging", "Errorrrrrrrrrrrrrrrrrrrrrrrr!");
                    ex.printStackTrace();
                }


            }
        })).start();




    }

    private void handleUserActivity(int type, int confidence) {
        Log.d(TAG, "in handle user activity");
        String label = "UNKNOWN";

        switch (type) {
            case DetectedActivity.IN_VEHICLE: {
                label = getString(R.string.activity_in_vehicle);
                break;
            }
            case DetectedActivity.ON_BICYCLE: {
                label = getString(R.string.activity_on_bicycle);
                break;
            }
            case DetectedActivity.ON_FOOT: {
                label = getString(R.string.activity_on_foot);
                break;
            }
            case DetectedActivity.RUNNING: {
                label = getString(R.string.activity_running);
                break;
            }
            case DetectedActivity.STILL: {
                label = getString(R.string.activity_still);
                break;
            }
            case DetectedActivity.TILTING: {
                label = getString(R.string.activity_tilting);
                break;
            }
            case DetectedActivity.WALKING: {
                label = getString(R.string.activity_walking);
                break;
            }
            case DetectedActivity.UNKNOWN: {
                label = getString(R.string.activity_unknown);
                break;
            }

        }


        Log.e(TAG, "User activity: " + label + ", Confidence: " + confidence);

        if (confidence > Constants.CONFIDENCE) {
            activity = label;
            Log.d(TAG, "handleUserActivity: " + label);
            txtActivity = findViewById(R.id.text_steps);
            txtActivity.setText(label);
        }
    }

    protected void startTracking() {
        Intent intent = new Intent(MainActivity.this, BackgroundDetectedActivitiesService.class);
        startService(intent);
    }


    public void isStress() throws IOException, ParseException {

        boolean isJam = false;
        final FitbitData data = new FitbitData();

        long heartRate = data.getHeartRateValue();
//        int heartRate = 101;
        if (heartRate > 65) {

            String mood = "Normal";
            mRootRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("Debugging", "on Data change is running");
                    Map<String, Object> value = (Map<String, Object>) dataSnapshot.child("process").getValue();
                    final Boolean isJam = (Boolean)value.get("Traffic");
                    Log.e(TAG, "onDataChange: isJam =" + isJam);
                    (new Thread(new Runnable() {
                        @Override
                        public void run() {
                            boolean isStress = false;
                            long asSleep = 0;
                            try {
                                asSleep = data.getMinutesAsleep();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (asSleep < 400) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        imgMood.setImageResource(R.drawable.emo_desperate);
                                        Log.d(TAG, "Debugging stress because sleep: ");
                                    }
                                });

                                stressStr = "Stress";
                            }

                            if (btnSwitch.isChecked()) {
                                if (isJam) {
                                    isStress = true;
                                    stressStr = "Stress";
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            imgMood.setImageResource(imgInt[0]);
                                            Log.d(TAG, "Debugging stress because Traffic: ");
                                        }
                                    });
                                } else {
                                    isStress = false;
                                    stressStr = "Normal";
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            imgMood.setImageResource(imgInt[1]);
                                        }
                                    });
                                }
                            }

                            Log.d(TAG, "Debugging: Sleep"+ asSleep);

                            DatabaseReference process = mRootRef.child("process");
                            process.child("Stress").setValue(isStress);
//                            process.child("Traffic").setValue(false);
                        }
                    })).start();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Debugging", "on Data change error");
                    Log.e(TAG, "onCancelled: ", databaseError.toException());
                }
            });


//old
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imgMood.setImageResource(imgInt[1]);
                }
            });
        }
        DatabaseReference process = mRootRef.child("process");
        process.child("HeartRate").setValue(heartRate);
    }


    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    private void getDeviceLocation(final String type) {
        Log.d("Debugging", "in getDevicePermission");
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationClient.getLastLocation();
                locationResult.addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d("Debugging", "in onSuccess");
                        if (location != null) {
                            if (type == "initial") preLocation = location;
                            else {
                                thisLocation = location;
                            }

                            try {
                                calculateVelocity(getBaseContext());
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("Debugging", "location is null");
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    int jamCount = 0;
    int notJamCount = 0;
    int locationCount = 0;

    public void calculateVelocity(Context context) throws IOException, ParseException {
        txtTraffic = findViewById(R.id.text_map);


        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SharedPreferences preferences = context.getSharedPreferences("DailyEmoPref", 0);
        String username = preferences.getString("username", "");

        database = FirebaseDatabase.getInstance();

        mRootRef = database.getReferenceFromUrl("https://dailyemo-194412.firebaseio.com/Users/" + username);


        Log.d("Debugging", "in calculate velo");

        (new Thread(new Runnable() {
            private void isTrafficJam(double velocity, int count, long distance) {
                boolean isJam = false;
                Log.d(TAG, "Debugging : isTrafficJam: " + velocity);
                if (velocity < 20) {
                    isJam = true;
                    jamCount++;

                    Calendar calendar = Calendar.getInstance();
                    Date now = calendar.getTime();

                    DatabaseReference locDate = mRootRef.child("DateTime").child(date);
                    locDate.child("Location").child("TrafficJam").child(""+jamCount).child(now + "").child("Velocity").setValue(velocity);
                    locDate.child("Location").child("TrafficJam").child(""+jamCount).child(now + "").child("Distance").setValue(distance);

                } else {
                    Calendar calendar = Calendar.getInstance();
                    Date now = calendar.getTime();
                    isJam = false;
                    notJamCount++;
                    DatabaseReference locDate = mRootRef.child("DateTime").child(date);
                    locDate.child("Location").child("TrafficNotJam").child(""+ notJamCount).child(now + "").child("Velocity").setValue(velocity);
                    locDate.child("Location").child("TrafficNotJam").child("" + notJamCount).child(now + "").child("Distance").setValue(distance);
                }
                Log.e(TAG, "isTrafficJam: Debugging : "+isJam );
                DatabaseReference process = mRootRef.child("process");
                process.child("Traffic").setValue(isJam);

            }

            @Override
            public void run() {

                if (preLocation != null && thisLocation != null) {
                    String url = "https://maps.googleapis.com/maps/api/directions/json?";
                    url += "origin=" + preLocation.getLatitude() + "," + preLocation.getLongitude();
                    url += "&destination=" + thisLocation.getLatitude() + "," + thisLocation.getLongitude();
                    url += "&key=" + "AIzaSyDjEK_vRWBhbFL4S_3CsXWO-TG_7bBkXwk";
                    //ปริ้นดูค่าใน logcat จะขึ้น http คลิกตามลิ้ง
                    Log.d("Debugging", url);
                    try {
                        URLConnection connection = new URL(url).openConnection();
                        InputStream response = connection.getInputStream();
                        JSONParser parser = new JSONParser();
                        JSONObject result = (JSONObject) parser.parse(new InputStreamReader(response, "UTF-8"));
                        JSONObject routes = (JSONObject) ((JSONArray) result.get("routes")).get(0);
                        JSONObject legs = (JSONObject) ((JSONArray) routes.get("legs")).get(0);
                        final Long distance = (Long) ((JSONObject) legs.get("distance")).get("value");
                        Long duration = (Long) ((JSONObject) legs.get("duration")).get("value");
                        locationCount++;
//                        JSONObject end_location = (JSONObject)legs.get("end_location");
//                        final String lat = (JSONObject)end_location.get("lat")+"";
//                        final String lng = (JSONObject)end_location.get("lng")+"";
//                        final String strLat = lat+"";
//
//                        Log.d(TAG, "run: Lat : "+lat);
//                        Log.d(TAG, "run: Lat : "+lng);
                        // calculate เพือหา v ในทุกๆ 5 นาที ทำอันนี้******** (ซึ่งตอนนี้เป็น1วิ)
                        Log.d(TAG, "run: " + locationCount + " Distance = " + distance/100);
                        Log.d(TAG, "run: " + locationCount + " Duration = " + duration);

                        final int t = 5;
                        final double s = distance/100;
                        final double v = s / t;
//                        final double v = 180.0;

                        this.isTrafficJam(v, locationCount, distance);


                        Log.e(TAG, "calculateVelocity: V = " + v);

                        // reset location
                        //ห้ามลบบรรทัดนี้*****
                        preLocation = thisLocation;
                        thisLocation = null;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtDistance = findViewById(R.id.text_activity);
                                txtDistance.setText(s + "");
                                txtTraffic.setText(v + "");
                            }
                        });

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtTraffic.setText("--");
                        }
                    });
                }
            }

        })).start();




    }
}

