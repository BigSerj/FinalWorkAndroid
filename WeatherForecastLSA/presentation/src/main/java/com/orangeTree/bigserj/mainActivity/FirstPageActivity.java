package com.orangeTree.bigserj.mainActivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.orangeTree.bigserj.R;
import com.orangeTree.bigserj.base.BaseAppCompatActivity;
import com.crashlytics.android.Crashlytics;
import com.orangeTree.bigserj.databinding.ActivityMainBinding;

import io.fabric.sdk.android.Fabric;

public class FirstPageActivity extends BaseAppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        FirstPageViewModel viewModel = new FirstPageViewModel(this);
        this.viewModel = viewModel;

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setViewModel(viewModel);

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

    }

}