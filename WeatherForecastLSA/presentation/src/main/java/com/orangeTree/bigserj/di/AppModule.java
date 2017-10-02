package com.orangeTree.bigserj.di;


import com.orangeTree.bigserj.domain.interaction.DeleteAllDBUseCase;
import com.orangeTree.bigserj.domain.interaction.GetDataOnTheLocalityUseCase;
import com.orangeTree.bigserj.domain.interaction.GetLatLonUseCase;
import com.orangeTree.bigserj.domain.interaction.GetSizeDBUseCase;
import com.orangeTree.bigserj.domain.interaction.SetArrayLatLonToTheDBWhenDeleteUseCase;
import com.orangeTree.bigserj.domain.interaction.SetLatLonToTheDBUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


// тут мы рассказываем даггеру как нужно создавать наш объект
@Module
public class AppModule {

    @Provides
    @Singleton
    GetDataOnTheLocalityUseCase getDataOnTheLocalityUseCase(){
        return new GetDataOnTheLocalityUseCase();
    }

    @Provides
    @Singleton
    GetSizeDBUseCase getSizeDBUseCase(){
        return new GetSizeDBUseCase();
    }

    @Provides
    @Singleton
    GetLatLonUseCase getLatLonUseCase(){
        return new GetLatLonUseCase();
    }


    @Provides
    @Singleton
    SetLatLonToTheDBUseCase setLatLonToTheDBUseCase(){
        return new SetLatLonToTheDBUseCase();
    }

    @Provides
    @Singleton
    SetArrayLatLonToTheDBWhenDeleteUseCase setArrayLatLonToTheDBWheneDeleteUseCase(){
        return new SetArrayLatLonToTheDBWhenDeleteUseCase();
    }

    @Provides
    @Singleton
    DeleteAllDBUseCase deleteAllDBUseCase(){
        return new DeleteAllDBUseCase();
    }

}
