package com.pronovich.hotelbooking.dao.connectionpool;

import com.pronovich.hotelbooking.configuration.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

public class ConnectionPool {

    private static ConnectionPool pool;

    private static ArrayBlockingQueue<ProxyConnection> connectionQueue;

    private ConnectionPool() {
        initConnectionPool();
    }

    public static ConnectionPool getPool() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    private static void initConnectionPool() {
        String url = ConfigManager.getProperty(ConfigManager.DB_URL);
        String user = ConfigManager.getProperty(ConfigManager.DB_USER);
        String password = ConfigManager.getProperty(ConfigManager.DB_PASSWORD);
        int poolSize = Integer.parseInt(ConfigManager.getProperty(ConfigManager.DB_MAX_CONNECTIONS));

        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        try {
            //TODO register driver???
//            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connectionQueue.offer(new ProxyConnection(connection));
            }
        } catch (SQLException e) {
            //TODO ADD LOG FATAL
            throw new RuntimeException("Connection pool initialize Error", e);
        }
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            //TODO Log or Exception???
        }
        return connection;
    }

    public void closeConnection(ProxyConnection connection) {
        connectionQueue.offer(connection);
    }
}
