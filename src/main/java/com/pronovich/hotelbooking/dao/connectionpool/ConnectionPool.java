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

    /**
     * Logger instance
     */
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    /**
     * Instance of the ConnectionPool
     */
    private static ConnectionPool pool;

    /**
     * ArrayBlockingQueue for storage ProxyConnection objects
     */
    private ArrayBlockingQueue<ProxyConnection> connectionQueue;

    /**
     * ReentrantLock for lock access to creation new instance ConnectionPool
     */
    private static ReentrantLock lock = new ReentrantLock();

    /**
     * AtomicBoolean value for checking if the instance already exists
     */
    private static AtomicBoolean instanceExists = new AtomicBoolean(false);

    /**
     * private constructor for creation only one instance of ConnectionPool
     */
    private ConnectionPool() {
        initConnectionPool();
    }

    /**
     * The only method to get ConnectionPool instance
     * @return instance of ConnectionPool
     */
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

    /**
     * Method for initializing instance of ConnectionPool
     */
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

    /**
     * Creating and filling the ConnectionPool with all connections
     * @param poolSize - the number of connection that have to be created
     */
    private void createConnectionsForPool(int poolSize) {
        for (int i = 0; i < poolSize; i++) {
            Connection connection = ConnectionUtils.createConnection();
            connectionQueue.offer(new ProxyConnection(connection));
        }
    }

    /**
     * check if number of Connection is enough
     * @param poolSize - estimated size of the ConnectionPool
     * @return - true if the actual size is at least half the estimated size, false - otherwise
     */
    private boolean numberConnectionsIsEnough(int poolSize) {
        return connectionQueue.size() > (poolSize / 2);
    }

    /**
     * Method for getting the ProxyConnection from ConnectionPool
     * @return ProxyConnection instance
     */
    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.error("Impossible get connection");
        }
        return connection;
    }

    /**
     * return ProxyConnection in the ConnectionPool after use
     * @param connection - ProxyConnection instance
     */
    public void returnConnection(ProxyConnection connection) {
        connectionQueue.offer(connection);
    }

    /**
     * Close all ProxyConnections
     */
    public void closeAllConnections() {
        for (int i = 0; i < connectionQueue.size(); i++) {
            try {
                connectionQueue.take().close();
            } catch (SQLException | InterruptedException e) {
                LOGGER.error("Close connection error");
            }
        }
        derigisterAllDrivers();
    }

    /**
     * Derigister all Drivers
     */
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
