package com.android.bigserj.domain.entity;



public class DisplayLocation implements DomainModel{

    private String city;

    private String stateName;

    private String countryIso3166;

    private String latitude;

    private String longitude;

    private String elevation;


    public String getCity() {
        return city;
    }

    public String getStateName() {
        return stateName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public void setCountryIso3166(String countryIso3166) {
        this.countryIso3166 = countryIso3166;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getCountryIso3166() {
        return countryIso3166;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getElevation() {
        return elevation;
    }




}
