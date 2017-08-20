package com.pronovich.hotelbooking.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LocalDateUtils {

    public static long calculateDaysBetweenDates(LocalDate checkInDate, LocalDate checkOutDate) {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
}
