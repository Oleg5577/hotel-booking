package com.pronovich.hotelbooking.dao.daoutils;

import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.entity.propertyenum.Role;
import com.pronovich.hotelbooking.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetConverter {

    public static User createUserEntity(ResultSet resultSet) throws DaoException {
        User user = new User();
        try {
            Integer userId = resultSet.getInt("user_id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String phoneNumber = resultSet.getString("phone_number");
            String role = resultSet.getString("role_name");

            user.setId(userId);
            user.setEmail(email);
            user.setPassword(password);
            user.setName(name);
            user.setSurname(surname);
            user.setPhoneNumber(phoneNumber);
            user.setRole(Role.valueOf(role));
        } catch (SQLException e) {
            throw new DaoException();
        }
        return user;
    }

    public static RoomOrder createOrderEntity(ResultSet resultSet) {
    }
}
