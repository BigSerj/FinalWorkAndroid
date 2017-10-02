package com.orangeTree.bigserj.domain.interaction;


import com.orangeTree.bigserj.data.database.RealmService;
import com.orangeTree.bigserj.domain.entity.LatLon;
import com.orangeTree.bigserj.domain.interaction.base.UseCase;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

public class SetArrayLatLonToTheDBWhenDeleteUseCase extends UseCase<ArrayList<LatLon>, Void> {

    @Override
    protected Observable<Void> buildUseCase(final ArrayList<LatLon> latLonArrayList) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Void> e) throws Exception {
                RealmService realmService = new RealmService();
                realmService.saveArrayData(convertDomainToData(latLonArrayList));
                realmService.close();
            }
        });
    }

    // конвертируем из domain в data
    private ArrayList<com.orangeTree.bigserj.data.entity.LatLon> convertDomainToData(
            ArrayList<LatLon> latLonArrayListDomain) {
        ArrayList<com.orangeTree.bigserj.data.entity.LatLon> latLonArrayListData = new ArrayList<>();
        for (int i = 0; i < latLonArrayListDomain.size(); i++) {
            com.orangeTree.bigserj.data.entity.LatLon latLonData =
                    new com.orangeTree.bigserj.data.entity.LatLon();
            latLonData.setId(latLonArrayListDomain.get(i).getId());
            latLonData.setCity(latLonArrayListDomain.get(i).getCity());
            latLonData.setLat(latLonArrayListDomain.get(i).getLat());
            latLonData.setLon(latLonArrayListDomain.get(i).getLon());
            latLonArrayListData.add(latLonData);
        }
        return latLonArrayListData;
    }

}
