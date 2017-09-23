package com.android.bigserj;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static com.android.bigserj.Constants.PERMISSION_RATIONAL;
import static com.android.bigserj.Constants.REQUEST_PERMISSIONS_REQUEST_CODE;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class MyLocality {

    Activity myLocalityActivity;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location myLastLocation;

    public MyLocality(Activity myLocalityActivity) {
        this.myLocalityActivity = myLocalityActivity;
        init();
    }

    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(myLocalityActivity);
        if (!checkPermissions())
            requestPermissions();
        else
            getLastLocation();
    }




    //    Запрашиваем текущее местоположение
    @SuppressWarnings("MissingPermission")
    public Location getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(myLocalityActivity, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        // если нет ошибки получения геолокации
                        if (task.isSuccessful() && task.getResult() != null)
                            // сохраняем координаты
                            myLastLocation = task.getResult();
                        else {
                            // если есть ошибка - выводим соотв. сообщение
                            Log.w(TAG, "getLastLocation: exception", task.getException());
                            showSnackbar(PERMISSION_RATIONAL);
                        }
                    }
                });
        return myLastLocation;
    }


    //    Возвращает текущее состояние необходимых разрешений
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(myLocalityActivity,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(myLocalityActivity,
                new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(myLocalityActivity,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION);

        // делаем запрос на разрешение использования текущей геопозиции
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(PERMISSION_RATIONAL, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Запрос на получение геолокации
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // пропустить разрешение - например, пользователь установил "никогда не спрашивать"
            startLocationPermissionRequest();
        }
    }



    private void showSnackbar(String text){
        Toast.makeText(myLocalityActivity,text,Toast.LENGTH_SHORT).show();
    }

    private void showSnackbar(String mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(myLocalityActivity.findViewById(android.R.id.content),
                mainTextStringId,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(myLocalityActivity.getString(actionStringId), listener).show();
    }



}
