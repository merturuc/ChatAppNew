package com.example.chatapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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


public class LobbyActivity extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lobby);
            String id = SharedPrefManager.getInstance(this).getId();
            getLobbyUser(id);
            ListView lobbyList = findViewById(R.id.listViewUsername);

            ArrayList<LobbyDetails> lobbyDetails = new ArrayList<>();
            LobbyAdapter adapter = new LobbyAdapter(this,lobbyDetails);
            lobbyList.setAdapter(adapter);


    }

    public void getLobbyUser(final String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_LobbyUser,


                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<LobbyDetails> lobby = new ArrayList<>();
                        try {
                            JSONArray jsonarray = new JSONArray(response);
                                Log.e("Response",response);
                                for(int i=0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String username       = jsonobject.getString("username");
                                    Log.e("username",username);
                                    LobbyDetails lobbyDetails = new LobbyDetails(username);
                                    lobby.add(lobbyDetails);

                            }



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
