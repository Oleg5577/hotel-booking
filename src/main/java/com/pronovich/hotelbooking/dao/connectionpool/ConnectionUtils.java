package com.pronovich.hotelbooking.dao.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

class ConnectionUtils {

    private static final String BUNDLE = "property/db";

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE);

    private static String url = resourceBundle.getString("db.url");
    private static String user = resourceBundle.getString("db.user");
    private static String password = resourceBundle.getString("db.password");
    private static String poolSize = resourceBundle.getString("db.poolSize");

    static Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            //TODO add log???
        }
        return connection;
    }

    static int definePoolSize() {
        return Integer.parseInt(poolSize);
    }
}
