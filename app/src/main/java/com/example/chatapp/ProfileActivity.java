package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewUsername , textViewUserEmail;
    private Button buttonLogout;
    private Button buttonBackChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        textViewUsername = findViewById(R.id.textViewUsername);
        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());

        buttonLogout = findViewById(R.id.buttonLogout);
        buttonBackChat = findViewById(R.id.buttonBackChat);
        buttonBackChat.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
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
    }
}
