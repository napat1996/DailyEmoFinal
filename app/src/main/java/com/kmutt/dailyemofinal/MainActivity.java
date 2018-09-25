package com.kmutt.dailyemofinal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.kmutt.dailyemofinal.Calendar;
import com.kmutt.dailyemofinal.Model.FitbitData;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {

    private Thread repeatTaskThread;
    private String TAG = MainActivity.class.getSimpleName();


    BroadcastReceiver broadcastReceiver;

    private static final String API_PREFIX = "https://api.fitbit.com";

    EditText inputUsername, inputEmail, inputPassword, confirmPassword;
    private TextView txtHeartRate, txtSleep, txtActivity, txtTraffic, txtDistance;
    private Button btnHeartRate, btnSleep, btnStep, btnMap, btnEmo;
    Button btnRegister;
    DatabaseReference mRootRef, users;
    FirebaseDatabase database;

//    private DrawerLayout dl;
//    private ActionBarDrawerToggle toggle;
//
//    Fragment[] bottomNavigationFragments;

    private FusedLocationProviderClient mFusedLocationClient;
    private boolean mLocationPermissionGranted;

    private Location preLocation;
    private Location thisLocation;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int INTERVAL = 1 * 1000;
    private boolean isSterss = false;

    private int heartRate = 0, asSleep = 0;
    public String stressStr = "Normal";
    public String activity = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BROADCAST_DETECTED_ACTIVITY)) {
                    int type = intent.getIntExtra("type", -1);
                    int confidence = intent.getIntExtra("confidence", 0);
                    handleUserActivity(type, confidence);
                }
            }
        };

        startTracking();

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
                            db.updateHeartRateDataToDB(getApplicationContext().getApplicationContext());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                btnEmo = findViewById(R.id.button_mood);

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


                                btnEmo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        System.out.println("go to Mood graph page");
                                        Intent myIntent = new Intent(getApplicationContext(), Calendar.class);
                                        startActivity(myIntent);

                                    }
                                });

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

        //setContentView(R.layout.activity_main);
        //เปิด service เพื่อขอ current location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //ขอ permission โทรศัพท์
        getLocationPermission();
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
        boolean isJam = true;
        FitbitData data = new FitbitData();
        boolean isStress;

        int heartRate = data.getHeartRateValue();
        if (heartRate > 100) {
            if (asSleep < 40000) {
                isStress = true;
                stressStr = "Stress";
            } else if (isJam == true) {
                isStress = true;
                stressStr = "Normal";
            } else {
                isStress = false;
                stressStr = "Normal";
            }

        } else {
            isStress = false;
            stressStr = "Normal";
        }
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
                                calculateVelocity();
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

    public void calculateVelocity() throws IOException, ParseException {
        txtTraffic = findViewById(R.id.text_map);

        Log.d("Debugging", "in calculate velo");

        (new Thread(new Runnable() {
            private void isTrafficJam(double velocity) {
                boolean stress = false;
                if (velocity < 30) {
                    stress = true;
                } else {
                    stress = false;
                }

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

//                        JSONObject end_location = (JSONObject)legs.get("end_location");
//                        final String lat = (JSONObject)end_location.get("lat")+"";
//                        final String lng = (JSONObject)end_location.get("lng")+"";
//                        final String strLat = lat+"";
//
//                        Log.d(TAG, "run: Lat : "+lat);
//                        Log.d(TAG, "run: Lat : "+lng);
                        // calculate เพือหา v ในทุกๆ 5 นาที ทำอันนี้******** (ซึ่งตอนนี้เป็น1วิ)
                        Log.d("Debugging : Distance = ", distance + "");
                        Log.d("Debugging : Duration = ", duration + "");

                        final int t = 5;
                        final double s = distance;
                        final double v = s / t;

                        this.isTrafficJam(v);

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

