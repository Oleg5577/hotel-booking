package com.pronovich.hotelbooking.dao.connectionpool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

class ConnectionUtils {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionUtils.class);

    private static final String BUNDLE = "property/db";
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE);

    private static String url = resourceBundle.getString("db.url");
    private static String user = resourceBundle.getString("db.user");
    private static String password = resourceBundle.getString("db.password");

    private static final int DEFAULT_POOL_SIZE = 10;

    static Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            LOGGER.error("Creation connection error");
        }
        return connection;
    }

    static int definePoolSize() {
        int poolSize;
        try {
            String poolSizeFromProperties = resourceBundle.getString("db.poolSize");
            poolSize = Integer.parseInt(poolSizeFromProperties);
        } catch (MissingResourceException e) {
            return DEFAULT_POOL_SIZE;
        }
        return poolSize;
    }
}
