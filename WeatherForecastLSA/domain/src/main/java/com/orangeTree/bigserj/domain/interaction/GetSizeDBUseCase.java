package com.orangeTree.bigserj.domain.interaction;


import com.orangeTree.bigserj.data.database.RealmService;
import com.orangeTree.bigserj.domain.entity.LatLon;
import com.orangeTree.bigserj.domain.interaction.base.UseCase;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


public class GetSizeDBUseCase extends UseCase<Void,ArrayList<LatLon>> {

    @Override
    protected Observable<ArrayList<LatLon>> buildUseCase(Void aVoid) {
        return Observable.create(new ObservableOnSubscribe<ArrayList<LatLon>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ArrayList<LatLon>> e) throws Exception {
                RealmService realmService = new RealmService();
                ArrayList<com.orangeTree.bigserj.data.entity.LatLon> listLLData =
                        realmService.getAllLatLon();
                ArrayList<LatLon> arrayOfLatLonDomain = new ArrayList<>();
                // делаем копию из дата в домен ентити
                for (int i=0;i<listLLData.size();i++){
                    LatLon latLonDomain = new LatLon();
                    latLonDomain.setId(listLLData.get(i).getId());
                    latLonDomain.setCity(listLLData.get(i).getCity());
                    latLonDomain.setLat(listLLData.get(i).getLat());
                    latLonDomain.setLon(listLLData.get(i).getLon());
                    arrayOfLatLonDomain.add(latLonDomain);
                }
                realmService.close();
                e.onNext(arrayOfLatLonDomain);
                e.onComplete();
            }
        });
    }
}