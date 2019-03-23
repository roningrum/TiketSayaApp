package com.digitalsoftware.tiketsayaapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TicketDetailAct extends AppCompatActivity {
    Button btnBuyTicket;
    LinearLayout btnBackHome;
    TextView titleTicket, locationTicket, photoSpotTicket,
            wifiTicket, festivalTicket, shortDescTicket;
    ImageView headerTicketDetail;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        btnBuyTicket = findViewById(R.id.btn_buyticket);
        btnBackHome = findViewById(R.id.btn_back);
        titleTicket = findViewById(R.id.title_ticket);
        locationTicket = findViewById(R.id.location_ticket);
        photoSpotTicket = findViewById(R.id.photo_spot_ticket);
        wifiTicket = findViewById(R.id.wifi_ticket);
        festivalTicket = findViewById(R.id.festival_ticket);
        shortDescTicket = findViewById(R.id.short_desc_ticket);
        headerTicketDetail = findViewById(R.id.header_ticket_detail);

        //mengambil data dari Intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");
        //mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data yang ada dengan data baru
                titleTicket.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                locationTicket.setText(dataSnapshot.child("lokasi").getValue().toString());
                photoSpotTicket.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                wifiTicket.setText(dataSnapshot.child("is_wifi").getValue().toString());
                festivalTicket.setText(dataSnapshot.child("is_festival").getValue().toString());
                shortDescTicket.setText(dataSnapshot.child("short_desc").getValue().toString());
                Picasso.get().load(dataSnapshot.child("url_thumbnail").getValue().toString()).noFade().into(headerTicketDetail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoCheckOut = new Intent(TicketDetailAct.this,TicketCheckOutAct.class);
                gotoCheckOut.putExtra("jenis_tiket", jenis_tiket_baru);
                startActivity(gotoCheckOut);
            }
        });

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

    }
}
