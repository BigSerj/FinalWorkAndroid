package com.android.bigserj.domain.entity;


import java.util.ArrayList;

public class FoundWeather {

    private ArrayList temp;
    private ArrayList humidity;
    private ArrayList speedOfWind;
    private ArrayList pressure;


    public ArrayList getTemp() {
        return temp;
    }

    public void setTemp(ArrayList temp) {
        this.temp = temp;
    }

    public ArrayList getHumidity() {
        return humidity;
    }

    public void setHumidity(ArrayList humidity) {
        this.humidity = humidity;
    }

    public ArrayList getSpeedOfWind() {
        return speedOfWind;
    }

    public void setSpeedOfWind(ArrayList speedOfWind) {
        this.speedOfWind = speedOfWind;
    }

    public ArrayList getPressure() {
        return pressure;
    }

    public void setPressure(ArrayList pressure) {
        this.pressure = pressure;
    }
}
