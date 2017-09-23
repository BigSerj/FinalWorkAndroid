package com.android.bigserj.data.net;


import com.android.bigserj.data.entity.DataOnTheLocality;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestApi {



    // возвращает все поля
    @GET("{latLong}.json")
    Observable<DataOnTheLocality> getStationId(@Path("latLong") String latLong);


//    // возвращает все профили
//    @GET("data/profile")
//    Observable<List<DataOnTheLocality>> getProfiles();

//    // возвращает один профиль "resourceName"
//    @GET("data/profile/{id}")
//    Observable<DataOnTheLocality> getProfile(@Path("id") String id);
//
//    // значит этот объект профиля который подадим будет конверирован в json и передан в тело запроса
//    @POST("data/profile")
//    Observable<Void> saveNewProfile(@Body DataOnTheLocality profile);
//
//    // редактируем
//    @PUT("data/profile/{id}")
//    Observable<Void> editProfile(@Path("id") String id, @Body DataOnTheLocality profile);
}



// ПАТТЕРН REPOSITORY всунуть в data-модуль !!!