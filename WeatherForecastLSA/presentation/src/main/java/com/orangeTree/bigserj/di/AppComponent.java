package com.orangeTree.bigserj.di;



import com.orangeTree.bigserj.PlaceForm.PlaceFormPageViewModel;
import com.orangeTree.bigserj.PlaceForm.PlaceFormViewModel;
import com.orangeTree.bigserj.myPlaces.MyPlacesViewModel;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

     void inject(PlaceFormPageViewModel formPageViewModel);
     void inject(PlaceFormViewModel placeFormViewModel);
     void inject(MyPlacesViewModel myPlacesViewModel);

}