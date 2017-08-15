package com.pronovich.hotelbooking.dao;

import com.pronovich.hotelbooking.entity.Room;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;

import java.util.List;

public interface RoomOrderDao {

    List<RoomOrder> findAllOrdersByUser(User user) throws DaoException;

    List<RoomOrder> findAllOrdersForAllUsers() throws DaoException;

    void createOrder(RoomRequest roomRequest, Room room) throws DaoException;

    void changeOrderStatusToCanceled(Integer orderId) throws DaoException;

    void changeOrderStatusToPaid(Integer orderId) throws DaoException;

    void changeOrderStatusToCheckedIn(Integer orderId) throws DaoException;

    void changeOrderStatusToCheckedOut(Integer orderId) throws DaoException;
}
