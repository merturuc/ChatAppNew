package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewUsername , textViewUserEmail , textViewUserID;
    private Button buttonLogout;
    private Button buttonBackChat;
    private Button buttonProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        textViewUsername = findViewById(R.id.textViewUsername);
        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        textViewUserID = findViewById(R.id.textViewUserID);
        textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());
        textViewUserID.setText(SharedPrefManager.getInstance(this).getId());
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonBackChat = findViewById(R.id.buttonBackChat);
        buttonProfile = findViewById(R.id.buttonProfile);
        buttonProfile.setOnClickListener(this);
        buttonBackChat.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
    }
    private void updateImage(){
        System.out.println("Update");
    }


    @Override
    public void onClick(View v) {
        if (v == buttonLogout)
            SharedPrefManager.getInstance(this).logout();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        if (v == buttonBackChat){
            startActivity(new Intent(this, ChatActivity.class));
       }
        if(v == buttonProfile){
            updateImage();
        }
    }
}
