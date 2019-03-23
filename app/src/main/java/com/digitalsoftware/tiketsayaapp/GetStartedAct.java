package com.digitalsoftware.tiketsayaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStartedAct extends AppCompatActivity {
    Animation ttb, btt;
    ImageView emblemApp;
    TextView introApp;
    Button btnSignIn, btnCreateNewAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        btnSignIn = findViewById(R.id.btn_sign_in);
        emblemApp = findViewById(R.id.emblem_app);
        introApp = findViewById(R.id.intro_app);
        btnCreateNewAccount = findViewById(R.id.btn_new_account_create);

        emblemApp.startAnimation(btt);
        introApp.startAnimation(btt);
        btnSignIn.startAnimation(ttb);
        btnCreateNewAccount.startAnimation(ttb);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoSignIn = new Intent(GetStartedAct.this, SignInAct.class);
                startActivity( gotoSignIn);
            }
        });
        btnCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoRegisterOne = new Intent(GetStartedAct.this, RegisterOneAct.class);
                startActivity(gotoRegisterOne);
            }
        });
    }
}
