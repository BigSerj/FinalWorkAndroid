package com.orangeTree.bigserj.mainActivity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orangeTree.bigserj.base.BaseFragment;
import com.orangeTree.bigserj.databinding.FragmentGoogleplaceBinding;


public class GooglePlaceApiFragment extends BaseFragment {

    private FragmentGoogleplaceBinding binding;
    private GooglePlaceApiViewModel googlePlaceApiViewModel;


    public static GooglePlaceApiFragment newInstance(FragmentManager manager) {

        Fragment fragment = manager.findFragmentByTag(GooglePlaceApiFragment.class.getName());
        GooglePlaceApiFragment googlePlaceApiFragment;

        if (fragment != null && fragment instanceof GooglePlaceApiFragment)
            googlePlaceApiFragment = (GooglePlaceApiFragment) fragment;
        else {
            googlePlaceApiFragment = new GooglePlaceApiFragment();
            Bundle bundle = new Bundle();
            googlePlaceApiFragment.setArguments(bundle);
        }

        return googlePlaceApiFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        googlePlaceApiViewModel = new GooglePlaceApiViewModel(this);
        this.viewModelFragment = googlePlaceApiViewModel;
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentGoogleplaceBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setViewModel(googlePlaceApiViewModel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
