package com.android.bigserj.domain.interaction;


import com.android.bigserj.data.database.RealmService;
import com.android.bigserj.domain.entity.LatLon;
import com.android.bigserj.domain.interaction.base.UseCase;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

public class SetLatLonToTheDBUseCase extends UseCase<LatLon, Void> {

    @Override
    protected Observable<Void> buildUseCase(final LatLon latLon) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Void> e) throws Exception {
                RealmService realmService = new RealmService();
                realmService.saveData(convertDomainToData(latLon));
                realmService.close();
            }
        });
    }

    // конвертируем из domain в data
    private com.android.bigserj.data.entity.LatLon convertDomainToData(com.android.bigserj.domain.entity.LatLon latLonDomain) {
        com.android.bigserj.data.entity.LatLon latLonData = new com.android.bigserj.data.entity.LatLon();
        latLonData.setId(latLonDomain.getId());
        latLonData.setCity(latLonDomain.getCity());
        latLonData.setLat(latLonDomain.getLat());
        latLonData.setLon(latLonDomain.getLon());
        return latLonData;
    }

}
