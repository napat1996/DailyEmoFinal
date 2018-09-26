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

import com.google.firebase.database.ValueEventListener;
import com.kmutt.dailyemofinal.Model.FitbitData;
import com.kmutt.dailyemofinal.Model.User;


public class MainActivity extends AppCompatActivity {

    private Thread repeatTaskThread;
    private String TAG = MainActivity.class.getSimpleName();


    BroadcastReceiver broadcastReceiver;

    private static final String API_PREFIX = "https://api.fitbit.com";

    EditText inputUsername, inputEmail, inputPassword, confirmPassword;
    private TextView txtHeartRate, txtSleep, txtActivity, txtTraffic, txtDistance;
    private Button btnHeartRate, btnSleep, btnStep, btnMap, btnEmo;
    private Button btnHome,btnProfile,btnResult,btnSuggesstion;
    private ImageView imgMood;
    private Switch btnSwitch;

    private int imgInt[] = {R.drawable.emo_desperate,R.drawable.emo_blushing};
    Button btnRegister;
    DatabaseReference mRootRef, users;
    FirebaseDatabase database;


    private FusedLocationProviderClient mFusedLocationClient;
    private boolean mLocationPermissionGranted;

    private Location preLocation;
    private Location thisLocation;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int INTERVAL = 5 * 1000;
    private boolean isSterss = false;

    private int heartRate = 0, asSleep = 0;
    public String stressStr = "Normal";
    public String activity = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Intent s = new Intent(getApplicationContext(), SummaryActivity.class);
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

        (new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    FitbitData data = new FitbitData();

                    try {
                        final int heartRate = data.getHeartRateValue();
                        final long sleepMinute = data.getMinutesAsleep();
//                    final String activity = trackActivity.setActivity();
//                    Log.d(TAG, "run: Activity : "+activity);

                        DatabaseService db = new DatabaseService();

                        Log.e(TAG, "onCreateView: sleep : " + sleepMinute);
                        try {
                            db.updateHeartRatetoDB(getApplicationContext().getApplicationContext());
                            db.updateSleepDataToDB(getApplicationContext().getApplicationContext());
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


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

//                                btnEmo = findViewById(R.id.button_mood);
                                imgMood = findViewById(R.id.img_mood);
                                imgMood.setImageResource(imgInt[1]);

                                btnHeartRate = findViewById(R.id.buttom_hr);
                                txtHeartRate = findViewById(R.id.heart_rate);
                                txtHeartRate.setText(heartRate + "");


                                btnSleep = findViewById(R.id.buttom_sleep);
                                txtSleep = findViewById(R.id.text_sleep);
                                txtSleep.setText(sleepMinute + "");

                                btnStep = findViewById(R.id.buttom_step);
//                            txtActivity = getActivity().findViewById(R.id.text_steps);
//                            txtActivity.setText(activity);

                                btnMap = findViewById(R.id.buttom_map2);
//                            txtTraffic = getActivity().findViewById(R.id.buttom_map2);
//                            txtTraffic.setText(traffic+"");


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

                                String statusSwitch;

                                if(btnSwitch.isChecked()){
                                    statusSwitch = btnSwitch.getTextOff().toString();
                                    (new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //ขอ latitide/longtitude ครั้งแรก
                                            getDeviceLocation("initial");
                                            //ทำทุกๆ interval 1 วิ (1*1000)
                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //หลักจาก get location ขอ current แต่ละ location เรื่อยๆ
                                                    getDeviceLocation("service");
                                                    handler.postDelayed(this, INTERVAL);

                                                }
                                            }, INTERVAL);
                                        }
                                    })).start();
                                }
                                else {
                                    statusSwitch = btnSwitch.getTextOn().toString();
                                }
                                Toast.makeText(getApplicationContext(),statusSwitch,Toast.LENGTH_LONG).show();

                            }
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    isStress();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        })).start();



//        Data data = new Data();
//        try {
//            data.getDateOfSleep();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


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
            Log.d(TAG, "handleUserActivity: "+label);
            txtActivity = findViewById(R.id.text_steps);
            txtActivity.setText(label);
        }
    }

    protected void startTracking() {
        Intent intent = new Intent(MainActivity.this, BackgroundDetectedActivitiesService.class);
        startService(intent);
    }


    public String isStress() throws IOException, ParseException {

        boolean isJam = false;
        FitbitData data = new FitbitData();
        boolean isStress;

        int heartRate = data.getHeartRateValue();
        if (heartRate > 100) {

            String mood = "Normal";
            mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User process = dataSnapshot.child("process").getValue(User.class);
                    boolean isJam = process.isTraffic();
                    Log.e(TAG, "onDataChange: isJam ="+ isJam );
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {


                }
            });

            if (asSleep < 40000) {
                isStress = true;
                imgMood.setImageResource(imgInt[0]);
                stressStr = "Stress";
            } else if (isJam == true) {
                isStress = true;
                stressStr = "Stress";
                imgMood.setImageResource(imgInt[0]);
            } else {
                isStress = false;
                stressStr = "Normal";
                imgMood.setImageResource(imgInt[1]);
            }

        } else {
            isStress = false;
            stressStr = "Normal";
            imgMood.setImageResource(imgInt[1]);
        }
        DatabaseReference process = mRootRef.child("process");
        process.child("Stress").setValue(isStress);
        return stressStr;
    }


//    //nav bar
//    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            Fragment selectedFragment = null;
//            switch (item.getItemId()) {
//                case R.id.nav_home:
//                    selectedFragment = bottomNavigationFragments[0];
//                    break;
//                case R.id.nav_sleep:
//                    selectedFragment = bottomNavigationFragments[1];
//                    break;
//                case R.id.nav_map:
//                    selectedFragment = bottomNavigationFragments[2];
//                    break;
//                case R.id.nav_dash:
//                    selectedFragment = bottomNavigationFragments[3];
//            }
//            getSupportFragmentManager().beginTransaction().replace(R.id.flcontent, selectedFragment).commit();
//
//            return true;
//
//        }
//    };
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (toggle.onOptionsItemSelected(item)) {
//            return (true);
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }
//
//
//    public void selectItemDrawer(MenuItem menuItem) {
//        Fragment myFragment = null;
//        Class fragmentClass;
//        switch (menuItem.getItemId()) {
//            case R.id.reminder:
//                fragmentClass = ReminderFragment.class;
//                break;
//            case R.id.setting:
//                fragmentClass = SettingFragment.class;
//                break;
//            case R.id.home:
//                fragmentClass = DhomeFragment.class;
//                break;
//            default:
//                fragmentClass = MainActivity.class;
//        }
//
//        try {
//            myFragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        FragmentManager fragmentManager = getSupportFragmentManager();
//////        fragmentManager.beginTransaction().replace(R.id.flcontent,myFragment).commit();
////        menuItem.setChecked(true);
////        setTitle(menuItem.getTitle());
////        dl.closeDrawers();
//    }
//
//    private void setupDrawerContent(NavigationView navigationView) {
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                selectItemDrawer(item);
//                return true;
//            }
//        });
//    }

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
    int notJamCount = 0 ;
    int locationCount = 0;

    public void calculateVelocity(Context context) throws IOException, ParseException {
        txtTraffic = findViewById(R.id.text_map);


        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SharedPreferences preferences = context.getSharedPreferences("DailyEmoPref", 0);
        String username = preferences.getString("username", "");

        database = FirebaseDatabase.getInstance();

        mRootRef = database.getReferenceFromUrl("https://dailyemo-194412.firebaseio.com/Users/"+username);


        Log.d("Debugging", "in calculate velo");

        (new Thread(new Runnable() {
            private void isTrafficJam(double velocity, int count, long distance) {
                boolean isJam = false;
                if (velocity < 30) {
                    isJam = true;
                    jamCount++;

                    java.util.Calendar calendar = Calendar.getInstance();
                    java.util.Date now = calendar.getTime();

                    DatabaseReference locDate = mRootRef.child("DateTime").child(date);
                    locDate.child("Location").child("TrafficJam"+jamCount).child(now+"").child("Velocity").setValue(velocity);
                    locDate.child("Location").child("TrafficJam"+jamCount).child(now+"").child("Distance").setValue(distance);

                } else {
                    java.util.Calendar calendar = Calendar.getInstance();
                    java.util.Date now = calendar.getTime();
                    isJam = false;
                    notJamCount++;
                    DatabaseReference locDate = mRootRef.child("DateTime").child(date);
                    locDate.child("Location").child("TrafficNotJam"+notJamCount).child(now+"").child("Velocity").setValue(velocity);
                    locDate.child("Location").child("TrafficNotJam"+notJamCount).child(now+"").child("Distance").setValue(distance);
                }
                DatabaseReference process = mRootRef.child("process");
                process.child("Traffic").setValue(isJam);

                // todo implement database transaction
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
                        Log.d(TAG, "run: "+ locationCount + " Distance = "+distance);
                        Log.d(TAG, "run: "+ locationCount + " Duration = "+duration);

                        final int t = 5;
                        final double s = distance;
                        final double v = s / t;

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
                                txtDistance.setText(distance + "");
                                txtTraffic.setText(v + "");
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
                            txtTraffic.setText("error!!");
                        }
                    });
                }
            }

        })).start();
    }
}

