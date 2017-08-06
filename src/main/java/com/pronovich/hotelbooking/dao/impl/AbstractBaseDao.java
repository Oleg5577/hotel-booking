package com.pronovich.hotelbooking.dao.impl;

import com.pronovich.hotelbooking.dao.connectionpool.ConnectionPool;
import com.pronovich.hotelbooking.dao.connectionpool.ProxyConnection;
import com.pronovich.hotelbooking.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

abstract class AbstractBaseDao {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    void closeDbResources(ProxyConnection connection, Statement statement) throws DaoException {
        closeStatement(statement);
        releaseConnection(connection);
    }

    void closeDbResources(ProxyConnection connection, Statement statement, ResultSet resultSet) throws DaoException {
        closeResultSet(resultSet);
        closeStatement(statement);
        releaseConnection(connection);
    }

    private void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.error("Error: ResultSet has not been closed!");
            }
        }
    }

    private void closeStatement(Statement statement) throws DaoException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException("Error: Statement has not been closed!");
            }
        }
    }

    private void releaseConnection(ProxyConnection connection) {
        if (connection != null) {
            ConnectionPool.getPool().returnConnection(connection);
        }
    }
}
