package com.pronovich.hotelbooking.utils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AmountUtils {

    public static BigDecimal calculateAmount(BigDecimal price, LocalDate checkInDate, LocalDate checkOutDate) {
        long daysNumber = LocalDateUtils.calculateDaysBetweenDates(checkInDate, checkOutDate);
        return price.multiply(BigDecimal.valueOf(daysNumber));
    }
}
