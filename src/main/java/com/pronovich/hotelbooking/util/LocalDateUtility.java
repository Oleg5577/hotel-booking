package com.pronovich.hotelbooking.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LocalDateUtility {

    public static long calculateDaysBetweenDates(LocalDate checkInDate, LocalDate checkOutDate) {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
}
