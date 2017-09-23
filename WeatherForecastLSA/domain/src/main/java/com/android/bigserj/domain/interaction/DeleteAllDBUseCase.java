package com.android.bigserj.domain.interaction;


import com.android.bigserj.data.database.RealmService;
import com.android.bigserj.domain.entity.LatLon;
import com.android.bigserj.domain.interaction.base.UseCase;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


public class DeleteAllDBUseCase extends UseCase<Void,Void> {

    @Override
    protected Observable<Void> buildUseCase(Void aVoid) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Void> e) throws Exception {
                RealmService realmService = new RealmService();
                realmService.deleteAllDatabaseFields();
                realmService.close();
            }
        });

    }

}
