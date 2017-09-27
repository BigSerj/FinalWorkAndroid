package com.android.bigserj;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static com.android.bigserj.Constants.*;
import static com.android.bigserj.SettingsHelper.STATE_TEMP.*;
import static com.android.bigserj.SettingsHelper.STATE_PRESSURE.*;
import static com.android.bigserj.SettingsHelper.STATE_WIND.*;

public class SettingsHelper {

    Context context;
    public SettingsHelper(Context context){
        this.context = context;
        init();
    }

    // хранение данных на устройстве
    public SharedPreferences sharedPreferences;

    public enum STATE_TEMP {
        NONE, CELSIUS, FAHRENHEIT
    }

    public enum STATE_PRESSURE {
        NONE, MILLIBARS, MILLIMETERS_OF_MERCURY
    }

    public enum STATE_WIND {
        NONE, M_PER_SEC, KM_PER_HOUR
    }

    public static STATE_TEMP tempCurrentState;
    public static STATE_PRESSURE pressureCurrentState;
    public static STATE_WIND windCurrentState;


    private void init(){
        // если еще не загружали - загружаем бд
        if (tempCurrentState==null || pressureCurrentState == null || windCurrentState==null)
            getAllStates();
    }



// --------------------------------------------------// СЧИТЫВАНИЕ СОСТОЯНИЙ НАСТРОЕК

    public void getAllStates() {
        getCelsius();
        getPressure();
        getWind();
    }

    private void getCelsius() {
        String readData = loadFirstPageSharedPreferences(SHAR_PREFS_STATE_TEMP);
        try {
            if (readData.equals(CELSIUS_STATE))
                tempCurrentState = CELSIUS;
            else
                tempCurrentState = FAHRENHEIT;
        } catch (Exception e) {
            Log.e("Read error", "Read Shared Preferences error getCelsius()");
        }
    }
    private void getPressure() {
        String readData = loadFirstPageSharedPreferences(SHAR_PREFS_STATE_PRESSURE);
        try{
            if (readData.equals(MILLIMETERS_OF_MERCURY_STATE))
                pressureCurrentState = MILLIMETERS_OF_MERCURY;
            else
                pressureCurrentState = MILLIBARS;
        }catch (Exception e){
            Log.e("Read error","Read Shared Preferences error getPressure()");
        }
    }
    private void getWind() {
        String readData = loadFirstPageSharedPreferences(SHAR_PREFS_STATE_WIND);
        try{
            if (readData.equals(MPS_STATE))
                windCurrentState = M_PER_SEC;
            else
                windCurrentState = KM_PER_HOUR;
        }catch (Exception e){
            Log.e("Read error","Read Shared Preferences error getWind()");
        }
    }






    // --------------------------------------------------SAVE/READ SharedPrefs
    private String loadFirstPageSharedPreferences(String KEY_NAME){

        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        String theFirstPageString = null;
        try {
            theFirstPageString = sharedPreferences.getString(KEY_NAME, null);
        }catch (Exception e){
            Log.e("Read error","Read Shared Preferences error by "+KEY_NAME);
        }
        return theFirstPageString;
    }
    private void saveFirstPageSharedPreferences(String KEY_NAME, String state){
        // сохраняем состояние первого пейджа на экране
        sharedPreferences.edit().putString(KEY_NAME,String.valueOf(state))
                .apply();
    }

// --------------------------------------------------




}
