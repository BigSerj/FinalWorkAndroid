package com.android.bigserj.data.database;



import com.android.bigserj.data.entity.LatLon;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;


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

    // сохраняем в бд так, что бы новое - обновилось, а старое удалилось (используется при удалении)
    public void saveArrayData(ArrayList<LatLon> latLonArrayList) {
        for (int i=0;i<latLonArrayList.size();i++)
            // либо сохраняем если небыло либо перезаписываем
            saveData(latLonArrayList.get(i));
        // удаляем последнюю строку
        removeTheFieldWithId(getCountRows());
    }



        // удаляем поле с указанным id и смещаем все что прпвее на одну строку назад
    public void removeTheFieldWithId(int id) {
        realm.beginTransaction();
        realm
                .where(LatLon.class)
                .equalTo("id", id)
                .findFirst()
                .deleteFromRealm();
        realm.commitTransaction();
    }

    public void slidePlusOneRow(int idBegin) {
        realm.beginTransaction();
        int pageCount = getCountRows();
//        saveData();
        for (int i = idBegin; i < pageCount; i++) {

        }
        realm.commitTransaction();
    }



    public void deleteAllDatabaseFields() {
        realm.beginTransaction();
        realm.delete(LatLon.class);
        realm.commitTransaction();
    }

}
