package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Files;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewUsername , textViewUserEmail , textViewUserID , textViewBio , textViewMessageCount ,textViewDate;
    private Button buttonLogout , buttonBackChat , buttonProfilUpdate;
    private CircleImageView imageViewProfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        System.out.println("Oncreate çalıştı");
        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewUserID = findViewById(R.id.textViewUserID);
        textViewDate = findViewById(R.id.textViewDate);
        textViewBio = findViewById(R.id.textViewBio);
        textViewMessageCount = findViewById(R.id.textViewMessageCount);

        imageViewProfil = findViewById(R.id.imageViewProfil);
        buttonBackChat = findViewById(R.id.buttonBackChat);
        buttonLogout = findViewById(R.id.buttonLogout);
       // buttonProfile = findViewById(R.id.buttonProfile);
        buttonProfilUpdate = findViewById(R.id.buttonProfilUpdate);
      //  buttonProfile.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        buttonProfilUpdate.setOnClickListener(this);
        buttonBackChat.setOnClickListener(this);
        System.out.println(SharedPrefManager.getInstance(this).getId());
       String id = SharedPrefManager.getInstance(this).getId();
       Profil(id);
    }
    public  void Profil(final String id) {


         StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_UPDATEPROFİL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            profilUp(
                                    jsonObject.getString("username"),
                                    jsonObject.getString("email"),
                                    jsonObject.getString("profile_bio"),
                                    jsonObject.getString("image_name"),
                                    jsonObject.getString("image_path"),
                                    jsonObject.getInt("mes_count"),
                                    jsonObject.getString("reg_date")
                            );
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void profilUp(String username, String email, String profile_bio ,String image_name ,String image_path ,  Integer mes_count ,String reg_date){
        String mesC = Integer.toString(mes_count);
        textViewUserEmail.setText(email);
      textViewUsername.setText(username);
      textViewMessageCount.setText(mesC);
      textViewBio.setText(profile_bio);
      textViewDate.setText(reg_date);
//      ProfileUserUpdate prof = new ProfileUserUpdate();
//      prof.oldProfil(username,email,profile_bio);

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
        if(v == buttonProfilUpdate){
            startActivity(new Intent(this,ProfileUserUpdate.class));
        }
       // if(v == buttonProfile){
         //  updateImage();
        //}
    }
}
