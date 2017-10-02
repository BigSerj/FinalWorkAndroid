package com.orangeTree.bigserj.PlaceForm;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.orangeTree.bigserj.databinding.ActivityPlaceformBinding;
import com.orangeTree.bigserj.R;
import com.orangeTree.bigserj.base.BaseAppCompatActivity;

public class PlaceFormActivity extends BaseAppCompatActivity implements OnTouchSearchListener,
        OnChangeCityListener{

    PlaceFormViewModel placeFormViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        placeFormViewModel = new PlaceFormViewModel(this);
        this.viewModel = placeFormViewModel;

        ActivityPlaceformBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_placeform);

        binding.setViewModel(placeFormViewModel);

        super.onCreate(savedInstanceState);



    }


    public static void showFragment(FragmentManager fragmentManager, Fragment fragment,
                                    boolean addToBackStack, int layout) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layout, fragment, fragment.getClass().getName());
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(2).commit();
    }

    @Override
    public void onTouchSearch() {
        placeFormViewModel.onTouchSearch();
    }

    @Override
    public void onChangeCity(String city) {
        placeFormViewModel.onChangeCity(city);
    }



    public void closeAllFragments(){
        for(int i=getSupportFragmentManager().getBackStackEntryCount();i>=0;i--) {
            getSupportFragmentManager().popBackStack();
            placeFormViewModel.closeSearchFragment();
            placeFormViewModel.deflateSettingsFragment();
        }
    }



    public void setCelsius() {
        placeFormViewModel.getCurrentFragment().placeFormPageViewModel.setCelsius();
    }

    public void setFahrenheit() {
        placeFormViewModel.getCurrentFragment().placeFormPageViewModel.setFahrenheit();
    }

    public void setPressureMMOfMercury() {
        placeFormViewModel.getCurrentFragment().placeFormPageViewModel.setPressureMMOfMercury();
    }

    public void setPressureMilliBars() {
        placeFormViewModel.getCurrentFragment().placeFormPageViewModel.setPressureMilliBars();
    }

    public void setWindMetersPerSeconds() {
        placeFormViewModel.getCurrentFragment().placeFormPageViewModel.setWindMetersPerSeconds();
    }

    public void setWindKilometersPerHour() {
        placeFormViewModel.getCurrentFragment().placeFormPageViewModel.setWindKilometersPerHour();
    }

}