package com.pronovich.hotelbooking.dao;

import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.exception.DaoException;

import java.util.List;

public interface OrderDao {

    List<RoomOrder> findAllOrdersByUserId(Integer id) throws DaoException;
}
