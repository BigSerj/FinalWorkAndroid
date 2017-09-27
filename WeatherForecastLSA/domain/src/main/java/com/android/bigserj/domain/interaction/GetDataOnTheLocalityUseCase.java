package com.android.bigserj.domain.interaction;


import com.android.bigserj.data.net.RestService;
import com.android.bigserj.domain.entity.DataOnTheLocality;
import com.android.bigserj.domain.interaction.base.UseCase;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class GetDataOnTheLocalityUseCase extends UseCase<String, DataOnTheLocality> {

    @Override
    protected Observable<DataOnTheLocality> buildUseCase(String latLong) {
        return RestService
                .getInstance()
                .getStationId(latLong)
                .map(new Function<com.android.bigserj.data.entity.DataOnTheLocality, DataOnTheLocality>() {
                    @Override
                    public DataOnTheLocality apply(@NonNull com.android.bigserj.data.entity.DataOnTheLocality dataOnTheLocality) throws Exception {

                        DataOnTheLocality dataOnTheLocalityModel = new DataOnTheLocality();

                        dataOnTheLocalityModel.getDisplayLocationModel().setCity(dataOnTheLocality.getDisplayLocation().getCity());
                        dataOnTheLocalityModel.getDisplayLocationModel().setStateName(dataOnTheLocality.getDisplayLocation().getStateName());
                        dataOnTheLocalityModel.getDisplayLocationModel().setCountryIso3166(dataOnTheLocality.getDisplayLocation().getCountryIso3166());
                        dataOnTheLocalityModel.getDisplayLocationModel().setLatitude(dataOnTheLocality.getDisplayLocation().getLatitude());
                        dataOnTheLocalityModel.getDisplayLocationModel().setLongitude(dataOnTheLocality.getDisplayLocation().getLongitude());
                        dataOnTheLocalityModel.getDisplayLocationModel().setElevation(dataOnTheLocality.getDisplayLocation().getElevation());

                        dataOnTheLocalityModel.setObservationTime(dataOnTheLocality.getObservationTime());
                        dataOnTheLocalityModel.setWeather(dataOnTheLocality.getWeather());
                        dataOnTheLocalityModel.setTempC(dataOnTheLocality.getTempC());
                        dataOnTheLocalityModel.setTempF(dataOnTheLocality.getTempF());
                        dataOnTheLocalityModel.setTempCFeelsLike(dataOnTheLocality.getTempCFeelsLike());
                        dataOnTheLocalityModel.setPressure_mb(dataOnTheLocality.getPressure_mb());
                        dataOnTheLocalityModel.setHumidity(dataOnTheLocality.getHumidity());
                        dataOnTheLocalityModel.setWindDir(dataOnTheLocality.getWindDir());
                        dataOnTheLocalityModel.setWindDegrees(dataOnTheLocality.getWindDegrees());
                        dataOnTheLocalityModel.setWindSpeed(dataOnTheLocality.getWindSpeed());
                        dataOnTheLocalityModel.setWindPoriviSreed(dataOnTheLocality.getWindPoriviSreed());
                        dataOnTheLocalityModel.setIconUrl(dataOnTheLocality.getIconUrl());

                        return dataOnTheLocalityModel;
                    }
                });

    }
}
