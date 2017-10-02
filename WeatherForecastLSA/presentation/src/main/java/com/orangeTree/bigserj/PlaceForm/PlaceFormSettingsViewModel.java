package com.orangeTree.bigserj.PlaceForm;


import android.databinding.ObservableField;
import com.orangeTree.bigserj.SettingsHelper;
import com.orangeTree.bigserj.base.BaseViewModelFragment;
import com.orangeTree.bigserj.Constants;

public class PlaceFormSettingsViewModel implements BaseViewModelFragment {

    private PlaceFormSettingsFragment placeFormSettingsFragment;
    private PlaceFormActivity placeFormActivity;

    PlaceFormSettingsViewModel(PlaceFormSettingsFragment placeFormSettingsFragment){
        this.placeFormSettingsFragment = placeFormSettingsFragment;
        placeFormActivity = ((PlaceFormActivity)placeFormSettingsFragment.getActivity());
    }
    private SettingsHelper settingsHelper;


    // регулируем цельсия/фаренгейт
    public ObservableField<SettingsHelper.STATE_TEMP> state_temp =
            new ObservableField<>(SettingsHelper.STATE_TEMP.NONE);

    // регулируем мм.рт.ст./мбар
    public ObservableField<SettingsHelper.STATE_PRESSURE> state_pressure =
            new ObservableField<>(SettingsHelper.STATE_PRESSURE.NONE);

    // регулируем в чем скорость ветра
    public ObservableField<SettingsHelper.STATE_WIND> state_wind =
            new ObservableField<>(SettingsHelper.STATE_WIND.NONE);



    @Override
    public void create() {
        settingsHelper = new SettingsHelper(placeFormSettingsFragment.getContext());
        state_temp.set(SettingsHelper.tempCurrentState);
        state_pressure.set(SettingsHelper.pressureCurrentState);
        state_wind.set(SettingsHelper.windCurrentState);
    }

    @Override
    public void destroyView() {

    }

    @Override
    public void viewCreated() {

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










    // --------------------------------------------------// ОТРАБОТКА НАЖАТИЙ:
    public void setCelsius(){
        settingsHelper.saveFirstPageSharedPreferences(Constants.SHAR_PREFS_STATE_TEMP, Constants.CELSIUS_STATE);
        state_temp.set(SettingsHelper.STATE_TEMP.CELSIUS);
        placeFormActivity.setCelsius();
    }
    public void setFahrenheit(){
        settingsHelper.saveFirstPageSharedPreferences(Constants.SHAR_PREFS_STATE_TEMP, Constants.FAHRENHEIT_STATE);
        state_temp.set(SettingsHelper.STATE_TEMP.FAHRENHEIT);
        placeFormActivity.setFahrenheit();
    }

    public void setPressureMMOfMercury(){
        settingsHelper.saveFirstPageSharedPreferences(Constants.SHAR_PREFS_STATE_PRESSURE,
                Constants.MILLIMETERS_OF_MERCURY_STATE);
        state_pressure.set(SettingsHelper.STATE_PRESSURE.MILLIMETERS_OF_MERCURY);
        placeFormActivity.setPressureMMOfMercury();
    }
    public void setPressureMilliBars(){
        settingsHelper.saveFirstPageSharedPreferences(Constants.SHAR_PREFS_STATE_PRESSURE, Constants.MLLIBARS_STATE);
        state_pressure.set(SettingsHelper.STATE_PRESSURE.MILLIBARS);
        placeFormActivity.setPressureMilliBars();
    }

    public void setWindMetersPerSeconds(){
        settingsHelper.saveFirstPageSharedPreferences(Constants.SHAR_PREFS_STATE_WIND, Constants.MPH_STATE);
        state_wind.set(SettingsHelper.STATE_WIND.M_PER_HOUR);
        placeFormActivity.setWindMetersPerSeconds();
    }
    public void setWindKilometersPerHour(){
        settingsHelper.saveFirstPageSharedPreferences(Constants.SHAR_PREFS_STATE_WIND, Constants.KPH_STATE);
        state_wind.set(SettingsHelper.STATE_WIND.KM_PER_HOUR);
        placeFormActivity.setWindKilometersPerHour();
    }


}
