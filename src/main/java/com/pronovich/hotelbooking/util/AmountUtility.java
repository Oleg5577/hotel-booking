package com.pronovich.hotelbooking.util;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AmountUtility {

    public static BigDecimal calculateAmount(BigDecimal price, LocalDate checkInDate, LocalDate checkOutDate) {
        long daysNumber = LocalDateUtility.calculateDaysBetweenDates(checkInDate, checkOutDate);
        return price.multiply(BigDecimal.valueOf(daysNumber));
    }
}
