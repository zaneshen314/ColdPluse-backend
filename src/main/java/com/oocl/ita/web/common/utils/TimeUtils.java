package com.oocl.ita.web.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static Date getCurrentTime() {
        return new Date();
    }

    public static Date stringToDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String dateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
