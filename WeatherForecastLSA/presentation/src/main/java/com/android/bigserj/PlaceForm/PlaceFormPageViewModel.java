package com.android.bigserj.PlaceForm;


import android.databinding.ObservableField;
import android.util.Log;

import com.android.bigserj.PageSettingsAdapter;
import com.android.bigserj.SettingsHelper;
import com.android.bigserj.TestApplication;
import com.android.bigserj.base.BaseViewModelFragment;
import com.android.bigserj.domain.entity.DataOnTheLocality;
import com.android.bigserj.domain.interaction.GetDataOnTheLocalityUseCase;
import com.bumptech.glide.Glide;

import java.security.spec.ECField;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

import static com.android.bigserj.Constants.*;

public class PlaceFormPageViewModel implements BaseViewModelFragment {

    public PlaceFormPageFragment placeFormPageFragment;

    public PlaceFormPageViewModel(PlaceFormPageFragment newPlaceFormPageFragment) {
        this.placeFormPageFragment = newPlaceFormPageFragment;
        TestApplication.appComponent.inject(this);
    }

    @Inject
    GetDataOnTheLocalityUseCase getDataOnTheLocalityUseCase;

    private OnChangeCityListener onChangeCityListener;

    PageSettingsAdapter pageSettingsAdapter;




    // регулируем + и -
    public enum STATE_ABOUT_ZERO {
        NONE, MINUS, PLUS
    }
    public ObservableField<STATE_ABOUT_ZERO> state_about_zero =
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



    public ObservableField<String> weather = new ObservableField<>();
    public ObservableField<String> temp = new ObservableField<>();
    public ObservableField<String> humidity = new ObservableField<>();
    public ObservableField<String> pressure = new ObservableField<>();
    public ObservableField<String> windSpeed = new ObservableField<>();
    public ObservableField<String> cityName = new ObservableField<>();
    public ObservableField<String> time = new ObservableField<>();


    Integer id;
    String lat;
    String lon;
    String city;


    @Override
    public void create() {
        SettingsHelper settingsHelper = new SettingsHelper(placeFormPageFragment.getContext());
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
                    @Override
                    public void onNext(@NonNull DataOnTheLocality dataOnTheLocalityModel) {


                        pageSettingsAdapter = new PageSettingsAdapter(dataOnTheLocalityModel);

                        // 1
                        // если поиск был по геолокации - вставляем найденное место
                        if (city.equals(EMPTY_PLACE)) {
                            System.out.println("");
                            city = dataOnTheLocalityModel.getDisplayLocationModel().getCity();
                            onChangeCityListener.onChangeCity(city);
                        }
                        cityName.set(city);

                        // 2
                        // вводим температуру
                        try{
                            temp.set(pageSettingsAdapter.getTemp().toString());
                        }catch (Exception e){
                            temp.set("");
                            Log.e("ReadAPI", "Error by reading temp");
                        }

                        // регулируем показаниями - "+"/"-"
                        if (pageSettingsAdapter.isMinus0())
                            state_about_zero.set(STATE_ABOUT_ZERO.MINUS);
                        else
                            state_about_zero.set(STATE_ABOUT_ZERO.PLUS);


                        // 3
                        // вводим Weather
                        try {
                            weather.set(dataOnTheLocalityModel.getWeather());
                        }catch (Exception e){
                            weather.set("");
                            Log.e("ReadAPI", "Error by reading weather");
                        }

                        // 4
                        // выводим влажность
                        try {
                            humidity.set(dataOnTheLocalityModel.getHumidity());
                        }catch (Exception e){
                            humidity.set("");
                            Log.e("ReadAPI", "Error by reading humidity");
                        }

                        // 5  !!!
                        // выводим давление
                        try {
                            pressure.set(pageSettingsAdapter.getPressure().toString());
                        }catch (Exception e){
                            pressure.set("");
                            Log.e("ReadAPI", "Error by reading pressure");
                        }

                        // 6
                        // выводим скорость ветра
                        try{
                            windSpeed.set(pageSettingsAdapter.getWindSpeed().toString());
                        }catch (Exception e){
                            windSpeed.set("");
                            Log.e("ReadAPI", "Error by reading windSpeed");
                        }

                        // 7
                        //выводим время
                        try {
                            time.set(dataOnTheLocalityModel.getObservationTime());
                        }catch (Exception e){
                            time.set("");
                            Log.e("ReadAPI", "Error by reading Weather");
                        }
                        // 8
                        // выводим картинку
                        try {
                            Glide.with(placeFormPageFragment.getContext())
                                    .load(dataOnTheLocalityModel
                                            .getIconUrl()).into(placeFormPageFragment.binding.urlWeather);
                        }catch (Exception e){
                            Log.e("ReadAPI", "Error by reading the URL");
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

}
