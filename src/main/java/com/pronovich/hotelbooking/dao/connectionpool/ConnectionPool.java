package com.pronovich.hotelbooking.dao.connectionpool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static ConnectionPool pool;

    private  ArrayBlockingQueue<ProxyConnection> connectionQueue;

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

    private  void initConnectionPool() {
        int poolSize = ConnectionUtils.definePoolSize();
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            createConnectionsForPool(poolSize);
            if ( !numberConnectionsIsEnough(poolSize) ) {
                createConnectionsForPool(poolSize - connectionQueue.size());
                if ( !numberConnectionsIsEnough(poolSize) ) {
                    //TODO ADD LOG FATAL
                    throw new RuntimeException("Connection pool initialize Error. Insufficient number of connections");
                }
            }
        } catch (SQLException e) {
            //TODO ADD LOG FATAL
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
            //TODO Log
        }
        return connection;
    }

    public void returnConnection(ProxyConnection connection) {
        connectionQueue.offer(connection);
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
    }
}
