package com.orangeTree.bigserj.PlaceForm;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orangeTree.bigserj.base.BaseFragment;
import com.orangeTree.bigserj.Constants;
import com.orangeTree.bigserj.databinding.FragmentPageBinding;

public class PlaceFormPageFragment extends BaseFragment {

    FragmentPageBinding binding;
    PlaceFormPageViewModel placeFormPageViewModel;

    static PlaceFormPageFragment placeFormPageFragmentThis;


    public static PlaceFormPageFragment newInstance(FragmentManager manager,
                                                    Integer id,String lat,String lon, String city) {

        Fragment fragment = manager.findFragmentByTag(PlaceFormPageFragment.class.getName());
        PlaceFormPageFragment placeFormPageFragment;

        if (fragment != null && fragment instanceof PlaceFormPageFragment)
            placeFormPageFragment = (PlaceFormPageFragment) fragment;
        else {
            placeFormPageFragment = new PlaceFormPageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.PUT_EXTRA_ID, id);
            bundle.putString(Constants.PUT_EXTRA_LAT, lat);
            bundle.putString(Constants.PUT_EXTRA_LON, lon);
            bundle.putString(Constants.PUT_EXTRA_PLACE_NAME, city);
            placeFormPageFragment.setArguments(bundle);
        }
        placeFormPageFragmentThis = placeFormPageFragment;
        return placeFormPageFragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        placeFormPageViewModel = new PlaceFormPageViewModel(this);
        this.viewModelFragment = placeFormPageViewModel;

        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            placeFormPageViewModel.id = bundle.getInt(Constants.PUT_EXTRA_ID);
            placeFormPageViewModel.lat = bundle.getString(Constants.PUT_EXTRA_LAT);
            placeFormPageViewModel.lon = bundle.getString(Constants.PUT_EXTRA_LON);
            placeFormPageViewModel.city = bundle.getString(Constants.PUT_EXTRA_PLACE_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPageBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setViewModel(placeFormPageViewModel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



}
