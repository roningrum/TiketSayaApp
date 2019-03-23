package com.digitalsoftware.tiketsayaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digitalsoftware.tiketsayaapp.adapter.MyTicketAdapter;
import com.digitalsoftware.tiketsayaapp.item.MyTicket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileAct extends AppCompatActivity {
    LinearLayout itemCheckMyTicket;
    Button editProfile, backHome, btnSignOut;

    TextView nama_lengkap, bio;
    CircleImageView photo_profile;

    DatabaseReference profileReference, placeReferece;

    String USERNAME_KEY = "usernamekey";
    String username_key = " ";
    String username_key_new = " ";

    RecyclerView myTicket_place;
    ArrayList<MyTicket> ticketsList;
    MyTicketAdapter ticketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getUseramaeLocal();

        itemCheckMyTicket = findViewById(R.id.item_my_ticket);
        editProfile = findViewById(R.id.btn_edit_profile);
        backHome = findViewById(R.id.btn_back_home);
        btnSignOut = findViewById(R.id.btn_sign_out);

        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        photo_profile = findViewById(R.id.photo_profile);

        myTicket_place = findViewById(R.id.my_ticket_list);
        myTicket_place.setLayoutManager(new LinearLayoutManager(this));
        ticketsList = new ArrayList<>();

        profileReference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        profileReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                Picasso.get().load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoMyTickets = new Intent(MyProfileAct.this, EditProfileAct.class);
                startActivity(gotoMyTickets);
            }
        });

        placeReferece = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new);
        placeReferece.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    MyTicket p = dataSnapshot1.getValue(MyTicket.class);
                    ticketsList.add(p);
                }
                ticketAdapter = new MyTicketAdapter(MyProfileAct.this,ticketsList);
                myTicket_place.setAdapter(ticketAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoHome = new Intent(MyProfileAct.this, HomeAct.class);
                startActivity(gotoHome);
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menghapus nilai / isi dari value user local
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, "");
                editor.apply();

                //berpindah activity
                Intent gotoHome = new Intent(MyProfileAct.this, SignInAct.class);
                startActivity(gotoHome);
                finish();
            }
        });
    }
    public void getUseramaeLocal(){
        SharedPreferences sharedPreferences= getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");

    }
}
