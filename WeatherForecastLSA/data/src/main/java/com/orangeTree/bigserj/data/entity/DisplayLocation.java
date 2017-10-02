package com.orangeTree.bigserj.data.entity;


import com.google.gson.annotations.SerializedName;

public class DisplayLocation implements DataModel{

    @SerializedName("city")
    private String city;

    @SerializedName("state_name")
    private String stateName;

    @SerializedName("country_iso3166")
    private String countryIso3166;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("elevation")
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
