package com.pronovich.hotelbooking.util;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class AmountUtilityTest {

    @Test
    public void calculateAmountCorrect() {
        BigDecimal price = BigDecimal.valueOf(50);
        LocalDate checkInDate = LocalDate.parse("2017-08-20");
        LocalDate checkOutDate = LocalDate.parse("2017-08-22");
        BigDecimal expectedAmount = BigDecimal.valueOf(100);
        BigDecimal actualAmount = AmountUtility.calculateAmount(price, checkInDate, checkOutDate);
        assertEquals("Amount is incorrect", expectedAmount, actualAmount);
    }

    @Test
    public void calculateAmountWrong() {
        BigDecimal price = BigDecimal.valueOf(50);
        LocalDate checkInDate = LocalDate.parse("2017-08-20");
        LocalDate checkOutDate = LocalDate.parse("2017-08-22");
        BigDecimal expectedAmount = BigDecimal.valueOf(50);
        BigDecimal actualAmount = AmountUtility.calculateAmount(price, checkInDate, checkOutDate);
        assertNotEquals("Amount is incorrect", expectedAmount, actualAmount);
    }
}