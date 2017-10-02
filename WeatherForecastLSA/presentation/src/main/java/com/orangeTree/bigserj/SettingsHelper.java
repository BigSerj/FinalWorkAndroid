package com.orangeTree.bigserj;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static com.orangeTree.bigserj.Constants.*;
import static com.orangeTree.bigserj.SettingsHelper.STATE_TEMP.*;
import static com.orangeTree.bigserj.SettingsHelper.STATE_PRESSURE.*;
import static com.orangeTree.bigserj.SettingsHelper.STATE_WIND.*;

public class SettingsHelper {

    private Context context;
    public SettingsHelper(Context context){
        this.context = context;
        init();
    }

    // хранение данных на устройстве
    private static SharedPreferences sharedPreferences;

    public enum STATE_TEMP {
        NONE, CELSIUS, FAHRENHEIT
    }

    public enum STATE_PRESSURE {
        NONE, MILLIBARS, MILLIMETERS_OF_MERCURY
    }

    public enum STATE_WIND {
        NONE, M_PER_HOUR, KM_PER_HOUR
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

    private void getAllStates() {
        getCelsius();
        getPressure();
        getWind();
    }

    private void getCelsius() {
        String readData = loadFirstPageSharedPreferences(SHAR_PREFS_STATE_TEMP);
        try {
            if (readData != null && readData.equals(CELSIUS_STATE))
                tempCurrentState = CELSIUS;
            else
                tempCurrentState = FAHRENHEIT;
        } catch (Exception e) {
            Log.e("Read error", "Read Shared Preferences error getCelsius()");
        }
        Log.e("Load","New settings has loaded succesfully "+readData);

    }
    private void getPressure() {
        String readData = loadFirstPageSharedPreferences(SHAR_PREFS_STATE_PRESSURE);
        try{
            if (readData != null && readData.equals(MILLIMETERS_OF_MERCURY_STATE))
                pressureCurrentState = MILLIMETERS_OF_MERCURY;
            else
                pressureCurrentState = MILLIBARS;
        }catch (Exception e){
            Log.e("Read error","Read Shared Preferences error getPressure()");
        }
        Log.e("Load","New settings has loaded succesfully "+readData);
    }
    private void getWind() {
        String readData = loadFirstPageSharedPreferences(SHAR_PREFS_STATE_WIND);
        try{
            if (readData != null && readData.equals(MPH_STATE))
                windCurrentState = M_PER_HOUR;
            else
                windCurrentState = KM_PER_HOUR;
        }catch (Exception e){
            Log.e("Read error","Read Shared Preferences error getWind()");
        }
        Log.e("Load","New settings has loaded succesfully "+readData);
    }






    // --------------------------------------------------SAVE/READ SharedPrefs
    private String loadFirstPageSharedPreferences(String KEY_NAME){

        sharedPreferences = context
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        try {
            return sharedPreferences.getString(KEY_NAME, null);
        }catch (Exception e){
            Log.e("Read error","Read Shared Preferences error by "+KEY_NAME);
        }
        return null;
    }

    public void saveFirstPageSharedPreferences(String KEY_NAME, String state){
        switch(KEY_NAME){
            case SHAR_PREFS_STATE_TEMP:
                if (state.equals(CELSIUS_STATE))
                    tempCurrentState = CELSIUS;
                else
                    tempCurrentState = FAHRENHEIT;
                break;
            case SHAR_PREFS_STATE_PRESSURE:
                if (state.equals(MILLIMETERS_OF_MERCURY_STATE))
                    pressureCurrentState = MILLIMETERS_OF_MERCURY;
                else
                    pressureCurrentState = MILLIBARS;
                break;
            case SHAR_PREFS_STATE_WIND:
                if (state.equals(MPH_STATE))
                    windCurrentState = M_PER_HOUR;
                else
                    windCurrentState = KM_PER_HOUR;
        }
        // сохраняем состояние первого пейджа на экране
        Log.e("Save","New settings has saved succesfully "+KEY_NAME);
        sharedPreferences.edit().putString(KEY_NAME,String.valueOf(state)).apply();
    }

// --------------------------------------------------




}
