package com.example.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class LobbyAdappter extends ArrayAdapter<LobbyDetails> {

    private List<LobbyDetails> lobbydetailsList;
    private Context ctx;
    public LobbyAdappter(List<LobbyDetails> P,Context c) {
        super(c, R.layout.list_item,P);
        this.lobbydetailsList = P;
        this.ctx = c;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.list_item,null,true);

        TextView username = view.findViewById(R.id.textViewLobbyUser);

        LobbyDetails lobbyDetails = lobbydetailsList.get(position);
        username.setText(lobbyDetails.getmUsername());

        return view;
    }
}
