package com.orangeTree.bigserj.data.entity;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LatLon extends RealmObject implements DataModel{

    @PrimaryKey
    private int id;
    private String city;
    private String lat;
    private String lon;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
