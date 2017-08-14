package com.android.bigserj.inLocality;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.android.bigserj.R;
import com.android.bigserj.base.BaseActivity;
import com.android.bigserj.databinding.ActivityInLocalityBinding;
import com.android.bigserj.mainActivity.MainActivity;

public class InLocalityActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        InLocalityViewModel viewModel = new InLocalityViewModel(this);
        this.viewModel = viewModel;


        ActivityInLocalityBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_in_locality);

        binding.setViewModel(viewModel);

        super.onCreate(savedInstanceState);

        Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InLocalityActivity.this, MainActivity.class); // объект, который выполняет для нас что-либо (намерения, наприме, перейти куда-либо или открыт что-то)
                startActivity(intent);
//                overridePendingTransition(R.anim.diagonaltranslate,R.anim.alpha);
            }
        });

    }
}