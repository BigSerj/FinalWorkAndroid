package com.android.bigserj.mainActivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.android.bigserj.R;
import com.android.bigserj.base.BaseAppCompatActivity;
import com.android.bigserj.databinding.ActivityMainBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tagmanager.Container;

public class FirstPageActivity extends BaseAppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        FirstPageViewModel viewModel = new FirstPageViewModel(this);
        this.viewModel = viewModel;

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setViewModel(viewModel);

        super.onCreate(savedInstanceState);





//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.search);



    }


    private static void showFragment(FragmentManager fragmentManager, Fragment fragment,
                                     boolean addToBackStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerSearch,fragment,fragment.getClass().getName());
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}