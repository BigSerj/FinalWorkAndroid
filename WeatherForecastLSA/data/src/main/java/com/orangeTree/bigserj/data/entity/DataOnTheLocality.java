package com.orangeTree.bigserj.data.entity;


import com.google.gson.annotations.SerializedName;


public class DataOnTheLocality implements DataModel{


    // Лист с данными о запрошенной локации
    @SerializedName("display_location")
    private DisplayLocation displayLocation = new DisplayLocation();

    // время измерения
    @SerializedName("observation_time_rfc822")
    private String observationTime;

    // текущая погода
    @SerializedName("weather")
    private String weather;

    // текущая температура ы градусах цельсия
    @SerializedName("temp_c")
    private String tempC;

    // текущая температура
    @SerializedName("temp_f")
    private String tempF;

    // текущая температура по ощущениям C
    @SerializedName("feelslike_c")
    private String tempCFeelsLike;

    // текущая температура по ощущениям F
    @SerializedName("feelslike_f")
    private String tempFFeelsLike;

    // текущее давление в миллибарах. для перевода в мм рт ст: * 0.75006375541921
    @SerializedName("pressure_mb")
    private String pressure_mb;

    // текущая влажность
    @SerializedName("relative_humidity")
    private String humidity;

    // направление ветра (аббревиатура)
    @SerializedName("wind_dir")
    private String windDir;

    // направление ветра (градусы на окружности)
    @SerializedName("wind_degrees")
    private String windDegrees;

    // скорость ветра
    @SerializedName("wind_kph")
    private String windSpeed;

    // скорость ветра
    @SerializedName("wind_mph")
    private String windSpeedMPH;

    // порывы ветра до
    @SerializedName("wind_gust_kph")
    private String windPoriviSreed;

    // ссылка на иконку соответствующую текущей погоде (gif)
    @SerializedName("icon_url")
    private String iconUrl;

    public String getTempF() {
        return tempF;
    }

    public void setTempF(String tempF) {
        this.tempF = tempF;
    }
    public DisplayLocation getDisplayLocation() {
        return displayLocation;
    }

    public void setDisplayLocation(DisplayLocation displayLocation) {
        this.displayLocation = displayLocation;
    }

    public String getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }


    public String getWindSpeedMPH() {
        return windSpeedMPH;
    }

    public void setWindSpeedMPH(String windSpeedMPH) {
        this.windSpeedMPH = windSpeedMPH;
    }


    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public String getTempCFeelsLike() {
        return tempCFeelsLike;
    }

    public void setTempCFeelsLike(String tempCFeelsLike) {
        this.tempCFeelsLike = tempCFeelsLike;
    }

    public String getPressure_mb() {
        return pressure_mb;
    }

    public void setPressure_mb(String pressure_mb) {
        this.pressure_mb = pressure_mb;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWindDegrees() {
        return windDegrees;
    }

    public void setWindDegrees(String windDegrees) {
        this.windDegrees = windDegrees;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindPoriviSreed() {
        return windPoriviSreed;
    }

    public void setWindPoriviSreed(String windPoriviSreed) {
        this.windPoriviSreed = windPoriviSreed;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTempFFeelsLike() {
        return tempFFeelsLike;
    }

    public void setTempFFeelsLike(String tempFFeelsLike) {
        this.tempFFeelsLike = tempFFeelsLike;
    }
}
