package com.android.bigserj.domain.interaction;


import com.android.bigserj.data.database.RealmService;
import com.android.bigserj.domain.entity.LatLon;
import com.android.bigserj.domain.interaction.base.UseCase;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


public class GetLatLonUseCase extends UseCase<Integer,LatLon> {

    @Override
    protected Observable<LatLon> buildUseCase(final Integer integer) {

        return Observable.create(new ObservableOnSubscribe<LatLon>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<LatLon> e) throws Exception {
                RealmService realmService = new RealmService();
                com.android.bigserj.data.entity.LatLon latLon = realmService.loadByIdData(integer);
                realmService.close();
                if (latLon != null) {
                    e.onNext(convertDataToDomain(latLon));
                    e.onComplete();
                } else {
                    e.onError(new Throwable("user with id = " + integer + " is not found"));
                }
            }
        });
    }


    // конвертируем из data в domain
    private com.android.bigserj.domain.entity.LatLon convertDataToDomain(com.android.bigserj.data.entity.LatLon latLonData) {
        com.android.bigserj.domain.entity.LatLon latLonDomain = new com.android.bigserj.domain.entity.LatLon();
        latLonDomain.setId(latLonData.getId());
        latLonDomain.setCity(latLonData.getCity());
        latLonDomain.setLat(latLonData.getLat());
        latLonDomain.setLon(latLonData.getLon());
        return latLonDomain;
    }
}
