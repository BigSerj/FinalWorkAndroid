package com.android.bigserj.PlaceForm;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.bigserj.R;
import com.android.bigserj.base.BaseAppCompatActivity;
import com.android.bigserj.databinding.ActivityPlaceformBinding;

public class PlaceFormActivity extends BaseAppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        PlaceFormViewModel viewModel = new PlaceFormViewModel(this);
        this.viewModel = viewModel;

        ActivityPlaceformBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_placeform);

        binding.setViewModel(viewModel);

        super.onCreate(savedInstanceState);

    }


    public static void showFragment(FragmentManager fragmentManager, Fragment fragment,
                                    boolean addToBackStack, int layout) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layout, fragment, fragment.getClass().getName());
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(2).commit();
    }

}

