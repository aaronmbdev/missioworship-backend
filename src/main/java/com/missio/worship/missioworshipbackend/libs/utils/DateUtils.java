package com.missio.worship.missioworshipbackend.libs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static String PATTERN = "yyyy-MM-dd";

    public static String parseFrom(final Date date) {
        return new SimpleDateFormat(PATTERN).format(date);
    }

    public static Date parseFrom(final String date) throws ParseException {
        return new SimpleDateFormat(PATTERN).parse(date);
    }
}
