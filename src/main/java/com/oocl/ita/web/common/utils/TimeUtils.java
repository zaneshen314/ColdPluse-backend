package com.oocl.ita.web.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static Date getCurrentTime() {
        return new Date();
    }

    public static Date stringToDate(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String dateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
}
