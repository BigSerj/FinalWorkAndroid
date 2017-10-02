package com.orangeTree.bigserj.data.net;


import com.orangeTree.bigserj.data.entity.DataOnTheLocality;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface RestApi {
    // возвращает все поля
    @GET("{latLong}.json")
    Observable<DataOnTheLocality> getStationId(@Path("latLong") String latLong);
}