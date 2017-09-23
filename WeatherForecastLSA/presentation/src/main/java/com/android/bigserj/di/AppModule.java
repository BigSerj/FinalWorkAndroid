package com.android.bigserj.di;


import com.android.bigserj.domain.interaction.DeleteAllDBUseCase;
import com.android.bigserj.domain.interaction.GetDataOnTheLocalityUseCase;
import com.android.bigserj.domain.interaction.GetLatLonUseCase;
import com.android.bigserj.domain.interaction.GetSizeDBUseCase;
import com.android.bigserj.domain.interaction.SetArrayLatLonToTheDBWheneDeleteUseCase;
import com.android.bigserj.domain.interaction.SetLatLonToTheDBUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


// тут мы рассказываем даггеру как нужно создавать наш объект
@Module
public class AppModule {

    @Provides
    @Singleton
    public GetDataOnTheLocalityUseCase getDataOnTheLocalityUseCase(){
        return new GetDataOnTheLocalityUseCase();
    }

    @Provides
    @Singleton
    public GetSizeDBUseCase getSizeDBUseCase(){
        return new GetSizeDBUseCase();
    }

    @Provides
    @Singleton
    public GetLatLonUseCase getLatLonUseCase(){
        return new GetLatLonUseCase();
    }


    @Provides
    @Singleton
    public SetLatLonToTheDBUseCase setLatLonToTheDBUseCase(){
        return new SetLatLonToTheDBUseCase();
    }

    @Provides
    @Singleton
    public SetArrayLatLonToTheDBWheneDeleteUseCase setArrayLatLonToTheDBWheneDeleteUseCase(){
        return new SetArrayLatLonToTheDBWheneDeleteUseCase();
    }

    @Provides
    @Singleton
    public DeleteAllDBUseCase deleteAllDBUseCase(){
        return new DeleteAllDBUseCase();
    }

}
