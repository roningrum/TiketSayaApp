package com.digitalsoftware.tiketsayaapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyTicketDetailAct extends AppCompatActivity {
    TextView namaWisataDetail, lokasiWisataDetail, dateWisataDetail, timeWisataDetail, ketentuanWisataDetail;
    LinearLayout btnBack;
    Button btnRefund;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket_detail);
        namaWisataDetail = findViewById(R.id.nama_wisata_details);
        lokasiWisataDetail = findViewById(R.id.lokasi_wisata_details);
        dateWisataDetail = findViewById(R.id.date_wisata_details);
        timeWisataDetail = findViewById(R.id.time_wisata_details);
        btnBack = findViewById(R.id.btn_back);
        btnRefund = findViewById(R.id.btn_refund_ticket);
        timeWisataDetail = findViewById(R.id.time_wisata_details);
        ketentuanWisataDetail = findViewById(R.id.ketentuan_wisata_details);

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String nama_wisata_baru = bundle.getString("nama_wisata");

        //mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(nama_wisata_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namaWisataDetail.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasiWisataDetail.setText(dataSnapshot.child("lokasi").getValue().toString());
                dateWisataDetail.setText(dataSnapshot.child("date_wisata").getValue().toString());
                timeWisataDetail.setText(dataSnapshot.child("time_wisata").getValue().toString());
                ketentuanWisataDetail.setText(dataSnapshot.child("ketentuan").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoProfile = new Intent(MyTicketDetailAct.this, MyProfileAct.class);
                startActivity(gotoProfile);
            }
        });
    }
}
