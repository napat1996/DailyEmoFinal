package com.kmutt.dailyemofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kmutt.dailyemofinal.Model.User;

public class EditProfileActivity extends AppCompatActivity {

    Button btnHome, btnProfile, btnResult, btnSuggesstion, btnSaveProfile;
    EditText inputUsername, inputPassword,inputConfPaswd, inputEmail, inputAge,inputWeight,inputHeight;
    FirebaseDatabase database;
    DatabaseReference mRootRef;
    String mUsername;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("DailyEmoPref", 0);
        String username = preferences.getString("username", "tk");

        String firebaseUrl = "https://dailyemo-194412.firebaseio.com/Users/" + username;
        Log.d("", "onCreate: debugging firebaseurl " + firebaseUrl);
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReferenceFromUrl(firebaseUrl);

        inputUsername = findViewById(R.id.edit_username);
        inputPassword = findViewById(R.id.edit_password);
//        inputConfPaswd = findViewById(R.id.edit_confirm_password);
        inputEmail = findViewById(R.id.edit_email);
        inputAge = findViewById(R.id.edit_age);
        inputHeight = findViewById(R.id.edit_high);
        inputWeight = findViewById(R.id.edit_weight);

        btnSaveProfile = findViewById(R.id.save_profile);
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(s);
            }
        });


    }
}
