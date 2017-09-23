package com.android.bigserj.inLocality;


import android.databinding.ObservableField;
import android.util.Log;

import com.android.bigserj.TestApplication;
import com.android.bigserj.base.BaseViewModelFragment;
import com.android.bigserj.domain.entity.DataOnTheLocality;
import com.android.bigserj.domain.interaction.GetDataOnTheLocalityUseCase;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

import static com.android.bigserj.Constants.NULL_STRING;

public class Location1ViewModel implements BaseViewModelFragment{


    public Location1Fragment location1Fragment;
    public Location1ViewModel (Location1Fragment location1Fragment){
        this.location1Fragment = location1Fragment;
        TestApplication.appComponent.inject(this);
    }


    public String latLong;
    public String namePlace;


    @Inject
    public GetDataOnTheLocalityUseCase getDataOnTheLocalityUseCase;
//    private GetDataOnTheLocalityUseCase getDataOnTheLocalityUseCase =
//            new GetDataOnTheLocalityUseCase();

    public ObservableField<String> tempC = new ObservableField<>();
    public ObservableField<String> namePlaceObsF = new ObservableField<>();

    public static final String LAT_LONG = "LAT_LONG";
    public static final String NAME_PLACE = "NAME_PLACE";

    @Override
    public void create() {
        tempC.set("fdfefe");

        String latLong = location1Fragment.getActivity().getIntent().getStringExtra(LAT_LONG)
                .replace("lat/lng: (","").replace(")","");
        final String namePlace = location1Fragment.getActivity().getIntent()
                .getStringExtra(NAME_PLACE);

        getDataOnTheLocalityUseCase.execute(latLong,
                new DisposableObserver<DataOnTheLocality>() {
                    @Override
                    public void onNext(@NonNull DataOnTheLocality dataOnTheLocalityModel) {
                        if (!(dataOnTheLocalityModel.getTempC()==null) ||
                                dataOnTheLocalityModel.getTempC().equals(""))
                            tempC.set(dataOnTheLocalityModel.getTempC());
                        else
                            tempC.set(NULL_STRING);

                        // вставляем искомую локацию
                        namePlaceObsF.set(namePlace);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("AAAA items = ", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("AAAA item", "onComplete");
                    }
                });
    }



    @Override
    public void destroyView() {
    }

    @Override
    public void viewCreated() {
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void activityCreated() {

    }
}
