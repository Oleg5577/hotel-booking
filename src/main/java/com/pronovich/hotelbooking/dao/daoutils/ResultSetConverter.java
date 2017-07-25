package com.pronovich.hotelbooking.dao.daoutils;

import com.pronovich.hotelbooking.entity.Room;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.entity.propertyenum.OrderStatus;
import com.pronovich.hotelbooking.entity.propertyenum.Role;
import com.pronovich.hotelbooking.exception.DaoException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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

    public static RoomOrder createOrderEntity(ResultSet resultSet, User user) throws DaoException {
        RoomOrder roomOrder = new RoomOrder();
        try {
            Integer orderId = resultSet.getInt("order_id");
            Date checkInDate = resultSet.getDate("check_in");
            Date checkOutDate = resultSet.getDate("check_out");
            BigDecimal amount = resultSet.getBigDecimal("amount");
            Room room = createRoomEntity(resultSet);
            OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
            boolean isPaid = resultSet.getBoolean("is_paid");

            roomOrder.setId(orderId);
            roomOrder.setCheckInDate(checkInDate);
            roomOrder.setCheckOutDate(checkOutDate);
            roomOrder.setAmount(amount);
            roomOrder.setRoom(room);
            roomOrder.setUser(user);
            roomOrder.setOrderStatus(orderStatus);
            roomOrder.setPaid(isPaid);

        } catch (SQLException e) {
            throw new DaoException();
        }
        return roomOrder;
    }

    private static Room createRoomEntity(ResultSet resultSet) throws DaoException {
        //TODO implement
        return null;
    }
}
