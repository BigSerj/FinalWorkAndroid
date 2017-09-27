package com.android.bigserj.mainActivity;


import android.content.Intent;
import android.databinding.ObservableField;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.bigserj.MyLocalityAdapter;
import com.android.bigserj.PlaceForm.PlaceFormViewModel;
import com.android.bigserj.R;
import com.android.bigserj.ToastMessage;
import com.android.bigserj.base.BaseViewModel;
import com.android.bigserj.myPlaces.MyPlacesActivity;
import com.android.bigserj.PlaceForm.PlaceFormActivity;

import static com.android.bigserj.Constants.*;

import static com.android.bigserj.Constants.URL_GIF_EARTH;

public class FirstPageViewModel implements BaseViewModel {


    private FirstPageActivity firstPageActivity;
    FirstPageViewModel(FirstPageActivity firstPageActivity) {
        this.firstPageActivity = firstPageActivity;
    }

    private MyLocalityAdapter myLocality;

    public ObservableField<String> urlEarth = new ObservableField<>();
    public enum STATE {PROGRESS, DATA}
    public ObservableField<STATE> state = new ObservableField<>(STATE.PROGRESS);


    @Override
    public void init() {
        // инициализируем объект класса для работы с текущей геолокацией
        myLocality = new MyLocalityAdapter(firstPageActivity);
        urlEarth.set(URL_GIF_EARTH);
    }

    @Override
    public void release() {

    }

    @Override
    public void resume() {



    }

    @Override
    public void pause() {
        state.set(STATE.PROGRESS);
    }




    private static void showFragment(FragmentManager fragmentManager, Fragment fragment,
                                     boolean addToBackStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerSearch,fragment,fragment.getClass().getName());
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



    // нажатие кнопки "ПО ТЕКУЩЕМУ МЕСТОПОЛОЖЕНИЮ"
    public void fromMainToGeocoder() {
        Location myLastLocation = myLocality.getLastLocation();
        if (myLastLocation !=null) {
            closeSearchFragment();

            PlaceFormViewModel.cityPlaceForm = EMPTY_PLACE;
            PlaceFormViewModel.latPlaceForm = String.valueOf(myLastLocation.getLatitude());
            PlaceFormViewModel.lonPlaceForm = String.valueOf(myLastLocation.getLongitude());

            Intent intent = new Intent(firstPageActivity, PlaceFormActivity.class);
            firstPageActivity.startActivity(intent);
        }else
            ToastMessage.showToast(firstPageActivity,PERMISSION_RATIONAL);
    }

    // нажатие кнопки "ПОИСК НОВОГО МЕСТА"
    public void fromMainToSearch() {
        state.set(STATE.DATA);
        showFragment(firstPageActivity.getSupportFragmentManager(), GooglePlaceApiFragment
                .newInstance(firstPageActivity.getSupportFragmentManager()), true);
    }

    // нажатие кнопки "МОИ МЕСТА"
    public void fromMainToMyPlaces() {
        closeSearchFragment();
        Intent intent = new Intent(firstPageActivity, MyPlacesActivity.class);
        firstPageActivity.startActivity(intent);
    }

    // закрытие фрагмента "ПОИСК"
    public void closeSearchFragment() {
        state.set(STATE.PROGRESS);
        firstPageActivity.getSupportFragmentManager().popBackStack();
    }




}
