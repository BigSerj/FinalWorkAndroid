package com.android.bigserj.PlaceForm;



import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.util.Log;

import com.android.bigserj.PageSettingsAdapter;
import com.android.bigserj.SettingsHelper;
import com.android.bigserj.base.BaseViewModelFragment;

import static com.android.bigserj.Constants.*;
import static com.android.bigserj.SettingsHelper.*;
import static com.android.bigserj.SettingsHelper.STATE_PRESSURE.MILLIBARS;
import static com.android.bigserj.SettingsHelper.STATE_PRESSURE.MILLIMETERS_OF_MERCURY;
import static com.android.bigserj.SettingsHelper.STATE_TEMP.CELSIUS;
import static com.android.bigserj.SettingsHelper.STATE_TEMP.FAHRENHEIT;
import static com.android.bigserj.SettingsHelper.STATE_WIND.KM_PER_HOUR;
import static com.android.bigserj.SettingsHelper.STATE_WIND.M_PER_SEC;

public class PlaceFormSettingsViewModel implements BaseViewModelFragment {

    private PlaceFormSettingsFragment placeFormSettingsFragment;
    PlaceFormSettingsViewModel(PlaceFormSettingsFragment placeFormSettingsFragment){
        this.placeFormSettingsFragment = placeFormSettingsFragment;
    }

    SharedPreferences sharedPreferences;

    // регулируем цельсия/фаренгейт
    public static ObservableField<STATE_TEMP> state_temp =
            new ObservableField<>(tempCurrentState);

    // регулируем мм.рт.ст./мбар
    public static ObservableField<STATE_PRESSURE> state_pressure =
            new ObservableField<>(pressureCurrentState);

    // регулируем в чем скорость ветра
    public static ObservableField<STATE_WIND> state_wind =
            new ObservableField<>(windCurrentState);



    @Override
    public void create() {
    }

    @Override
    public void destroyView() {

    }

    @Override
    public void viewCreated() {
        getAllStates();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void activityCreated() {

    }









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



    // --------------------------------------------------// ОТРАБОТКА НАЖАТИЙ:
    public void setCelsius(){
        saveFirstPageSharedPreferences(SHAR_PREFS_STATE_TEMP, CELSIUS_STATE);
        state_temp.set(CELSIUS);
    }
    public void setFahrenheit(){
        saveFirstPageSharedPreferences(SHAR_PREFS_STATE_TEMP, FAHRENHEIT_STATE);
        state_temp.set(STATE_TEMP.FAHRENHEIT);
    }

    public void setPressureMMOfMercury(){
        saveFirstPageSharedPreferences(SHAR_PREFS_STATE_PRESSURE, MILLIMETERS_OF_MERCURY_STATE);
        state_pressure.set(STATE_PRESSURE.MILLIMETERS_OF_MERCURY);
    }
    public void setPressureMilliBars(){
        saveFirstPageSharedPreferences(SHAR_PREFS_STATE_PRESSURE, MLLIBARS_STATE);
        state_pressure.set(STATE_PRESSURE.MILLIBARS);
    }

    public void setWindMetersPerSeconds(){
        saveFirstPageSharedPreferences(SHAR_PREFS_STATE_WIND, MPS_STATE);
        state_wind.set(STATE_WIND.M_PER_SEC);
    }
    public void setWindKilometersPerHour(){
        saveFirstPageSharedPreferences(SHAR_PREFS_STATE_WIND, KPH_STATE);
        state_wind.set(STATE_WIND.KM_PER_HOUR);
    }

    // --------------------------------------------------SAVE/READ SharedPrefs
    private String loadFirstPageSharedPreferences(String KEY_NAME){

        sharedPreferences = placeFormSettingsFragment.getActivity()
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

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


}
