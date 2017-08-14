package com.android.bigserj.domain.interaction;


import com.android.bigserj.domain.entity.FoundWeather;
import com.android.bigserj.domain.entity.Locality;
import com.android.bigserj.domain.interaction.base.UseCase;

import java.util.ArrayList;

public class FoundWeatherUseCase extends UseCase<Locality, FoundWeather> {
    @Override
    protected FoundWeather buildUseCase() {

        Locality locality = new Locality();



        FoundWeather foundWeather = new FoundWeather();

        ArrayList<Integer> temp = new ArrayList<>();
        ArrayList<Integer> humidity = new ArrayList<>();
        ArrayList<Integer> speedOfWind = new ArrayList<>();
        ArrayList<Integer> pressure = new ArrayList<>();



        // сегодня
        temp.add(19);
        humidity.add(60);
        speedOfWind.add(1);
        pressure.add(750);

        // завтра
        temp.add(20);
        humidity.add(61);
        speedOfWind.add(2);
        pressure.add(751);

        // послезавтра
        temp.add(21);
        humidity.add(62);
        speedOfWind.add(3);
        pressure.add(752);



        foundWeather.setTemp(temp);
        foundWeather.setHumidity(humidity);
        foundWeather.setSpeedOfWind(speedOfWind);
        foundWeather.setPressure(pressure);

        return foundWeather;
    }
}
