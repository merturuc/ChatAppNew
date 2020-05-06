package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LobbyActivity extends AppCompatActivity implements View.OnClickListener {
        ListView listView;
        List<LobbyDetails> lobbyList;
        Button buttonBackChat,buttonProfile,buttonLogout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lobby);
            String id = SharedPrefManager.getInstance(this).getId();
            getLobbyUser(id);
            listView = findViewById(R.id.listViewUsername);
            lobbyList = new ArrayList<>();
            buttonBackChat = findViewById(R.id.buttonBackChat);
            buttonLogout = findViewById(R.id.buttonLogout);
            buttonProfile = findViewById(R.id.buttonProfile);

            buttonProfile.setOnClickListener(this);
            buttonLogout.setOnClickListener(this);
            buttonBackChat.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
      if(v == buttonProfile){
          startActivity(new Intent(this, ProfileUpdateActivity.class));
      }
      if(v == buttonLogout){
          SharedPrefManager.getInstance(this).logout();
          finish();
          startActivity(new Intent(this, LoginActivity.class));
        }
      if(v == buttonBackChat){
          startActivity(new Intent(this,ChatActivity.class));
        }
    }

    public void getLobbyUser(final String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_LobbyUser,


                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonarray = new JSONArray(response);
                                Log.e("Response",response);
                                for(int i=0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    LobbyDetails p = new LobbyDetails(jsonobject.getString("username"));
                                    lobbyList.add(p);

                            }
                                LobbyAdappter adapter = new LobbyAdappter(lobbyList,getApplicationContext());
                                listView.setAdapter(adapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Log.e("ErrorListener","Problem Loading JSON");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }



}
