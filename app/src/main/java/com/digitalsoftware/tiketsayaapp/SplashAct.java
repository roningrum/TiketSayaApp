package com.digitalsoftware.tiketsayaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashAct extends AppCompatActivity {
    Animation app_splash, app_splash_title;
    ImageView app_logo;
    TextView app_subtitle;

    String USERNAME_KEY = "usernamekey";
    String username_key = " ";
    String username_key_new = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //load aimasi spaslh screen
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        app_splash_title= AnimationUtils.loadAnimation(this, R.anim.btt);
        app_logo = findViewById(R.id.app_logo);
        app_subtitle = findViewById(R.id.app_subtitle);

        //run animasi
        app_logo.startAnimation(app_splash);
        app_subtitle.startAnimation(app_splash_title);

        //setting waktu animasi
        getUseramaeLocal();

    }
    public void getUseramaeLocal(){
        SharedPreferences sharedPreferences= getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
        if(username_key_new.isEmpty()){
            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //merubah activity ke activity lain
                    Intent intent = new Intent(SplashAct.this, GetStartedAct.class);
                    startActivity(intent);
                    finish();
                }
            },2000);
        }
        else{
            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //merubah activity ke activity lain
                    Intent intent = new Intent(SplashAct.this, HomeAct.class);
                    startActivity(intent);
                    finish();
                }
            },2000);

        }
    }
}
