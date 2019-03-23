package com.digitalsoftware.tiketsayaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SignInAct extends AppCompatActivity {
    TextView btnNewAccount;
    Button btnSignIn;
    EditText etUsername, etPassword;
    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btnNewAccount = findViewById(R.id.btn_new_account);
        btnSignIn = findViewById(R.id.btn_sign_in);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        btnNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoRegisterOne = new Intent(SignInAct.this, RegisterOneAct.class);
                startActivity(gotoRegisterOne);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ubah state menjadi Loading
                btnSignIn.setEnabled(false);
                btnSignIn.setText("Loading...");

                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                if (username.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Username kosong", Toast.LENGTH_SHORT).show();
                    btnSignIn.setEnabled(true);
                    btnSignIn.setText("SIGN IN");
                }
                else{
                    if(password.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password kosong", Toast.LENGTH_SHORT).show();
                        btnSignIn.setEnabled(true);
                        btnSignIn.setText("SIGN IN");
                    }
                    else{
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    //ambil data password
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    //validasi password dengan password di edit text
                                    if (password.equals(passwordFromFirebase)) {
                                        //menyimpan data ke local storage (handphone)
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, etUsername.getText().toString());
                                        editor.apply();
                                        //berpindah activity
                                        Intent gotoHome = new Intent(SignInAct.this, HomeAct.class);
                                        startActivity(gotoHome);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Pasword tidak cocok", Toast.LENGTH_SHORT).show();
                                        btnSignIn.setEnabled(true);
                                        btnSignIn.setText("SIGN IN");
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "Username tidak terdaftar", Toast.LENGTH_SHORT).show();
                                    btnSignIn.setEnabled(true);
                                    btnSignIn.setText("SIGN IN");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }
            }
        });
    }
}
