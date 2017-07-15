package com.pronovich.hotelbooking.dao.dbutils;

import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetConverter {

    public static User createUserEntity(ResultSet resultSet) throws DaoException {
        User userEntity = new User();
        try {
            Integer userId = resultSet.getInt("user_id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String phoneNumber = resultSet.getString("phone_number");
            String role = resultSet.getString("role_name");

            userEntity.setId(userId);
            userEntity.setEmail(email);
            userEntity.setPassword(password);
            userEntity.setName(name);
            userEntity.setSurname(surname);
            userEntity.setPhoneNumber(phoneNumber);
            userEntity.setRole(role);

        } catch (SQLException e) {
            throw new DaoException();
        }
        return userEntity;
    }
}
