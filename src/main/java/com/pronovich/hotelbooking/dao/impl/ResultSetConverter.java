package com.pronovich.hotelbooking.dao.impl;

import com.pronovich.hotelbooking.entity.Room;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.entity.OrderStatus;
import com.pronovich.hotelbooking.entity.RequestStatus;
import com.pronovich.hotelbooking.entity.Role;
import com.pronovich.hotelbooking.entity.RoomType;
import com.pronovich.hotelbooking.exception.DaoException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

class ResultSetConverter {

    private static final String USER_ID = "user_id";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String ROLE_NAME = "role_name";
    private static final String ORDER_ID = "order_id";
    private static final String CHECK_IN = "check_in";
    private static final String CHECK_OUT = "check_out";
    private static final String ORDER_STATUS = "order_status";
    private static final String AMOUNT = "amount";
    private static final String IS_PAID = "is_paid";
    private static final String ROOM_ID = "room_id";
    private static final String NUMBER = "number";
    private static final String SIZE = "size";
    private static final String PRICE = "price";
    private static final String TYPE_NAME = "type_name";
    private static final String REQUEST_ID = "request_id";
    private static final String ROOM_SIZE = "room_size";
    private static final String REQUEST_STATUS = "request_status";

    static User createUserEntity(ResultSet resultSet) throws DaoException {
        User user = new User();
        try {
            Integer userId = resultSet.getInt(USER_ID);
            String email = resultSet.getString(EMAIL);
            String name = resultSet.getString(NAME);
            String surname = resultSet.getString(SURNAME);
            String phoneNumber = resultSet.getString(PHONE_NUMBER);
            Role role = Role.valueOf(resultSet.getString(ROLE_NAME).toUpperCase());

            user.setId(userId);
            user.setEmail(email);
            user.setName(name);
            user.setSurname(surname);
            user.setPhoneNumber(phoneNumber);
            user.setRole(role);
        } catch (SQLException e) {
            throw new DaoException();
        }
        return user;
    }

    static RoomOrder createRoomOrderEntity(ResultSet resultSet, User user) throws DaoException {
        RoomOrder roomOrder = new RoomOrder();
        try {
            Integer orderId = resultSet.getInt(ORDER_ID);
            LocalDate checkInDate = resultSet.getDate(CHECK_IN).toLocalDate();
            LocalDate checkOutDate = resultSet.getDate(CHECK_OUT).toLocalDate();
            BigDecimal amount = resultSet.getBigDecimal(AMOUNT);
            OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase());
            boolean isPaid = resultSet.getBoolean(IS_PAID);
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

    static Room createRoomEntity(ResultSet resultSet) throws DaoException {
        Room room = new Room();
        try {
            Integer roomId = resultSet.getInt(ROOM_ID);
            Integer roomNumber = resultSet.getInt(NUMBER);
            Integer size = resultSet.getInt(SIZE);
            BigDecimal price = resultSet.getBigDecimal(PRICE);
            RoomType roomType = RoomType.valueOf(resultSet.getString(TYPE_NAME).toUpperCase());

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

    static RoomRequest createRequestEntity(ResultSet resultSet, User user) throws DaoException {
        RoomRequest roomRequest = new RoomRequest();
        try {
            Integer requestId = resultSet.getInt(REQUEST_ID);
            LocalDate checkInDate = resultSet.getDate(CHECK_IN).toLocalDate();
            LocalDate checkOutDate = resultSet.getDate(CHECK_OUT).toLocalDate();
            RoomType roomType = RoomType.valueOf(resultSet.getString(TYPE_NAME).toUpperCase());
            Integer roomSize = resultSet.getInt(ROOM_SIZE);
            RequestStatus requestStatus = RequestStatus.valueOf(resultSet.getString(REQUEST_STATUS).toUpperCase());

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
