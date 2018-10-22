package com.kmutt.dailyemofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kmutt.dailyemofinal.Model.User;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private String TAG = SignupActivity.class.getSimpleName();
    EditText inputUsername, inputEmail, inputPassword, confirmpassword, inputAge, inputWeight, inputHeight, inputBD, inputSex;
    Button btnRegister;
    DatabaseReference mRootRef, username;
    FirebaseDatabase database;
    String mUsername;

    private FirebaseAuthException auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Firebase
        database = FirebaseDatabase.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference("Users");


        btnRegister = (Button)findViewById(R.id.sign_in_button);
        inputEmail = (EditText)findViewById(R.id.inputEmail);
        inputPassword = (EditText)findViewById(R.id.inputPassword);
        inputUsername = (EditText) findViewById(R.id.inputUsername);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dailyemo-194412.firebaseio.com/Users");
                final User user = new User(inputUsername.getText().toString(),
                        inputPassword.getText().toString(),
                        inputEmail.getText().toString(),
                        inputSex.getText().toString(),
                        inputHeight.getText().toString(),
                        inputBD.getText().toString(),
                        inputAge.getText(),
                        inputBD.getText().toString(),
                        inputWeight.getText()
                        );
                mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUsername()).exists())
                            Toast.makeText(SignupActivity.this, "The Username is Already Exist!", Toast.LENGTH_SHORT).show();
                        else {
                            mRootRef.child(user.getUsername()).setValue(user);
                            Toast.makeText(SignupActivity.this, "Success Register!", Toast.LENGTH_SHORT).show();

                            mUsername = inputUsername.toString();
                            SharedPreferences preferences = getApplicationContext().getSharedPreferences("DailyEmoPref", 0);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("username", mUsername);
                            editor.commit();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled: !!!!!!!!!!!!N O T   W O R K K K K!!!!!!!!" );
                    }
                });
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
