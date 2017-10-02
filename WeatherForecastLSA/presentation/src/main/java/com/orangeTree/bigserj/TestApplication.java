package com.orangeTree.bigserj;


import android.app.Application;

import com.orangeTree.bigserj.di.AppComponent;
import com.orangeTree.bigserj.di.AppModule;
import com.orangeTree.bigserj.di.DaggerAppComponent;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

public class TestApplication extends Application{

    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Realm.init(this);

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }
}
