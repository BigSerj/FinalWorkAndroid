package com.orangeTree.bigserj.PlaceForm;


import android.databinding.ObservableField;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.orangeTree.bigserj.DateParse;
import com.orangeTree.bigserj.PageSettingsAdapter;
import com.orangeTree.bigserj.SettingsHelper;
import com.orangeTree.bigserj.TestApplication;
import com.orangeTree.bigserj.base.BaseViewModelFragment;
import com.orangeTree.bigserj.Constants;
import com.orangeTree.bigserj.domain.entity.DataOnTheLocality;
import com.orangeTree.bigserj.domain.interaction.GetDataOnTheLocalityUseCase;
import com.bumptech.glide.Glide;


import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

public class PlaceFormPageViewModel implements BaseViewModelFragment {

    private PlaceFormPageFragment placeFormPageFragment;

    PlaceFormPageViewModel(PlaceFormPageFragment newPlaceFormPageFragment) {
        this.placeFormPageFragment = newPlaceFormPageFragment;
        TestApplication.appComponent.inject(this);
    }

    @Inject
    GetDataOnTheLocalityUseCase getDataOnTheLocalityUseCase;

    private OnChangeCityListener onChangeCityListener;

    private PageSettingsAdapter pageSettingsAdapter;
    private SettingsHelper settingsHelper;


    // регулируем + и -
    public enum STATE_ABOUT_ZERO {
        NONE, MINUS, PLUS
    }

    // регулируем показывать направление ветра или нет
    public enum STATE_WIND_SHOW {
        NONE, YES, NO
    }

    public ObservableField<STATE_ABOUT_ZERO> state_about_zero =
            new ObservableField<>(STATE_ABOUT_ZERO.NONE);
    public ObservableField<STATE_ABOUT_ZERO> state_about_zero_FeelsLike_ =
            new ObservableField<>(STATE_ABOUT_ZERO.NONE);

    // регулируем цельсия/фаренгейт
    public ObservableField<SettingsHelper.STATE_TEMP> state_temp_page =
            new ObservableField<>(SettingsHelper.STATE_TEMP.NONE);

    // регулируем мм.рт.ст./мбар
    public ObservableField<SettingsHelper.STATE_PRESSURE> state_pressure_page =
            new ObservableField<>(SettingsHelper.STATE_PRESSURE.NONE);

    // регулируем в чем скорость ветра
    public ObservableField<SettingsHelper.STATE_WIND> state_wind_page =
            new ObservableField<>(SettingsHelper.STATE_WIND.NONE);

    // регулируем в чем скорость ветра
    public ObservableField<STATE_WIND_SHOW> state_wind_show =
            new ObservableField<>(STATE_WIND_SHOW.NONE);


    public ObservableField<String> weather = new ObservableField<>();
    public ObservableField<String> temp = new ObservableField<>();
    public ObservableField<String> tempFeelsLike = new ObservableField<>();
    public ObservableField<String> humidity = new ObservableField<>();
    public ObservableField<String> pressure = new ObservableField<>();
    public ObservableField<String> windSpeed = new ObservableField<>();
    public ObservableField<Integer> windDirection = new ObservableField<>();
    public ObservableField<String> windDir = new ObservableField<>();
    public ObservableField<String> cityName = new ObservableField<>();
    public ObservableField<String> timeDayTime = new ObservableField<>();
    public ObservableField<String> timeDate = new ObservableField<>();


    Integer id;
    String lat;
    String lon;
    String city;


    @Override
    public void create() {
        settingsHelper = new SettingsHelper(placeFormPageFragment.getContext());
    }

    @Override
    public void destroyView() {
        getDataOnTheLocalityUseCase.dispose();
    }

    @Override
    public void viewCreated() {


        try {
            onChangeCityListener = (OnChangeCityListener) placeFormPageFragment.getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(placeFormPageFragment.getActivity().toString() +
                    " must implement OnTouchSearchListener");
        }


        getDataOnTheLocalityUseCase.execute(lat + "," + lon,
                new DisposableObserver<DataOnTheLocality>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onNext(@NonNull DataOnTheLocality dataOnTheLocalityModel) {


                        pageSettingsAdapter = new PageSettingsAdapter(dataOnTheLocalityModel);

                        // 1
                        // если поиск был по геолокации - вставляем найденное место
                        if (city.equals(Constants.EMPTY_PLACE)) {
                            city = dataOnTheLocalityModel.getDisplayLocationModel().getCity();
                            onChangeCityListener.onChangeCity(city);
                        }
                        cityName.set(city);

                        // выводим все параметры которые могут изменяться в настройках
                        setChangeableParameters();

                        // 3
                        // вводим Weather
                        try {
                            weather.set(dataOnTheLocalityModel.getWeather());
                        } catch (Exception e) {
                            weather.set("");
                            Log.e("ReadAPI", "Error by reading weather");
                        }

                        // 4
                        // выводим влажность
                        try {
                            humidity.set(dataOnTheLocalityModel.getHumidity());
                        } catch (Exception e) {
                            humidity.set("-");
                            Log.e("ReadAPI", "Error by reading humidity");
                        }


                        // 7
                        //выводим время
                        try {
                            // 1 строка - день недели, время
                            timeDayTime.set(DateParse.parseToDayAndTime(dataOnTheLocalityModel.getObservationTime()));
                            // 2 строка - дата
                            timeDate.set(DateParse.parseToCertainData(dataOnTheLocalityModel.getObservationTime()));
                        } catch (Exception e) {
                            timeDayTime.set("-");
                            timeDate.set("-");
                            Log.e("ReadAPI", "Error by parsing the String to Date");
                        }


                        // 8
                        // выводим картинку текущей погоды
                        try {
                            Glide.with(placeFormPageFragment.getContext())
                                    .load(dataOnTheLocalityModel
                                            .getIconUrl()).into(placeFormPageFragment.binding.urlWeather);
                        } catch (Exception e) {
                            Log.e("ReadAPI", "Error by reading the URL");
                        }


                        // 9
                        // выводим картинку направления ветра
                        try {
                            if (!dataOnTheLocalityModel.getWindDegrees().isEmpty()) {
                                Integer dirWind = Integer.valueOf(dataOnTheLocalityModel.getWindDegrees());
                                if (dirWind >= 0 && dirWind <= 360) {
                                    state_wind_show.set(STATE_WIND_SHOW.YES);
//                                    windDirection.set(dirWind-180); // если стрелка изначально вниз
                                    windDirection.set(dirWind);
                                } else
                                    state_wind_show.set(STATE_WIND_SHOW.NO);
                            }
                        } catch (Exception e) {
                            Log.e("ReadAPI", "Error by reading the windGPS");
                        }

                        // 10
                        // выводим текст направление ветра
                        try {
                            // 1 строка - день недели, время
                            windDir.set(dataOnTheLocalityModel.getWindDir());
                        } catch (Exception e) {
                            windDir.set("-");
                            Log.e("ReadAPI", "Error by reading the wind direction");
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("items ", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("item", "onComplete");
                    }
                });

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


    private void setChangeableParameters() {
        if (pageSettingsAdapter==null)
            settingsHelper = new SettingsHelper(placeFormPageFragment.getContext());

        pageSettingsAdapter.initReload();

        // 2
        // вводим температуру
        try {
            temp.set(pageSettingsAdapter.getTemp().toString());
            state_temp_page.set(SettingsHelper.tempCurrentState);
        } catch (Exception e) {
            temp.set("-");
            Log.e("ReadAPI", "Error by reading temp");
        }

        // регулируем показаниями - "+"/"-"
        if (pageSettingsAdapter.isMinus0() && pageSettingsAdapter.isCelsius())
            state_about_zero.set(STATE_ABOUT_ZERO.MINUS);
        else
            state_about_zero.set(STATE_ABOUT_ZERO.PLUS);


        // 2.2
        // вводим температуру по ощущениям
        try {
            tempFeelsLike.set(pageSettingsAdapter.getTempFeelsLike().toString());
            state_temp_page.set(SettingsHelper.tempCurrentState);
        } catch (Exception e) {
            tempFeelsLike.set("-");
            Log.e("ReadAPI", "Error by reading temp");
        }

        // регулируем показаниями - "+"/"-" температуры по ощущениям
        if (pageSettingsAdapter.isFeelsLikeMinus0() && pageSettingsAdapter.isCelsius())
            state_about_zero_FeelsLike_.set(STATE_ABOUT_ZERO.MINUS);
        else
            state_about_zero_FeelsLike_.set(STATE_ABOUT_ZERO.PLUS);




        // 5
        // выводим давление
        try {
            pressure.set(pageSettingsAdapter.getPressure().toString());
            state_pressure_page.set(SettingsHelper.pressureCurrentState);
        } catch (Exception e) {
            pressure.set("-");
            Log.e("ReadAPI", "Error by reading pressure");
        }

        // 6
        // выводим скорость ветра
        try {
            windSpeed.set(pageSettingsAdapter.getWindSpeed().toString());
            state_wind_page.set(SettingsHelper.windCurrentState);
        } catch (Exception e) {
            windSpeed.set("-");
            Log.e("ReadAPI", "Error by reading windSpeed");
        }
    }


    public void closeAllFragments(){
        ((PlaceFormActivity) placeFormPageFragment.getActivity()).closeAllFragments();
    }


    void setCelsius() {
        setChangeableParameters();
        state_temp_page.set(SettingsHelper.STATE_TEMP.CELSIUS);
    }

    void setFahrenheit() {
        setChangeableParameters();
        state_temp_page.set(SettingsHelper.STATE_TEMP.FAHRENHEIT);
    }

    void setPressureMMOfMercury() {
        setChangeableParameters();
        state_pressure_page.set(SettingsHelper.STATE_PRESSURE.MILLIMETERS_OF_MERCURY);
    }

    void setPressureMilliBars() {
        setChangeableParameters();
        state_pressure_page.set(SettingsHelper.STATE_PRESSURE.MILLIBARS);
    }

    void setWindMetersPerSeconds() {
        setChangeableParameters();
        state_wind_page.set(SettingsHelper.STATE_WIND.M_PER_HOUR);
    }

    void setWindKilometersPerHour() {
        setChangeableParameters();
        state_wind_page.set(SettingsHelper.STATE_WIND.KM_PER_HOUR);
    }


}
