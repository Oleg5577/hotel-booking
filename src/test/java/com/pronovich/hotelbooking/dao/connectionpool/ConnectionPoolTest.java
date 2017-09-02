package com.pronovich.hotelbooking.dao.connectionpool;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectionPoolTest {

    @Test
    public void getPoolCorrect() {
        ConnectionPool pool = ConnectionPool.getPool();
        assertNotNull("ConnectionPool must be created", pool);
    }
}