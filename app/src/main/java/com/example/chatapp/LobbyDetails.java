package com.example.chatapp;

import android.util.Log;

public class LobbyDetails {


    private String mUsername;

    public LobbyDetails(String mUsername) {
        this.mUsername = mUsername;
        Log.e("mUsername",mUsername);
    }
    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }


}
