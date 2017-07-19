package com.pronovich.hotelbooking.dao.connectionpool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static ConnectionPool pool;

    private static ArrayBlockingQueue<ProxyConnection> connectionQueue;

    private static ReentrantLock lock = new ReentrantLock();

    private ConnectionPool() {
        initConnectionPool();
    }

    public static ConnectionPool getPool() {
        if (pool == null) {
            lock.lock();
            try {
                pool = new ConnectionPool();
            } finally {
                lock.unlock();
            }
        }
        return pool;
    }

    private static void initConnectionPool() {
        int poolSize = ConnectionUtils.definePoolSize();
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            for (int i = 0; i < poolSize; i++) {
                Connection connection = ConnectionUtils.createConnection();
                connectionQueue.offer(new ProxyConnection(connection));
            }
        } catch (SQLException e) {
            //TODO ADD LOG FATAL
            throw new RuntimeException("Connection pool initialize Error", e);
        }
    }

    public ProxyConnection getConnection() {
        lock.lock();
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            //TODO Log or Exception???
        } finally {
            lock.unlock();
        }
        return connection;
    }

    public void closeConnection(ProxyConnection connection) {
        lock.lock();
        try {
            connectionQueue.offer(connection);
        } finally {
            lock.unlock();
        }
    }

    public void closeAllConnections() {
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            // TODO add log
        }

        //TODO release all connections??
/*        lock.lock();
        try {
            Connection connection = null;
            int poolSize = Integer.parseInt(ConfigManager.getProperty(ConfigManager.DB_POOL_SIZE));
            for (int i = 0; i < poolSize; i++) {
                try {
                    connection = connectionQueue.take();
                } catch (InterruptedException e) {
                    //TODO add log
                }
                if (connection != null) {
                    try {
                        if (!connection.isClosed()) {
                            connection.close();
                        }
                    } catch (SQLException e) {
                        //TODO add log
                    }
                }
            }
        } finally {
            lock.unlock();
        }*/
    }
}
