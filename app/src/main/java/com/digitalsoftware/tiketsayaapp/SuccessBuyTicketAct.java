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

public class SuccessBuyTicketAct extends AppCompatActivity {
    Animation app_splash, btt, ttb;
    TextView appTitle, appSubTitle;
    ImageView iconSuccess;
    Button btnViewTicket, btnMyDashBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        btnViewTicket = findViewById(R.id.btn_view_ticket);
        btnMyDashBoard = findViewById(R.id.btn_view_dashboard);
        iconSuccess = findViewById(R.id.icon_success_buy_ticket);
        appSubTitle = findViewById(R.id.app_subtitle_success_buy_ticket);
        appTitle = findViewById(R.id.app_title_success_buy_ticket);

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        iconSuccess.startAnimation(app_splash);
        appSubTitle.startAnimation(ttb);
        appTitle.startAnimation(ttb);
        btnViewTicket.startAnimation(btt);
        btnMyDashBoard.startAnimation(btt);

        btnMyDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoHome = new Intent(SuccessBuyTicketAct.this, HomeAct.class);
                startActivity(gotoHome);
            }
        });
        btnViewTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoHome = new Intent(SuccessBuyTicketAct.this, MyProfileAct.class);
                startActivity(gotoHome);
            }
        });
    }
}
