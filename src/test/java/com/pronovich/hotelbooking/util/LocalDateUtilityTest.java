package com.pronovich.hotelbooking.util;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class LocalDateUtilityTest {

    @Test
    public void calculateDaysBetweenDatesCorrectTest() {
        LocalDate checkInDate = LocalDate.parse("2017-08-20");
        LocalDate checkOutDate = LocalDate.parse("2017-08-21");
        long actual = LocalDateUtility.calculateDaysBetweenDates(checkInDate, checkOutDate);
        assertEquals("Number of days is incorrect", 1, actual);
    }

    @Test
    public void calculateDaysBetweenDatesWrongTest() {
        LocalDate checkInDate = LocalDate.parse("2017-08-20");
        LocalDate checkOutDate = LocalDate.parse("2017-08-21");
        long actual = LocalDateUtility.calculateDaysBetweenDates(checkInDate, checkOutDate);
        assertNotEquals("Number of days is incorrect", 0, actual);
    }
}
