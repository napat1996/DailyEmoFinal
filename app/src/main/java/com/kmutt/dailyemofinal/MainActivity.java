package com.kmutt.dailyemofinal;

import android.content.Context;
import android.net.Network;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.kmutt.dailyemofinal.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText inputUsername, inputEmail, inputPassword, confirmPassword;
    Button btnRegister;
    DatabaseReference mRootRef, users;
    FirebaseDatabase database;

    private DrawerLayout dl;
    private ActionBarDrawerToggle toggle;

    Fragment[] bottomNavigationFragments;

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


        Fragment tempFragments[] = {new DhomeFragment(), new DsleepFragment(), new DgoingMapFragment(), new DdashbroadFragment()};
        bottomNavigationFragments = tempFragments;

        getSupportFragmentManager().beginTransaction().replace(R.id.flcontent, bottomNavigationFragments[0]).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

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
