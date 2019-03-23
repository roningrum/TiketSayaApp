package com.digitalsoftware.tiketsayaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAct extends AppCompatActivity {
    LinearLayout btn_ticket_pisa, btn_ticket_pagoda, btn_ticket_torri, btn_ticket_candi, btn_ticket_sphinx, btn_ticket_monas;
    CircleImageView btn_to_profile;
    TextView userBalance, namaLengkap, bio;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = " ";
    String username_key_new = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getUseramaeLocal();

        btn_ticket_pisa = findViewById(R.id.btn_ticket_pisa);
        btn_ticket_torri = findViewById(R.id.btn_ticket_torri);
        btn_ticket_pagoda = findViewById(R.id.btn_ticket_pagoda);
        btn_ticket_candi = findViewById(R.id.btn_ticket_candi);
        btn_ticket_sphinx = findViewById(R.id.btn_ticket_sphinx);
        btn_ticket_monas = findViewById(R.id.btn_ticket_monas);

        btn_to_profile = findViewById(R.id.btn_img_profile);
        userBalance = findViewById(R.id.home_user_balance);
        namaLengkap = findViewById(R.id.home_name_profile);
        bio = findViewById(R.id.home_bio_profile);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namaLengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                userBalance.setText("US$" + dataSnapshot.child("user_balance").getValue().toString());
                Picasso.get().load(dataSnapshot.child("url_photo_profile").getValue().toString()).noFade().into(btn_to_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoProfile = new Intent(HomeAct.this, MyProfileAct.class);
                startActivity(gotoProfile);
            }
        });

        btn_ticket_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoPisaTicket = new Intent(HomeAct.this, TicketDetailAct.class);
                gotoPisaTicket.putExtra("jenis_tiket", "Pisa");
                startActivity(gotoPisaTicket);
            }
        });
        btn_ticket_torri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoPisaTicket = new Intent(HomeAct.this, TicketDetailAct.class);
                gotoPisaTicket.putExtra("jenis_tiket", "Torri");
                startActivity(gotoPisaTicket);
            }
        });
        btn_ticket_pagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoPisaTicket = new Intent(HomeAct.this, TicketDetailAct.class);
                gotoPisaTicket.putExtra("jenis_tiket", "Pagoda");
                startActivity(gotoPisaTicket);
            }
        });
        btn_ticket_candi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoPisaTicket = new Intent(HomeAct.this, TicketDetailAct.class);
                gotoPisaTicket.putExtra("jenis_tiket", "Candi");
                startActivity(gotoPisaTicket);
            }
        });
        btn_ticket_sphinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoPisaTicket = new Intent(HomeAct.this, TicketDetailAct.class);
                gotoPisaTicket.putExtra("jenis_tiket", "Sphinx");
                startActivity(gotoPisaTicket);
            }
        });
        btn_ticket_monas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoPisaTicket = new Intent(HomeAct.this, TicketDetailAct.class);
                gotoPisaTicket.putExtra("jenis_tiket", "Monas");
                startActivity(gotoPisaTicket);
            }
        });

    }

    //buat method untuk ambil ilai username di halaman sebelumny
    public void getUseramaeLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
