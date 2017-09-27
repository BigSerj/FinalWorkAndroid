package com.android.bigserj.di;



import com.android.bigserj.PlaceForm.PlaceFormPageViewModel;
import com.android.bigserj.PlaceForm.PlaceFormViewModel;
import com.android.bigserj.myPlaces.MyPlacesViewModel;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    public void inject(PlaceFormPageViewModel formPageViewModel);
    public void inject(PlaceFormViewModel placeFormViewModel);
    public void inject(MyPlacesViewModel myPlacesViewModel);

}