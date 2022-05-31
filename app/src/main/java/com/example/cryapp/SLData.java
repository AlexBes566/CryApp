package com.example.cryapp;

import android.content.Context;
import android.content.SharedPreferences;

class SLData {

    public static Context context;

    SLData (Context context){
        SLData.context = context;
    }

    public void save_data(String line, String value){
        SharedPreferences mPref = context.getSharedPreferences("data_source", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(line, value);
        editor.apply();
    }

    public String load_data(String line){
        SharedPreferences mPref = context.getSharedPreferences("data_source", Context.MODE_PRIVATE);
        return mPref.getString(line,"");
    }
}
