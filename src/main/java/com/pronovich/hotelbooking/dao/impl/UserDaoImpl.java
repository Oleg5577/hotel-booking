package com.pronovich.hotelbooking.dao.impl;

import com.pronovich.hotelbooking.dao.UserDao;
import com.pronovich.hotelbooking.dao.connectionpool.ConnectionPool;
import com.pronovich.hotelbooking.dao.connectionpool.ProxyConnection;
import com.pronovich.hotelbooking.dao.dbutils.ResultSetConverter;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserDaoImpl extends AbstractBaseDao implements UserDao {

    @Override
    public void addUser(Map<String, String> userValues) {

    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) throws DaoException {
        //TODO ProxyConnection or Connection
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        User user = null;
        try {
            connection = ConnectionPool.getPool().getConnection();

            String sql = "SELECT email, password, name, surname, phone, role_name FROM user " +
                    "INNER JOIN role ON user.fk_role_id = role.id WHERE user.email = ? AND user.password = ?";

            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = ResultSetConverter.createUserEntity(resultSet);
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement,resultSet);
        }
    }
}
