package com.android.bigserj;


import android.app.Application;

import com.android.bigserj.di.AppComponent;
import com.android.bigserj.di.AppModule;
import com.android.bigserj.di.DaggerAppComponent;

import io.realm.Realm;


// тут инициализируется все что нужно при старте, как входная точка main в джаве
public class TestApplication extends Application{

    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }
}
