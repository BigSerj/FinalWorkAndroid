package com.android.bigserj.mainActivity;


import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.bigserj.PlaceForm.PlaceFormActivity;
import com.android.bigserj.PlaceForm.PlaceFormViewModel;
import com.android.bigserj.R;
import com.android.bigserj.base.BaseViewModelFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;

import static android.content.ContentValues.TAG;

public class GooglePlaceApiViewModel implements BaseViewModelFragment,PlaceSelectionListener {

    public GooglePlaceApiFragment googlePlaceApiFragment;
    public GooglePlaceApiViewModel (GooglePlaceApiFragment googlePlaceApiFragment){
        this.googlePlaceApiFragment = googlePlaceApiFragment;
    }


    @Override
    public void onPlaceSelected(Place place) {
//        // убираем фрагмент с выбором place
        googlePlaceApiFragment.getActivity().getSupportFragmentManager().popBackStack();

        // заполняем инфой соотв поля нового места
        PlaceFormViewModel.cityPlaceForm = place.getName().toString();
        PlaceFormViewModel.latPlaceForm = String.valueOf(place.getLatLng().latitude);
        PlaceFormViewModel.lonPlaceForm = String.valueOf(place.getLatLng().longitude);

        Intent intent = new Intent(googlePlaceApiFragment.getActivity(), PlaceFormActivity.class); // объект, который выполняет для нас что-либо (намерения, наприме, перейти куда-либо или открыт что-то)
        googlePlaceApiFragment.getActivity().startActivity(intent);
    }

    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());

        Toast.makeText(googlePlaceApiFragment.getContext(),
                "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }



    @Override
    public void create() {

    }

    @Override
    public void destroyView() {
        //        // убираем фрагмент с выбором place
        googlePlaceApiFragment.getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void viewCreated() {

        // инициализируем фрагмент поиска гугла
        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment)
                googlePlaceApiFragment
                        .getChildFragmentManager()
                        .findFragmentById(R.id.place_autocomplete_fragment1);

        // устанавливаем фильтр на то, чтобы поиск происходил только по населенным пунктам
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        autocompleteFragment.setFilter(typeFilter);
        // устанавливаем кликлистенер
        autocompleteFragment.setOnPlaceSelectedListener(this);

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
