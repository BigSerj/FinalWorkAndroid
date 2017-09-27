package com.android.bigserj.myPlaces;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.bigserj.R;
import com.android.bigserj.base.BaseFragmentActivity;
import com.android.bigserj.databinding.ActivityMyPlacesBinding;

public class MyPlacesActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        MyPlacesViewModel viewModel = new MyPlacesViewModel(this);
        this.viewModel = viewModel;

        ActivityMyPlacesBinding binding = DataBindingUtil.
                setContentView(this, R.layout.activity_my_places);

        binding.setViewModel(viewModel);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
