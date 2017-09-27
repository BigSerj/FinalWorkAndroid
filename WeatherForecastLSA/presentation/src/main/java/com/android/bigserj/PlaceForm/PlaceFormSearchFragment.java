package com.android.bigserj.PlaceForm;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bigserj.base.BaseFragment;
import com.android.bigserj.databinding.FragmentPlaceFormSearchBinding;


public class PlaceFormSearchFragment extends BaseFragment {


    private FragmentPlaceFormSearchBinding binding;
    private PlaceFormSearchViewModel placeFormSearchViewModel;


    public static PlaceFormSearchFragment newInstance(FragmentManager manager) {

        Fragment fragment = manager.findFragmentByTag(PlaceFormSearchFragment.class.getName());
        PlaceFormSearchFragment placeFormSearchFragment;

        if (fragment != null && fragment instanceof PlaceFormSearchFragment)
            placeFormSearchFragment = (PlaceFormSearchFragment) fragment;
        else {
            placeFormSearchFragment = new PlaceFormSearchFragment();
            Bundle bundle = new Bundle();
            placeFormSearchFragment.setArguments(bundle);
        }

        return placeFormSearchFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        placeFormSearchViewModel = new PlaceFormSearchViewModel(this);
        this.viewModelFragment = placeFormSearchViewModel;
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPlaceFormSearchBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setViewModel(placeFormSearchViewModel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



}
