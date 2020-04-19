package com.example.chatapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSOutput;

import java.util.HashMap;
import java.util.Map;

public class MessageDataPost  {
    Activity ctx;
public MessageDataPost(Activity context) {
        ctx = context;

    }

    public void  MessageData(final String message ,final String id){

         StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_DATAMESSAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                           JSONObject jsonObject = new JSONObject(response);


                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Errorlisner");
                    }
                }){
            @Override
            protected Map<String , String > getParams() throws AuthFailureError{
                Map<String , String > params = new HashMap<>();
                params.put("message",message);
                params.put("id",id);
                System.out.println(params);
                return params;

            }
        };
        RequestHandler.getInstance(ctx).addToRequestQueue(stringRequest);
    }
}
