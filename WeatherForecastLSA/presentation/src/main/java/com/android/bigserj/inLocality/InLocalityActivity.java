package com.android.bigserj.inLocality;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.android.bigserj.R;
import com.android.bigserj.base.BaseFragmentActivity;
import com.android.bigserj.databinding.ActivityInLocalityBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import static com.android.bigserj.inLocality.Location1ViewModel.*;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class InLocalityActivity extends BaseFragmentActivity {

    public static void show(InLocalityActivity activity, Class toAtivity){
        Intent intent = new Intent(activity, toAtivity); // объект, который выполняет для нас что-либо (намерения, наприме, перейти куда-либо или открыт что-то)
        activity.startActivity(intent);
    }

    public static final String WEATHER = "WEATHER";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        InLocalityViewModel viewModel = new InLocalityViewModel(this);
        this.viewModel = viewModel;

        ActivityInLocalityBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_in_locality);

        binding.setViewModel(viewModel);

        super.onCreate(savedInstanceState);





        String latLong = getIntent().getStringExtra(LAT_LONG)
                .replace("lat/lng: (","").replace(")","");
        String namePlace = getIntent().getStringExtra(NAME_PLACE);

        String weather = getIntent().getStringExtra(WEATHER);



        showFragment(getSupportFragmentManager(), Location1Fragment
                .newInstance(getSupportFragmentManager(), latLong, namePlace), true);




//        showFragment(getSupportFragmentManager(), Location2Fragment
//                .newInstance(getSupportFragmentManager(), weather), true);






    }

    public static void showFragment(FragmentManager fragmentManager, Fragment fragment,
                                    boolean addToBackStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment,fragment.getClass().getName());
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}