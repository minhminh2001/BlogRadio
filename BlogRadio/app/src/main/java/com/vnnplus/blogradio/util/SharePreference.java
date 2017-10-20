package com.vnnplus.blogradio.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vnnplus.blogradio.model.DownloadObject;
import com.vnnplus.blogradio.model.Radio;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giangdv on 4/13/2017.
 */

public class SharePreference {
    static String MY_PREFS_NAME = "BLOGRADIO";
//    static String MY_
    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getDefaults(String key, Context context, String dv) {
        SharedPreferences editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return editor.getString(key, dv);
    }
    public static void setList(String key , ArrayList<Radio> lRadio, Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(lRadio);
        Log.e("jsondata",json);
        editor.putString(key, json);
        editor.commit();
    }
    public static ArrayList<Radio> getListRadio(String key , Context context){
        SharedPreferences editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        ArrayList<Radio> lRadio = new ArrayList<>();
        Gson gson = new Gson();
        String json = editor.getString(key ,"");
        if (!json.equals("")){
            Type type = new TypeToken<List<Radio>>(){}.getType();
            lRadio = gson.fromJson(json , type);
        }
        return lRadio;
    }

}
