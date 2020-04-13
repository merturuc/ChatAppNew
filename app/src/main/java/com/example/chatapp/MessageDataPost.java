package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSOutput;

import java.util.HashMap;
import java.util.Map;

public class MessageDataPost extends AppCompatActivity {



    public void Post(){
        final String id = SharedPrefManager.getInstance(this).getId();
        final String  message = "deneme";
        System.out.println(id+""+message);
         StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_DATAMESSAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"), Toast.LENGTH_LONG).show();
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
                }){
            @Override
            protected Map<String , String > getParams() throws AuthFailureError{
                Map<String , String > params = new HashMap<>();
                params.put("id",id);
                params.put("message",message);
                System.out.println(params);
                return params;

            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}
