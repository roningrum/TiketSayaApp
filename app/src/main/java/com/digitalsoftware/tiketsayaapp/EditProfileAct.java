package com.digitalsoftware.tiketsayaapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfileAct extends AppCompatActivity {
    ImageView photoEditProfile;
    EditText etNamaProfile, etBioProfile, etUsernameProfile, etPasswordProfile, etEmailProfile;
    Button btnSaveProfile, btnAddNewPhoto;
    LinearLayout btnBack;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = " ";
    String username_key_new = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getUseramaeLocal();

        photoEditProfile = findViewById(R.id.photo_profile_edit);
        etNamaProfile = findViewById(R.id.et_nama_edit_profile);
        etBioProfile = findViewById(R.id.et_bio_edit_profile);
        etUsernameProfile = findViewById(R.id.et_username_edit_profile);
        etEmailProfile = findViewById(R.id.et_email_edit_profile);
        etPasswordProfile = findViewById(R.id.et_password_edit_profile);
        btnSaveProfile = findViewById(R.id.btn_save_profile);
        btnAddNewPhoto = findViewById(R.id.btn_add_new_photo);
        btnBack = findViewById(R.id.btn_back);


        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                etNamaProfile.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                etBioProfile.setText(dataSnapshot.child("bio").getValue().toString());
                etUsernameProfile.setText(dataSnapshot.child("username").getValue().toString());
                etPasswordProfile.setText(dataSnapshot.child("password").getValue().toString());
                etEmailProfile.setText(dataSnapshot.child("email_address").getValue().toString());
                Picasso.get().load(dataSnapshot.child("url_photo_profile").getValue().toString()).noFade().into(photoEditProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveProfile.setEnabled(false);
                btnSaveProfile.setText("Loading...");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nama_lengkap").setValue(etNamaProfile.getText().toString());
                        dataSnapshot.getRef().child("bio").setValue(etBioProfile.getText().toString());
                        dataSnapshot.getRef().child("username").setValue(etUsernameProfile.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(etPasswordProfile.getText().toString());
                        dataSnapshot.getRef().child("email_address").setValue(etEmailProfile.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //validasi apakah file udah diupload apa belum
                if (photo_location != null) {
                    StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));
                    storageReference1.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String uri_photo = uri.toString();
                                    reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                }
                            });

                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            //pindah ke Profile
                            Intent gotoProfile = new Intent(EditProfileAct.this, MyProfileAct.class);
                            startActivity(gotoProfile);

                        }
                    });
                }

            }
        });
        btnAddNewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto() {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null) {
            photo_location = data.getData();
            Picasso.get().load(photo_location).centerCrop().fit().into(photoEditProfile);
        }
    }

    public void getUseramaeLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
