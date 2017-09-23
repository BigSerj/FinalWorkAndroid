package com.android.bigserj.PlaceForm;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bigserj.base.BaseFragment;
import com.android.bigserj.databinding.FragmentSettingsBinding;

public class PlaceFormSettingsFragment extends BaseFragment{

    private FragmentSettingsBinding binding;
    private PlaceFormSettingsViewModel placeFormSettingsViewModel;


    public static PlaceFormSettingsFragment newInstance(FragmentManager manager) {

        Fragment fragment = manager.findFragmentByTag(PlaceFormSearchFragment.class.getName());
        PlaceFormSettingsFragment placeFormSettingsFragment;

        if (fragment != null && fragment instanceof PlaceFormSettingsFragment)
            placeFormSettingsFragment = (PlaceFormSettingsFragment) fragment;
        else {
            placeFormSettingsFragment = new PlaceFormSettingsFragment();
            Bundle bundle = new Bundle();
            placeFormSettingsFragment.setArguments(bundle);
        }

        return placeFormSettingsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        placeFormSettingsViewModel = new PlaceFormSettingsViewModel(this);
        this.viewModelFragment = placeFormSettingsViewModel;
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setViewModel(placeFormSettingsViewModel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
