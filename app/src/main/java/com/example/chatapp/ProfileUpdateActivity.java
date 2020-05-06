package com.example.chatapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewUsername , textViewUserEmail , textViewUserID , textViewBio , textViewMessageCount ,textViewDate;
    private Button buttonLogout , buttonBackChat , buttonProfilUpdate , buttonImageChoose ;
    private CircleImageView imageViewProfil;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewUserID = findViewById(R.id.textViewUserID);
        textViewDate = findViewById(R.id.textViewDate);
        textViewBio = findViewById(R.id.textViewBio);
        textViewMessageCount = findViewById(R.id.textViewMessageCount);

        imageViewProfil = findViewById(R.id.imageViewProfil);
        buttonBackChat = findViewById(R.id.buttonBackChat);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonImageChoose = findViewById(R.id.buttonImageChoose);
        buttonProfilUpdate = findViewById(R.id.buttonProfilUpdate);

        buttonImageChoose.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        buttonProfilUpdate.setOnClickListener(this);
        buttonBackChat.setOnClickListener(this);



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
                            SharedPrefManager.getInstance(getApplicationContext())
                                    .profilData(
                                            jsonObject.getString("email"),
                                            jsonObject.getString("username"),
                                            jsonObject.getString("profile_bio")
                                    );

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
    public void profilUp(String username, String email, String profile_bio ,String image_name ,String image_path ,  Integer mes_count ,String reg_date){
        String mesC = Integer.toString(mes_count);
        textViewUserEmail.setText(email);
      textViewUsername.setText(username);
      textViewMessageCount.setText(mesC);
      textViewBio.setText(profile_bio);
        textViewDate.setText(reg_date);
      imageViewProfil.setImageBitmap(bitmap);
      new GetImageFromURL(imageViewProfil).execute(image_name);
    }

    private void updateImage(){
        System.out.println("imageUpdate");
    }
    private void ChooseFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            try {
               bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
               imageViewProfil.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
            String id = SharedPrefManager.getInstance(this).getId();
            UploadPicture(id,getStringImage(bitmap)); // id kontrol edilecek.!
        }
    }

    private void UploadPicture(final String id , final String profilImage) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploadingg..!");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("Tag",response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                Toast.makeText(ProfileUpdateActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(ProfileUpdateActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     progressDialog.dismiss();
                        Toast.makeText(ProfileUpdateActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                params.put("profilImage",profilImage);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
              String encodedImage =  Base64.encodeToString(imageByteArray, Base64.DEFAULT);
              return encodedImage;


    }

    @Override
    public void onClick(View v) {
        if(v == buttonImageChoose){
            ChooseFile();
        }
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
