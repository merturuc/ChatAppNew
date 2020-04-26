package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileUserUpdate extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername, editTextEmail, editTextBio;
    private Button buttonUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user_update);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextBio = findViewById(R.id.editTextBio);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(this);
        String username = SharedPrefManager.getInstance(this).getUsername();
        String email = SharedPrefManager.getInstance(this).getUserEmail();
        String bio = SharedPrefManager.getInstance(this).getbio();
        oldProfil(username,email,bio);
    }
    public void oldProfil(String username , String email , String bio){
        editTextUsername.setText(username);
        editTextEmail.setText(email);
        editTextBio.setText(bio);  //Profil update yapılırken eski veriler edittext yazdırılcak.
    }
    @Override
    public void onClick(View v) {
        if (v == buttonUpdate) {
            profileUserUpdate();
            startActivity(new Intent(this, ProfileUpdateActivity.class));
        }
    }

    public void profileUserUpdate() {
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String bio = editTextBio.getText().toString().trim();
        final String id = SharedPrefManager.getInstance(this).getId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_UPDATEUSERPROF,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", "onErrorResponse");
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("bio", bio);
                params.put("username", username);
                params.put("email", email);
                params.put("id", id);
                return params;

            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

}
