package com.pronovich.hotelbooking.util;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AmountUtility {

    /**
     * Calculation cost of living in the hotel
     * @param price - price a room per night
     * @param checkInDate  - check-in date in hotel
     * @param checkOutDate - check-out date from hotel
     * @return cost of living in the hotel in the period
     */
    public static BigDecimal calculateAmount(BigDecimal price, LocalDate checkInDate, LocalDate checkOutDate) {
        long daysNumber = LocalDateUtility.calculateDaysBetweenDates(checkInDate, checkOutDate);
        return price.multiply(BigDecimal.valueOf(daysNumber));
    }
}
