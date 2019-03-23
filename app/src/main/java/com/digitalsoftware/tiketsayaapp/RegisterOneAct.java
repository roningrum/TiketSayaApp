package com.digitalsoftware.tiketsayaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneAct extends AppCompatActivity {
    Button btnContinue;
    LinearLayout btnBack;
    EditText etUsername, etPassword, etEmailAddress;
    DatabaseReference reference;

    //username yang akan disimpan

    String USERNAME_KEY = "usernamekey";
    String username_key = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        btnBack = findViewById(R.id.btn_back);
        btnContinue = findViewById(R.id.btn_continue);
        etEmailAddress = findViewById(R.id.emailaddress);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        FirebaseApp.initializeApp(this);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //menyimpan data ke local storage (handphone)
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, etUsername.getText().toString());
                editor.apply();

//                Toast.makeText(getApplicationContext(), "Username "+ etUsername.getText().toString(), Toast.LENGTH_SHORT).show();

                //simpan database
                reference = FirebaseDatabase.getInstance().getReference();
                reference.child("Users").child(etUsername.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("username").setValue(etUsername.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(etPassword.getText().toString());
                        dataSnapshot.getRef().child("email_address").setValue(etEmailAddress.getText().toString());
                        dataSnapshot.getRef().child("user_balance").setValue(800);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //berpindah activity
                Intent gotoNextRegister = new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                startActivity(gotoNextRegister);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //simpan data username ke lokal
                Intent backToSignIn = new Intent(RegisterOneAct.this, SignInAct.class);
                startActivity(backToSignIn);
            }
        });

    }
}
