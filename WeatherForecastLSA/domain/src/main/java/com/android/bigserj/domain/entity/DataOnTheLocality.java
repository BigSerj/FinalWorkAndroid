package com.android.bigserj.domain.entity;


public class DataOnTheLocality implements DomainModel{

    private DisplayLocation displayLocationModel = new DisplayLocation();

    // время измерения -
    private String observationTime;

    // текущая погода -
    private String weather;

    // текущая температура + F/C
    private String tempC;

    // текущая температура
    private String tempF;

    // текущая температура по ощущениям + F/C
    private String tempCFeelsLike;

    // текущее давление в миллибарах. для перевода в мм рт ст: * 0.75006375541921 + мм.рт.ст./мБар
    private String pressure_mb;

    // текущая влажность -
    private String humidity;

    // направление ветра (аббревиатура) -
    private String windDir;

    // направление ветра (градусы на окружности) -
    private String windDegrees;

    // скорость ветра +
    private String windSpeed;

    // порывы ветра до +
    private String windPoriviSreed;

    // ссылка на иконку соответствующую текущей погоде (gif) -
    private String iconUrl;


    public String getTempF() {
        return tempF;
    }

    public void setTempF(String tempF) {
        this.tempF = tempF;
    }
    public DisplayLocation getDisplayLocationModel() {
        return displayLocationModel;
    }

    public void setDisplayLocationModel(DisplayLocation displayLocationModel) {
        this.displayLocationModel = displayLocationModel;
    }

    public String getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
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

}
