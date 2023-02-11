package com.missio.worship.missioworshipbackend.libs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static String PATTERN = "yyyy-MM-dd";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);

    public static String parseFrom(final Date date) {
        return simpleDateFormat.format(date);
    }

    public static Date parseFrom(final String date) throws ParseException {
        return simpleDateFormat.parse(date);
    }
}
