package com.orangeTree.bigserj.domain.interaction;


import com.orangeTree.bigserj.data.database.RealmService;
import com.orangeTree.bigserj.domain.interaction.base.UseCase;

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
