package com.pronovich.hotelbooking.dao.impl;

import com.pronovich.hotelbooking.dao.connectionpool.ConnectionPool;
import com.pronovich.hotelbooking.dao.connectionpool.ProxyConnection;
import com.pronovich.hotelbooking.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

abstract class AbstractBaseDao {

    void closeDbResources(ProxyConnection connection, Statement statement) throws DaoException {
        closeDbResources(connection, statement, null);
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
                System.out.println("Error: ResultSet has not been closed!");
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
            ConnectionPool.getPool().closeConnection(connection);
        }
    }
}
