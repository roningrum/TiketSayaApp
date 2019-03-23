package com.digitalsoftware.tiketsayaapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterTwoAct extends AppCompatActivity {
    LinearLayout btnBack;
    Button btnContinue,btnAddPhoto;
    CircleImageView pic_photo_profile;
    EditText etName, etBio;

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
        setContentView(R.layout.activity_register_two);

        getUseramaeLocal();

        btnBack = findViewById(R.id.btn_back);
        btnContinue = findViewById(R.id.btn_continue);
        btnAddPhoto = findViewById(R.id.btn_upload_photo);
        btnContinue = findViewById(R.id.btn_continue);
        pic_photo_profile = findViewById(R.id.pic_photo_profile);
        etName = findViewById(R.id.et_nama_lengkap);
        etBio = findViewById(R.id.et_bio);


        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ubah state menjadi Loading
                btnContinue.setEnabled(false);
                btnContinue.setText("Loading...");
                //menyimpan ke database
                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);

                //validasi apakah file udah diupload apa belum
                if(photo_location !=null){
                    StorageReference storageReference1 = storage.child(System.currentTimeMillis() +"."+ getFileExtension(photo_location));
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
                            reference.getRef().child("nama_lengkap").setValue(etName.getText().toString());
                            reference.getRef().child("bio").setValue(etBio.getText().toString());

                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            //Berpindah Activaty
                            Intent gotoSuccess = new Intent(RegisterTwoAct.this, SuccessRegisterAct.class);
                            startActivity(gotoSuccess);
                        }
                    });
                }


            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToPrev= new Intent(RegisterTwoAct.this, RegisterOneAct.class);
                startActivity(backToPrev);
            }
        });
    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( contentResolver.getType(uri));
    }
    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == photo_max && resultCode == RESULT_OK && data !=null && data.getData()!=null){
            photo_location = data.getData();
            Picasso.get().load(photo_location).centerCrop().fit().into(pic_photo_profile);
        }
    }

    //buat method untuk ambil ilai username di halaman sebelumny
    public void getUseramaeLocal(){
        SharedPreferences sharedPreferences= getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
