package com.pronovich.hotelbooking.utils;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class LocalDateUtilsTest {

    @Test
    public void calculateDaysBetweenDatesCorrectTest() {
        LocalDate first = LocalDate.parse("2017-08-20");
        LocalDate second = LocalDate.parse("2017-08-21");
        long actual = LocalDateUtils.calculateDaysBetweenDates(first, second);
        assertEquals("Number of days is incorrect", 1, actual);
    }
}