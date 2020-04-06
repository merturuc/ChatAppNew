package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonProfile;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

      if(!SharedPrefManager.getInstance(this).isLoggedIn()){
           finish();
           startActivity(new Intent(this , LoginActivity.class));
            return;
       }

        buttonProfile = findViewById(R.id.buttonProfile);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonProfile.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.menuLogout:
//                SharedPrefManager.getInstance(this).logout();
//                finish();
//                startActivity(new Intent(this, LoginActivity.class));
//                break;
//            case R.id.menuSettings:
//                startActivity(new Intent(this, ProfileActivity.class));
//                break;
//        }
//        return true;
//
//    }

    @Override
    public void onClick(View v) {
        if (v == buttonProfile)
            startActivity(new Intent(this, ProfileActivity.class));
        if (v == buttonLogout) {
            SharedPrefManager.getInstance(this).logout();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
    }

