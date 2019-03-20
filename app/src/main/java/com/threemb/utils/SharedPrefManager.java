package com.threemb.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Amol on 6/21/2015.
 */
public class SharedPrefManager {

    public static final String MY_PREFERENCES = "MyPrefs";
 /*   public static final String CURRENT_ADDRESS ="current_address" ;
    public static final String CURRENT_LATITUDE ="latitude" ;
    public static final String CURRENT_LONGITUDE ="longitude" ;
    public static final String CURRENT_ALTITUDE = "altitude";*/
    public static final String SAVE_INGEST_DURATION = "ingest_duration";
    public static final String SAVE_GPS_DURATION = "gps_duration";
    public static final String PROJECT_ID = "project_id";
    public static final String SEGEMENT_ROW = "segement_id";
    public static final String FIRSTRUN = "firstrun";
    private static final String CUSTOMER_ID = "customer_id";
    public static String USER_NAME = "user_name";
    public static String USER_ID = "user_id";
    public static String JOB_ID= "job_id";
    public static String PASSWORD = "pasword";
    public static String USER_KEY = "user_key";
    public static String iSLOGGED_IN = "hasLoggedIn";



    public static void saveUserCredentials(Context context, String userKey, String userName, String password,String userid) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_KEY, userKey).putString(USER_NAME, userName).putString(PASSWORD, password).putString(USER_ID,userid);
        editor.commit();
    }

    public static void saveValue(Context context, String key, Object value) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        }
        editor.commit();
    }

    public static String getStringValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    public static int getIntValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, 0);
    }

    public static float getFloatValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getFloat(key, 0);
    }

    public static boolean shouldWeAsk(Context context,String permission){
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        return (sharedPref.getBoolean(permission, true));
    }

    public static void markAsAsked(Context context,String permission){
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        sharedPref.edit().putBoolean(permission, false).apply();
    }

    public static boolean getBooleanValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }

    public static long getLongValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getLong(key, 0);
    }
}
