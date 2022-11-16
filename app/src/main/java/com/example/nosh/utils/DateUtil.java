package com.example.nosh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class DateUtil {

    final static private SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.CANADA
    );

    /**
     * Return String representation of a Date object in yyyy-mm-dd
     */
    public static String formatDate(Date date) {
        return formatter.format(date);
    }

    /**
     * Obtain a Calendar object from a string date with format yyyy-mm-dd
     */
    public static Calendar getCalendar(String strDate) {
        final Calendar c = Calendar.getInstance();

        try {
            c.setTime(Objects.requireNonNull(formatter.parse(strDate)));
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        return c;
    }

    public static long dayDifferences(Date start, Date end) {
        return TimeUnit.DAYS.convert(end.getTime() - start.getTime(),
                TimeUnit.MICROSECONDS);
    }
}
