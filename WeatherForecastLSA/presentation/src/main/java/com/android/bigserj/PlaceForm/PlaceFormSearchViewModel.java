package com.android.bigserj.PlaceForm;


import android.util.Log;
import android.widget.Toast;

import com.android.bigserj.R;
import com.android.bigserj.base.BaseViewModelFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;

import static android.content.ContentValues.TAG;

public class PlaceFormSearchViewModel implements BaseViewModelFragment,PlaceSelectionListener {

    public PlaceFormSearchFragment placeFormSearchFragment;
    public PlaceFormSearchViewModel(PlaceFormSearchFragment placeFormSearchFragment){
        this.placeFormSearchFragment = placeFormSearchFragment;
    }

    private OnTouchSearchListener someEventListener;

    @Override
    public void onPlaceSelected(Place place) {

        // заполняем инфой соотв поля нового места
        PlaceFormViewModel.cityPlaceForm = String.valueOf(place.getName());
        PlaceFormViewModel.latPlaceForm = String.valueOf(place.getLatLng().latitude);
        PlaceFormViewModel.lonPlaceForm = String.valueOf(place.getLatLng().longitude);


        someEventListener.onTouchSearch();
    }



    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());

        Toast.makeText(placeFormSearchFragment.getContext(), "Place selection failed: " +
                        status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }




    @Override
    public void create() {
    }

    @Override
    public void destroyView() {
    }

    @Override
    public void viewCreated() {

        try {
            someEventListener = (OnTouchSearchListener) placeFormSearchFragment.getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(placeFormSearchFragment.getActivity().toString() +
                    " must implement OnTouchSearchListener");
        }

        // инициализируем фрагмент поиска гугла
        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment)
                placeFormSearchFragment
                        .getChildFragmentManager()
                        .findFragmentById(R.id.place_autocomplete_fragment2);

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
