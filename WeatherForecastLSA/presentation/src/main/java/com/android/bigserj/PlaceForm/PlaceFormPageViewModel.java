package com.android.bigserj.PlaceForm;


import android.databinding.ObservableField;
import android.util.Log;

import com.android.bigserj.TestApplication;
import com.android.bigserj.base.BaseViewModelFragment;
import com.android.bigserj.data.entity.LatLon;
import com.android.bigserj.domain.entity.DataOnTheLocality;
import com.android.bigserj.domain.interaction.GetDataOnTheLocalityUseCase;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

public class PlaceFormPageViewModel implements BaseViewModelFragment {

    public PlaceFormPageFragment placeFormPageFragment;
    public PlaceFormPageViewModel(PlaceFormPageFragment newPlaceFormPageFragment){
        this.placeFormPageFragment = newPlaceFormPageFragment;
        TestApplication.appComponent.inject(this);
    }
    @Inject
    GetDataOnTheLocalityUseCase getDataOnTheLocalityUseCase;




    public ObservableField<String> newTempC = new ObservableField<>();
    public ObservableField<String> pageNumb = new ObservableField<>();


    String lat;
    String lon;
    String city;
    private String latlonThis;





    @Override
    public void create() {
    }

    @Override
    public void destroyView() {
        getDataOnTheLocalityUseCase.dispose();
    }

    @Override
    public void viewCreated() {

//            LatLon ob0 = new LatLon();
//            LatLon ob1 = new LatLon();
//            LatLon ob2 = new LatLon();
//            LatLon ob3 = new LatLon();
//            LatLon ob4 = new LatLon();
//            LatLon ob5 = new LatLon();
//            ob0.setLat(lat);// Текущее
//            ob0.setLon(lon);
//            latLonModels.add(ob0);
//            ob1.setLat("55.16999817");// Витебск
//            ob1.setLon("30.21999931");
//            latLonModels.add(ob1);
//            ob2.setLat("54.88333130");// Лепель
//            ob2.setLon("28.70000076");
//            latLonModels.add(ob2);
//            ob3.setLat("53.914078");// Минск
//            ob3.setLon("27.582375");
//            latLonModels.add(ob3);
//            ob4.setLat("53.625481");// Гродно
//            ob4.setLon("23.736858");
//            latLonModels.add(ob4);
//            ob5.setLat("52.06666565");// Брест
//            ob5.setLon("23.61666679");
//            latLonModels.add(ob5);




        LatLon latLon = new LatLon();

            StringBuilder sb = new StringBuilder();
            if (PlaceFormViewModel.currentPage == 0)
                latlonThis = sb.append(lat).append(",").append(lon).toString();
            else
                latlonThis = sb.append(latLon.getLat()).append(",").append(latLon.getLon()).toString();

            getDataOnTheLocalityUseCase.execute(latlonThis,
                    new DisposableObserver<DataOnTheLocality>() {
                        @Override
                        public void onNext(@NonNull DataOnTheLocality dataOnTheLocalityModel) {
                            PlaceFormViewModel.cityPlaceForm =
                                    dataOnTheLocalityModel.getDisplayLocationModel().getCity();

                            newTempC.set(dataOnTheLocalityModel.getDisplayLocationModel().getStateName());
//                            pageNumb.set(String.valueOf(pageNumb));

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
//    }

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
