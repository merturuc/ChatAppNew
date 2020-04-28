package com.example.chatapp;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LobbyAdapter extends ArrayAdapter<LobbyDetails> {

    public LobbyAdapter(Activity context, ArrayList<LobbyDetails> lobbyDetails) {
        super(context, 0, lobbyDetails);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        LobbyDetails lobbyDetails = getItem(position);
        Log.e("position",lobbyDetails.getmUsername());
        TextView textViewUser = listItemView.findViewById(R.id.textViewLobbyUser);
        textViewUser.setText(lobbyDetails.getmUsername());

        return listItemView;
    }
}
