package com.android.bigserj.inLocality;


import com.android.bigserj.base.BaseViewModel;
import com.android.bigserj.mainActivity.FirstPageActivity;


public class InLocalityViewModel implements BaseViewModel {


    public InLocalityActivity activity;
    public InLocalityViewModel(InLocalityActivity activity) {
        this.activity = activity;
    }


    public static final String LAT_LONG = "LAT_LONG";
    public static final String NAME_PLACE = "NAME_PLACE";


    @Override
    public void init() {

    }

    @Override
    public void release() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }



    public void onSuperButtonClickToMainActivity() {
        InLocalityActivity.show(activity,FirstPageActivity.class);
    }

    public void onSuperButtonClickToPlacesAutoCompleteActivity() {
//        InLocalityActivity.show(activity,PlacesAutoCompleteActivity.class);
    }





}
