package com.pronovich.hotelbooking.dao.daoutils;

import com.pronovich.hotelbooking.entity.Room;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.entity.characteristic.OrderStatus;
import com.pronovich.hotelbooking.entity.characteristic.RequestStatus;
import com.pronovich.hotelbooking.entity.characteristic.Role;
import com.pronovich.hotelbooking.entity.characteristic.RoomType;
import com.pronovich.hotelbooking.exception.DaoException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
            Role role = Role.valueOf(resultSet.getString("role_name").toUpperCase());

            user.setId(userId);
            user.setEmail(email);
            user.setPassword(password);
            user.setName(name);
            user.setSurname(surname);
            user.setPhoneNumber(phoneNumber);
            user.setRole(role);
        } catch (SQLException e) {
            throw new DaoException();
        }
        return user;
    }

    public static RoomOrder createOrderEntity(ResultSet resultSet, User user) throws DaoException {
        RoomOrder roomOrder = new RoomOrder();
        try {
            Integer orderId = resultSet.getInt("order_id");
            //TODO check if work java.util.Date or LocalDate
            LocalDate checkInDate = resultSet.getDate("check_in").toLocalDate();
            LocalDate checkOutDate = resultSet.getDate("check_out").toLocalDate();
            BigDecimal amount = resultSet.getBigDecimal("amount");
            OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("order_status").toUpperCase());
            boolean isPaid = resultSet.getBoolean("is_paid");
            Room room = createRoomEntity(resultSet);

            roomOrder.setId(orderId);
            roomOrder.setCheckInDate(checkInDate);
            roomOrder.setCheckOutDate(checkOutDate);
            roomOrder.setAmount(amount);
            roomOrder.setOrderStatus(orderStatus);
            roomOrder.setPaid(isPaid);
            roomOrder.setRoom(room);
            roomOrder.setUser(user);
        } catch (SQLException e) {
            throw new DaoException();
        }
        return roomOrder;
    }

    public static Room createRoomEntity(ResultSet resultSet) throws DaoException {
        Room room = new Room();
        try {
            Integer roomId = resultSet.getInt("room_id");
            Integer roomNumber = resultSet.getInt("number");
            Integer size = resultSet.getInt("size");
            BigDecimal price = resultSet.getBigDecimal("price");
            RoomType roomType = RoomType.valueOf(resultSet.getString("type_name").toUpperCase());

            room.setId(roomId);
            room.setRoomNumber(roomNumber);
            room.setSize(size);
            room.setPrice(price);
            room.setRoomType(roomType);
        } catch (SQLException e) {
            throw new DaoException();
        }
        return room;
    }

    public static RoomRequest createRequestEntity(ResultSet resultSet, User user) throws DaoException {
        RoomRequest roomRequest = new RoomRequest();
        try {
            Integer requestId = resultSet.getInt("request_id");
            LocalDate checkInDate = resultSet.getDate("check_in").toLocalDate();
            LocalDate checkOutDate = resultSet.getDate("check_out").toLocalDate();
            RoomType roomType = RoomType.valueOf(resultSet.getString("type_name").toUpperCase());
            Integer roomSize = resultSet.getInt("room_size");
            RequestStatus requestStatus = RequestStatus.valueOf(resultSet.getString("request_status").toUpperCase());

            roomRequest.setId(requestId);
            roomRequest.setCheckInDate(checkInDate);
            roomRequest.setCheckOutDate(checkOutDate);
            roomRequest.setRoomType(roomType);
            roomRequest.setRoomSize(roomSize);
            roomRequest.setRequestStatus(requestStatus);
            roomRequest.setUser(user);
        } catch (SQLException e) {
            throw new DaoException();
        }
        return roomRequest;
    }
}
