package com.orangeTree.bigserj;


import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.util.Date;

public class DateParse {

    // конвертируем оригинальную дату в формат дата
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Date parseOriginalStringToDate(String originalDate) throws ParseException {
        return new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")
                .parse(originalDate);
    }

    // выдераем из даты     день недели
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String parseToDayOfWeek(String originalDate) throws ParseException {
        return new SimpleDateFormat("E")
                .format(parseOriginalStringToDate(originalDate));
    }

    // выдераем из даты     дд мм гггг
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String parseToCertainData(String originalDate) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy")
                .format(parseOriginalStringToDate(originalDate));
    }

    // выдераем из даты     время
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String parseToTime(String originalDate) throws ParseException {
        return new SimpleDateFormat("HH:mm:ss")
                .format(parseOriginalStringToDate(originalDate));
    }


    // выдераем из даты     день недели и время
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String parseToDayAndTime(String originalDate) throws ParseException {
        return new SimpleDateFormat("EEEE, HH:mm")
                .format(parseOriginalStringToDate(originalDate));
    }




}