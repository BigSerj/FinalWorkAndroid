package com.android.bigserj.PlaceForm;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.bigserj.base.BaseFragment;
import com.android.bigserj.databinding.FragmentPageBinding;

import static com.android.bigserj.Constants.*;

public class PlaceFormPageFragment extends BaseFragment {

    FragmentPageBinding binding;
    private PlaceFormPageViewModel placeFormPageViewModel;


    public static PlaceFormPageFragment newInstance(FragmentManager manager,
                                                    Integer id,String lat,String lon, String city) {

        Fragment fragment = manager.findFragmentByTag(PlaceFormPageFragment.class.getName());
        PlaceFormPageFragment placeFormPageFragment;

        if (fragment != null && fragment instanceof PlaceFormPageFragment)
            placeFormPageFragment = (PlaceFormPageFragment) fragment;
        else {
            placeFormPageFragment = new PlaceFormPageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(PUT_EXTRA_ID, id);
            bundle.putString(PUT_EXTRA_LAT, lat);
            bundle.putString(PUT_EXTRA_LON, lon);
            bundle.putString(PUT_EXTRA_PLACE_NAME, city);
            placeFormPageFragment.setArguments(bundle);
        }
        return placeFormPageFragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        placeFormPageViewModel = new PlaceFormPageViewModel(this);
        this.viewModelFragment = placeFormPageViewModel;

        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            placeFormPageViewModel.id = bundle.getInt(PUT_EXTRA_ID);
            placeFormPageViewModel.lat = bundle.getString(PUT_EXTRA_LAT);
            placeFormPageViewModel.lon = bundle.getString(PUT_EXTRA_LON);
            placeFormPageViewModel.city = bundle.getString(PUT_EXTRA_PLACE_NAME);
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
