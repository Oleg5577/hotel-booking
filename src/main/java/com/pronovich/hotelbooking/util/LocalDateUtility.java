package com.pronovich.hotelbooking.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LocalDateUtility {

    /**
     * Number of days between two days
     * @param checkInDate - check-in date in hotel
     * @param checkOutDate - check-out date from hotel
     * @return  Number of days
     */
    public static long calculateDaysBetweenDates(LocalDate checkInDate, LocalDate checkOutDate) {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
}
