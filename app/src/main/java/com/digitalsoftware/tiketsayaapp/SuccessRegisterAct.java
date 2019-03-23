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

public class SuccessRegisterAct extends AppCompatActivity {
    TextView appTitle, appSubTitle;
    ImageView iconSuccess;
    Button btnExplore;
    Animation app_splash, btt, ttb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);
        btnExplore = findViewById(R.id.btn_explore_now);
        appTitle = findViewById(R.id.app_title);
        iconSuccess = findViewById(R.id.icon_success);
        appSubTitle = findViewById(R.id.app_subtitle);

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        iconSuccess.startAnimation(app_splash);
        appSubTitle.startAnimation(ttb);
        appTitle.startAnimation(ttb);
        btnExplore.startAnimation(btt);



        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoHome = new Intent(SuccessRegisterAct.this, HomeAct.class);
                startActivity(gotoHome);
            }
        });
    }
}
