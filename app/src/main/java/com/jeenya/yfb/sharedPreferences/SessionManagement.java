package com.jeenya.yfb.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by hp on 7/9/2015.
 */
public class SessionManagement {

    public static final String DEFAULT = "N/A";

    public static void SavePersonalInfo(Context context, String name, String phone, String bGroup) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("personal", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("phone", phone);
        editor.putString("blood", bGroup);
        editor.commit();
    }


    public static HashMap<String, String> GetPersonalInfo(Context context) {
        HashMap<String, String> hm = new HashMap<String, String>();
        //  ArrayList<String> a = new ArrayList<String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("personal", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", DEFAULT);
        String ph = sharedPreferences.getString("phone", DEFAULT);
        String bloodGroup = sharedPreferences.getString("blood", DEFAULT);
        //a.add(name);
        // a.add(ph);
        // a.add(bloodGroup);
        hm.put("name", name);
        hm.put("phone", ph);
        hm.put("blood", bloodGroup);

        return hm;
    }


}
