package com.orangeTree.bigserj.data.net;

import com.orangeTree.bigserj.data.entity.DataOnTheLocality;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.orangeTree.bigserj.data.ConstantsData.*;

// тут нсатроим ретрофит и тут будут лежать сервисы которые мы настроим из слоя domain
public class RestService {
    private static final RestService instance = new RestService();

    private RestApi restApi;

    public static RestService getInstance() {
        return instance;
    }

    private RestService() {
        init();
    }




    private void init() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)  // если за 10 сек не произойдет считывания с серверы какой-либо инфы
                .connectTimeout(10, TimeUnit.SECONDS)  // будет ошибка если за 10 сек не произойдет подключение к серверу
                .addInterceptor(logging) // класс который в промежутке между тем чтобы отправить данные производит некоторые действия с объектом
                .build();


        // gson либа для парсинга json
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DataOnTheLocality.class,
                new CurrentObservationDeserializer<DataOnTheLocality>())
                .create();


        //
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CONDITIONS_URL_EN)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();


        restApi = retrofit.create(RestApi.class);




    }



    // возвращает лист
    public Observable<DataOnTheLocality> getStationId(String latLong){
        return restApi.getStationId(latLong);
    }

}