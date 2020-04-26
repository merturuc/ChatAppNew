package com.example.chatapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class GetImageFromURL extends AsyncTask<String, Void, Bitmap> {

    ImageView imgV;
    Bitmap bitmap;
    public GetImageFromURL(ImageView imgV){
        this.imgV = imgV;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        String urldisplay = url[0];
        bitmap = null;
        try{
            InputStream srt = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(srt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imgV.setImageBitmap(bitmap);
    }
}
