package com.android.bigserj.di;



import com.android.bigserj.PlaceForm.PlaceFormPageViewModel;
import com.android.bigserj.PlaceForm.PlaceFormViewModel;
import com.android.bigserj.inLocality.Location1ViewModel;

import javax.inject.Singleton;

import dagger.Component;


// связующее звено между AppModule и UI (мост для того чтобы оно все работало)
@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    public void inject(Location1ViewModel location1ViewModel);
    public void inject(PlaceFormPageViewModel formPageViewModel);
    public void inject(PlaceFormViewModel placeFormViewModel);

}