package com.android.bigserj;


import android.util.Log;

import com.android.bigserj.domain.entity.DataOnTheLocality;

import static com.android.bigserj.Constants.*;
import static com.android.bigserj.PlaceForm.PlaceFormSettingsViewModel.state_pressure;
import static com.android.bigserj.PlaceForm.PlaceFormSettingsViewModel.state_temp;
import static com.android.bigserj.PlaceForm.PlaceFormSettingsViewModel.state_wind;
import static com.android.bigserj.SettingsHelper.*;

public class PageSettingsAdapter {



    private DataOnTheLocality dataOnTheLocality;
    private boolean isCelsius;
    private boolean isMercury;
    private boolean isMPS;
    private int pressureInMMOMercury;
    private int windKPHToMPS;

    private boolean isMinus0=false;

    public boolean isMinus0() {
        return isMinus0;
    }


    // только параметризированный конструктор
    private PageSettingsAdapter(){}
    public PageSettingsAdapter(DataOnTheLocality dataOnTheLocality){
        this.dataOnTheLocality = dataOnTheLocality;
        init();
    }

    // инициализируем начальные данные
    private void init(){
        updateGets();
        pressureInMMOMercury = getPressureInMillimetersOfMercury();
        windKPHToMPS = getWindKPHToMPS();
    }
    private void updateGets(){
        isCelsius = getIsCelsius();
        isMercury = getIsMercury();
        isMPS = getIsMPS();
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
        return state_temp.get().equals(STATE_TEMP.CELSIUS);
    }
    private boolean getIsMercury(){
        return state_pressure.get().equals(STATE_PRESSURE.MILLIMETERS_OF_MERCURY);
    }
    private boolean getIsMPS(){
        return state_wind.get().equals(STATE_WIND.M_PER_SEC);
    }



    private int getRoundedValue(Float floats){
        return Math.round(floats);
    }
    private Integer floatToInt(String floats){
        Float f = Float.valueOf(floats);
        return getRoundedValue(f);
    }

    public boolean isCelsius() {
        return isCelsius;
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


    public Integer getPressure(){
        updateGets();
        try{
            if (isMercury){
                return getPressureInMillimetersOfMercury();
            }else
                return floatToInt(dataOnTheLocality.getPressure_mb());
        }catch (Exception e){
            Log.e("PageSettingsAdptr error","Get temp error.");
        }
        return null;
    }

    public Integer getWindSpeed(){
        updateGets();
        try{
            if (!isMPS){
                return getWindKPHToMPS();
            }else
                return Integer.valueOf(dataOnTheLocality.getWindSpeed());
        }catch (Exception e){
            Log.e("PageSettingsAdptr error","Get temp error.");
        }
        return null;
    }

}
