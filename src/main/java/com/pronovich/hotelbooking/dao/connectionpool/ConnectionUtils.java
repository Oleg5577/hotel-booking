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
    private static final String DB_POOL_SIZE = "db.poolSize";
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE);

    private static final String URL = resourceBundle.getString("db.url");
    private static final String USER = resourceBundle.getString("db.user");
    private static final String PASSWORD = resourceBundle.getString("db.password");

    private static final int DEFAULT_POOL_SIZE = 10;

    static Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("Creation connection error");
        }
        return connection;
    }

    /**
     * Define pool size using property file. If there is an error reading the property file,
     * will set default pool size
     * @return pool size
     */
    static int definePoolSize() {
        int poolSize;
        try {
            String poolSizeFromProperties = resourceBundle.getString(DB_POOL_SIZE);
            poolSize = Integer.parseInt(poolSizeFromProperties);
        } catch (MissingResourceException e) {
            return DEFAULT_POOL_SIZE;
        }
        return poolSize;
    }
}
