package com.pronovich.hotelbooking.configuration;

import java.util.ResourceBundle;

public class ConfigManager {

    private static final String BUNDLE = "config";

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE);

    public static final String DB_MAX_CONNECTIONS = "db.maxConnections";

    public static final String DB_URL = "db.url";

    public static final String DB_USER = "db.user";

    public static final String DB_PASSWORD = "db.password";

    public static String getProperty(String property) {
        return (String) resourceBundle.getObject(property);
    }
}
