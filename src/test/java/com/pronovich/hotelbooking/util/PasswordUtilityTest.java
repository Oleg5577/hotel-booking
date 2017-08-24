package com.pronovich.hotelbooking.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class PasswordUtilityTest {

    @Test
    public void getSalt() {
        byte[] saltFirst = PasswordUtility.getSalt();
        byte[] saltSecond = PasswordUtility.getSalt();
        assertFalse("Arrays are generated randomly and must be different", Arrays.equals(saltFirst, saltSecond));
    }

    @Test
    public void getSecurePassword() {
        String passwordToHash = "password";
        byte[] salt = {1, 2, 0, -5, 6, 7, 8, 12, 56, 11, 14, 22, 124, -15, 44, 78};
        String securePassword = PasswordUtility.getSecurePassword(passwordToHash, salt);
        assertNotEquals("Password must be encoded", passwordToHash, securePassword);
    }
}