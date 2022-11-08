package com.kyarlay.ayesunaing.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

/**
 * Created by ayesunaing on 9/2/16.
 */
public class MyPreference implements ConstantVariable{

    SharedPreferences prefs;
    Activity activity;

   /* public MyPreference(Activity activity) {
        this.activity = activity;
        if(prefs == null) {
           prefs =  activity.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        }
    }*/

    public MyPreference(Activity activity){
        try {
            MasterKey masterKey =  new MasterKey.Builder(activity)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            prefs = EncryptedSharedPreferences.create(
                    activity,
                    SP_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            this.activity = activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isContains(String name){
        boolean check = false;
        if(prefs.contains(name))
            return true;
        return check;
    }

    public void clearAll(){
        prefs.edit().clear().apply();
    }
    public void saveStringPreferences(String name, String value){
        prefs.edit().putString(name, value).apply();
    }
    public void saveIntPerferences(String name, int value){
        prefs.edit().putInt(name, value).apply();
    }

    public void saveFloatPerferences(String name, float value){
        prefs.edit().putFloat(name, value).apply();
    }

    public  float getFloatPreferences(String name){
        return prefs.getFloat(name, 0f);
    }



    public  int getIntPreferences(String name){
        int result;
        result = prefs.getInt(name, 0);
        return result;
    }

    public String getStringPreferences(String name){
        return prefs.getString(name, "");
    }

    public boolean getBooleanPreference(String name){
        return prefs.getBoolean(name, false);
    }

    public void saveBooleanPreference(String name, boolean value){
        prefs.edit().putBoolean(name, value).apply();
    }

    public void remove(String name){
        prefs.edit().remove(name).apply();
    }

    public  boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }



}
