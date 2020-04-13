package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.nio.file.Files;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewUsername , textViewUserEmail , textViewUserID;
    private Button buttonLogout , buttonBackChat , buttonProfile;
    private CircleImageView imageViewProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewUserID = findViewById(R.id.textViewUserID);
        imageViewProfil = findViewById(R.id.imageViewProfil);
        buttonBackChat = findViewById(R.id.buttonBackChat);
        buttonLogout = findViewById(R.id.buttonLogout);
       // buttonProfile = findViewById(R.id.buttonProfile);
        textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());
        textViewUserID.setText(SharedPrefManager.getInstance(this).getId());
        imageViewProfil.setImageResource(R.drawable.profil);
      //  buttonProfile.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        buttonBackChat.setOnClickListener(this);
    }
    private void updateImage(){
        System.out.println("imageUpdate");
    }

    @Override
    public void onClick(View v) {
        if(v == buttonBackChat){
            startActivity(new Intent(this,ChatActivity.class));
        }
        if(v == buttonLogout){
            SharedPrefManager.getInstance(this).logout();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
       // if(v == buttonProfile){
         //  updateImage();
        //}
    }
}
