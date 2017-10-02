package com.orangeTree.bigserj.data.database;



import com.orangeTree.bigserj.data.entity.LatLon;

import java.util.ArrayList;

import io.realm.Realm;


public class RealmService {

    private Realm realm;

    public RealmService() {
        init();
    }

    // инициализируем realm
    private void init() {
        realm = Realm.getDefaultInstance();
    }

    // закрываем realm
    public void close() {
        realm.close();
    }


    // возвращаем строку по id
    public LatLon loadByIdData(int id) {
        return realm
                .where(LatLon.class)
                .equalTo("id", id)
                .findFirst();
    }

    // получаем количество строк в бд
    public Integer getCountRows() {
        Number id = realm.where(LatLon.class).max("id");
        return (id == null) ? 0 : id.intValue() + 1;
    }
    // возвращаем лист всех объектов с данными
    public ArrayList<LatLon> getAllLatLon(){
        ArrayList<LatLon> listLL = new ArrayList<>();
        for (int i=0;i<getCountRows();i++) {
            if (loadByIdData(i)!=null)
                listLL.add(loadByIdData(i));
        }
        return listLL;
    }


    // сохраняем новый объект в бд
    public void saveData(LatLon latLon) {
        LatLon latLon1 = loadByIdData(latLon.getId());
        if (latLon1 == null) {
            realm.beginTransaction();
            latLon1 = realm.createObject(LatLon.class, latLon.getId());
            latLon1.setCity(latLon.getCity());
            latLon1.setLat(latLon.getLat());
            latLon1.setLon(latLon.getLon());
            realm.commitTransaction();
        }
        // если строка существует - перезаписываем ее
        else {
            realm.beginTransaction();
            latLon1.setCity(latLon.getCity());
            latLon1.setLat(latLon.getLat());
            latLon1.setLon(latLon.getLon());
            realm.commitTransaction();
        }
    }

    // пересохраняем в бд
    public void saveArrayData(ArrayList<LatLon> latLonArrayList) {
        deleteAllDatabaseFields();
        for (int i=0;i<latLonArrayList.size();i++)
            saveData(latLonArrayList.get(i));
    }

    public void deleteAllDatabaseFields() {
        realm.beginTransaction();
        realm.delete(LatLon.class);
        realm.commitTransaction();
    }

}
