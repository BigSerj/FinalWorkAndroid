package com.orangeTree.bigserj;


import android.util.Log;

import com.orangeTree.bigserj.domain.entity.DataOnTheLocality;

import static com.orangeTree.bigserj.Constants.*;
import static com.orangeTree.bigserj.SettingsHelper.*;

public class PageSettingsAdapter {



    private DataOnTheLocality dataOnTheLocality;


    private boolean isCelsius;
    private boolean isMercury;
    private boolean isMPH;
    private int pressureInMMOMercury;
    private int windKPHToMPS;

    private boolean isMinus0=false;
    private boolean isFeelsLikeMinus0=false;

    public boolean isMinus0() {
        return isMinus0;
    }

    public boolean isFeelsLikeMinus0() {
        return isFeelsLikeMinus0;
    }

    // только параметризированный конструктор
    private PageSettingsAdapter(){}
    public PageSettingsAdapter(DataOnTheLocality dataOnTheLocality){
        this.dataOnTheLocality = dataOnTheLocality;
        initReload();
    }

    // инициализируем начальные данные
    public void initReload(){
        updateGets();
        pressureInMMOMercury = getPressureInMillimetersOfMercury();
        windKPHToMPS = getWindKPHToMPS();
    }
    private void updateGets(){
        isCelsius = getIsCelsius();
        isMercury = getIsMercury();
        isMPH = getIsMPS();
    }
    public boolean isCelsius() {
        return isCelsius;
    }



    // конвертируем из миллибар в мм рт ст
    private int getPressureInMillimetersOfMercury(){
        try{
            pressureInMMOMercury = getRoundedValue(Float.valueOf(dataOnTheLocality.getPressure_mb())
                    *COEFFICIENT_FOR_CONVERT_PRESSURE);
        }catch (Exception e){
            Log.e("PageSettingsAdptr error","Error by converting in millimeters of mercury");
        }
        return pressureInMMOMercury;
    }

    // конвертируем километры в час в метры в секунду
    private int getWindKPHToMPS() {
        try{
            windKPHToMPS = getRoundedValue(Float.valueOf(dataOnTheLocality.getWindSpeed())
                    *COEFFICIENT_FOR_CONVERT_WIND);
        }catch (Exception e){
            Log.e("PageSettingsAdptr error","Error by converting in millimeters of mercury");
        }
        return windKPHToMPS;
    }

    // получаем текущее состояние настроек
    private boolean getIsCelsius(){
        return tempCurrentState.equals(STATE_TEMP.CELSIUS);
    }
    private boolean getIsMercury(){
        return pressureCurrentState.equals(STATE_PRESSURE.MILLIMETERS_OF_MERCURY);
    }
    private boolean getIsMPS(){
        return windCurrentState.equals(STATE_WIND.M_PER_HOUR);
    }



    private int getRoundedValue(Float floats){
        return Math.round(floats);
    }
    private Integer floatToInt(String floats){
        Float f = Float.valueOf(floats);
        return getRoundedValue(f);
    }


    public Integer getTemp(){
        updateGets();
        Integer temp=null;
        try {
            if (isCelsius) {
                temp = floatToInt(dataOnTheLocality.getTempC());
                isMinus0 = temp < 0;
                if (isMinus0)
                    return Math.abs(temp);
            }else
                return floatToInt(dataOnTheLocality.getTempF());
        }catch (Exception e){
            Log.e("PageSettingsAdptr error","Get temp error.");
        }
        return temp;
    }

    public Integer getTempFeelsLike(){
        updateGets();
        Integer temp=null;
        try {
            if (isCelsius) {
                temp = floatToInt(dataOnTheLocality.getTempCFeelsLike());
                isFeelsLikeMinus0 = temp < 0;
                if (isFeelsLikeMinus0)
                    return Math.abs(temp);
            }else
                return floatToInt(dataOnTheLocality.getTempFFeelsLike());
        }catch (Exception e){
            Log.e("PageSettingsAdptr error","Get temp feelslike.");
        }
        return temp;
    }


    public Integer getPressure(){
        updateGets();
        try{
            if (isMercury){
                return getPressureInMillimetersOfMercury();
            }else
                return floatToInt(dataOnTheLocality.getPressure_mb());
        }catch (Exception e){
            Log.e("PageSettingsAdptr error","Get pressure error.");
        }
        return null;
    }

    public Integer getWindSpeed(){
        updateGets();
        try{
            if (isMPH){
                return Integer.valueOf(dataOnTheLocality.getWindSpeedMPH());
            }else
                return Integer.valueOf(dataOnTheLocality.getWindSpeed());
        }catch (Exception e){
            Log.e("PageSettingsAdptr error","Get wind speed.");
        }
        return null;
    }

}
