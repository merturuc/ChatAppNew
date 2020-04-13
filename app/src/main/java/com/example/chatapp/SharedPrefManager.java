package com.example.chatapp;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.preference.PreferenceManager;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.toolbox.Volley;

public class SharedPrefManager {
    // Önbellekte verileri tutmak için
    private static SharedPrefManager instance;
    private static Context ctx;
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_EMAIL = "useremail";
    private static final String KEY_USER_ID = "userid";

    private SharedPrefManager(Context context) {
        ctx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

        public boolean userLogin(int id, String username, String email) {
            SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USERNAME, username);

        editor.apply();

        return true;
    }
       public boolean ruserLogin(){
           SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
           SharedPreferences.Editor editor = sharedPreferences.edit();


           editor.putString(KEY_USER_EMAIL, "");
           editor.putString(KEY_USERNAME, "");

           editor.apply();
        return  true;
       }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        System.out.println("Giriş yapıldı1");
        if (sharedPreferences.getString(KEY_USERNAME, null) != null) {
            return true;
        }
        return false;
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        System.out.println("Çıkış yapıldı");
        editor.clear();
        editor.apply();
        return true;
    }
    public String getUsername(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }
    public String  getUserEmail(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    public String getId() {
       SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
       return Integer.toString(sharedPreferences.getInt(KEY_USER_ID,0));

    }
    public Integer getId2(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID,0);
    }
}
