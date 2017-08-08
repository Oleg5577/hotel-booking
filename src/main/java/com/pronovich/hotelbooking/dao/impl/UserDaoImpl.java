package com.pronovich.hotelbooking.dao.impl;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.UserDao;
import com.pronovich.hotelbooking.dao.connectionpool.ConnectionPool;
import com.pronovich.hotelbooking.dao.connectionpool.ProxyConnection;
import com.pronovich.hotelbooking.dao.daoutils.ResultSetConverter;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserDaoImpl extends AbstractBaseDao implements UserDao {

    private static final String ADD_USER_SQL = "INSERT INTO hotel_booking_db.user " +
            "(email, password, password_salt, name, surname, phone_number, fk_role_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String FIND_ROLE_ID_BY_NAME_SQL = "SELECT role_id FROM hotel_booking_db.role WHERE role_name=?";

    private static final String FIND_USER_SQL = "SELECT user_id, email, password, name, surname, phone_number, role_name " +
            "FROM hotel_booking_db.user INNER JOIN hotel_booking_db.role ON user.fk_role_id = role.role_id " +
            "WHERE user.email = ? AND user.password = ?";

    private static final String FIND_USER_BY_ID_SQL = "SELECT user_id, email, name, surname, phone_number, role_name " +
            "FROM user LEFT JOIN role ON user.fk_role_id = role.role_id WHERE user.user_id = ?";

    private static final String FIND_USER_BY_EMAIL_SQL = "SELECT user_id, email, name, surname, phone_number, role_name " +
            "FROM user LEFT JOIN role ON user.fk_role_id = role.role_id WHERE user.email = ?";

    private static final String FIND_SALT_BY_EMAIL_SQL = "SELECT password_salt FROM user WHERE email = ?";

    @Override
    public void addUser(RequestContent content) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(ADD_USER_SQL);

            Map<String, String> requestParameters = content.getRequestParameters();
            Map<String, Object> requestAttributes = content.getRequestAttributes();

            statement.setString(1, requestParameters.get("email"));
            statement.setString(2, (String) requestAttributes.get("securePassword"));
            statement.setString(3, (String) requestAttributes.get("encodedSalt"));
            statement.setString(4, requestParameters.get("name"));
            statement.setString(5, requestParameters.get("surname"));
            statement.setString(6, requestParameters.get("phoneNumber"));
            statement.setInt(7, findRoleIdByName(requestParameters.get("role")));

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement);
        }
    }

    @Override
    public Integer findRoleIdByName(String role) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer roleId = null;
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(FIND_ROLE_ID_BY_NAME_SQL);
            statement.setString(1, role);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                roleId = resultSet.getInt("role_id");
            }
            return roleId;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }

    @Override
    public String findPasswordSaltByEmail(String email) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String salt = null;
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(FIND_SALT_BY_EMAIL_SQL);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                salt = resultSet.getString("password_salt");
            }
            return salt;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = ConnectionPool.getPool().getConnection();

            statement = connection.prepareStatement(FIND_USER_SQL);
            statement.setString(1, email);
            statement.setString(2, password);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = ResultSetConverter.createUserEntity(resultSet);
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }

    @Override
    public User findUserById(Integer userId) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(FIND_USER_BY_ID_SQL);
            statement.setInt(1, userId);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = ResultSetConverter.createUserEntity(resultSet);
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }

    @Override
    public User findUserByEmail(String email) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(FIND_USER_BY_EMAIL_SQL);
            statement.setString(1, email);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = ResultSetConverter.createUserEntity(resultSet);
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }
}
