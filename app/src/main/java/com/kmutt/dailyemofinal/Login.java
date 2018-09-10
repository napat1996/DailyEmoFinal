package com.kmutt.dailyemofinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kmutt.dailyemofinal.Model.User;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private String TAG = Login.class.getSimpleName();
    Button loginButton ;
    Button createNewAccount;
    ProgressBar loginProgress;
    //DatabaseReference users;

    FirebaseDatabase database;
    DatabaseReference mRootRef, users;

    EditText inputEmail, inputPassword, inputUsername;
    Button btnLogin;
    TextView btnToSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference("Users");

       inputUsername = (EditText)findViewById(R.id.inputUsername);
       inputPassword = (EditText)findViewById(R.id.inputPassword) ;

        btnLogin = (Button)findViewById(R.id.log_in_button);
        btnToSignup = (TextView)findViewById(R.id.createNewAccount);

        //loginButton = (Button) findViewById(R.id.log_in_button);
       // loginProgress = findViewById(R.id.login_progress);
        //loginProgress.setVisibility(View.INVISIBLE);

        //createNewAccount = (Button) findViewById(R.id.createNewAccount);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loginProgress.setVisibility(View.VISIBLE);
                System.out.println(inputUsername.getText().toString());
                //btnLogin.setVisibility(View.INVISIBLE);
                logIn(inputUsername.getText().toString(),
                        inputPassword.getText().toString());
            }
        });

        // create new account
        btnToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("EIEI");
                Intent s = new Intent(getApplicationContext(), Signup.class);
                startActivity(s);

            }
        });


    }

    private void logIn(final String username, final String password) {
        System.out.println("Hi, I'm a logIn method!!");
        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // ไม่เข้าอันล่าง
                if(dataSnapshot.child(username).exists()){
                    if(!username.isEmpty()){
                        User login = dataSnapshot.child(username).getValue(User.class);
                        if (login.getPassword().equals(password)){
                            Toast.makeText(Login.this,"Success Login", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onDataChange: ++++++++++++ S U C C E S S   F U L L ++++++++++++");

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Login.this, "Password is wrong!", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onDataChange: !!!!!!!!!!! P A S S W O R D   W R O N G !!!!!!!!!!!!!");
                        }
                    }
                    else {
                        Toast.makeText(Login.this, "Username is Not Register!", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onDataChange: !!!!!!!!!!! U S E R N A M E   W R O N G !!!!!!!!!!!!!");
                    }
                }
                else
                    Log.e(TAG, "onDataChange: !!!!!!!!!!! S O M E T H I N G  W R O N G !!!!!!!!!!!!!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    

}
