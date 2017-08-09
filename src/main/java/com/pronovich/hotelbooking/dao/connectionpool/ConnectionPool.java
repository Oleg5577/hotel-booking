package com.pronovich.hotelbooking.dao.connectionpool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static ConnectionPool pool;

    private ArrayBlockingQueue<ProxyConnection> connectionQueue;

    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean instanceExists = new AtomicBoolean(false);

    private ConnectionPool() {
        initConnectionPool();
    }

    public static ConnectionPool getPool() {
        if (!instanceExists.get()) {
            lock.lock();
            try {
                if (pool == null) {
                    pool = new ConnectionPool();
                    instanceExists.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return pool;
    }

    private void initConnectionPool() {
        int poolSize = ConnectionUtils.definePoolSize();
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            createConnectionsForPool(poolSize);
            if (!numberConnectionsIsEnough(poolSize)) {
                createConnectionsForPool(poolSize - connectionQueue.size());
                if (!numberConnectionsIsEnough(poolSize)) {
                    LOGGER.fatal("Connection pool initialize Error. Insufficient number of connections");
                    throw new RuntimeException("Connection pool initialize Error.");
                }
            }
        } catch (SQLException e) {
            LOGGER.fatal("Connection pool initialize Error");
            throw new RuntimeException("Connection pool initialize Error", e);
        }
    }

    private void createConnectionsForPool(int poolSize) {
        for (int i = 0; i < poolSize; i++) {
            Connection connection = ConnectionUtils.createConnection();
            connectionQueue.offer(new ProxyConnection(connection));
        }
    }

    private boolean numberConnectionsIsEnough(int poolSize) {
        return connectionQueue.size() > (poolSize / 2);
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.error("Impossible get connection");
        }
        return connection;
    }

    public void returnConnection(ProxyConnection connection) {
        connectionQueue.offer(connection);
    }

    public void closeAllConnections() {
        for (int i = 0; i < connectionQueue.size(); i++) {
            try {
                ProxyConnection connection = connectionQueue.take();
                connection.close();
            } catch (SQLException | InterruptedException e) {
                LOGGER.error("Close connection error");
            }
        }
        derigisterAllDrivers();
    }

    private void derigisterAllDrivers() {
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            LOGGER.error("Deregister driver error");
        }
    }
}
