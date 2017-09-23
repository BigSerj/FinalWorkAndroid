package com.android.bigserj.inLocality;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bigserj.base.BaseFragment;
import com.android.bigserj.databinding.FragmentLocation1Binding;
import static com.android.bigserj.inLocality.Location1ViewModel.*;


public class Location1Fragment extends BaseFragment {

    private FragmentLocation1Binding binding;
    private Location1ViewModel location1ViewModel;

    public static Location1Fragment newInstance(FragmentManager manager,
                                                String text1, String text2) {

        Fragment fragment = manager.findFragmentByTag(Location1Fragment.class.getName());
        Location1Fragment location1Fragment;

        if (fragment != null && fragment instanceof Location1Fragment)
            location1Fragment = (Location1Fragment) fragment;
        else {
            location1Fragment = new Location1Fragment();
            Bundle bundle = new Bundle();
            bundle.putString(LAT_LONG, text1);
            bundle.putString(NAME_PLACE, text2);
            location1Fragment.setArguments(bundle);
        }

        return location1Fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        location1ViewModel = new Location1ViewModel(this);
        this.viewModelFragment = location1ViewModel;

        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            location1ViewModel.latLong = bundle.getString(LAT_LONG);
            location1ViewModel.namePlace = bundle.getString(NAME_PLACE);
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLocation1Binding.inflate(inflater);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setViewModel(location1ViewModel);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
