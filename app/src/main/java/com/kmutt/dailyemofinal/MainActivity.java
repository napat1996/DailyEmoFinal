package com.kmutt.dailyemofinal;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kmutt.dailyemofinal.Model.Data;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.kmutt.dailyemofinal.Calendar;


public class MainActivity extends AppCompatActivity {

    private Thread repeatTaskThread;
    private String TAG = MainActivity.class.getSimpleName();


    BroadcastReceiver broadcastReceiver;

    private static final String API_PREFIX = "https://api.fitbit.com";

    EditText inputUsername, inputEmail, inputPassword, confirmPassword;
    Button btnRegister;
    DatabaseReference mRootRef, users;
    FirebaseDatabase database;

    private DrawerLayout dl;
    private ActionBarDrawerToggle toggle;

    Fragment[] bottomNavigationFragments;

    TrafficActivity trafficActivity = new TrafficActivity();

    private boolean isSterss = false;
    private boolean isJam = trafficActivity.isTrafficJam();
    private int heartRate =0, asSleep = 0;
    public String stressStr = "Normal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = findViewById(R.id.dl);
        toggle = new ActionBarDrawerToggle(this, dl, R.string.open, R.string.close);
        dl.addDrawerListener(toggle);
        NavigationView navigationView = findViewById(R.id.nnnv);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(navigationView);


        Fragment tempFragments[] = {new DhomeFragment(), new DgoingMapFragment(), new DdashbroadFragment()};
        bottomNavigationFragments = tempFragments;

        getSupportFragmentManager().beginTransaction().replace(R.id.flcontent, bottomNavigationFragments[0]).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        (new Thread(new Runnable() {
            @Override
            public void run() {
                try {
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

    public String isStress() throws IOException, ParseException {
        Data data = new Data();
        boolean isStress;

        int heartRate = data.getHeartRateValue();
        if( heartRate > 70){
            if(asSleep < 300){
                isStress = true;
                stressStr = "Stress";
            }
            else if(isJam == true) {
                isStress = true;
                stressStr = "Normal";
            }
            else {
                isStress = false;
                stressStr = "Normal";
            }

        }
        else {
            isStress = false;
            stressStr = "Normal";
        }
        return stressStr;
    }



    //nav bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =  new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = bottomNavigationFragments[0];
                    break;
                case R.id.nav_sleep:
                    selectedFragment = bottomNavigationFragments[1];
                    break;
                case R.id.nav_map:
                    selectedFragment = bottomNavigationFragments[2];
                    break;
                case R.id.nav_dash:
                    selectedFragment = bottomNavigationFragments[3];
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.flcontent, selectedFragment).commit();

            return true;

        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(toggle.onOptionsItemSelected(item)){
            return (true);
        }


        return super.onOptionsItemSelected(item);
    }




    public void selectItemDrawer (MenuItem menuItem){
        Fragment myFragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()){
            case R.id.reminder:
                fragmentClass = ReminderFragment.class;
                break;
            case R.id.setting:
                fragmentClass = SettingFragment.class;
                break;
            case R.id.home:
                fragmentClass = DhomeFragment.class;
                break;
            default:
                fragmentClass = MainActivity.class;
        }

        try  {
            myFragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flcontent,myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        dl.closeDrawers();
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }




}
