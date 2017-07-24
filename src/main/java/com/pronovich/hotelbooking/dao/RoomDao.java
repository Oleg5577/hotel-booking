package com.pronovich.hotelbooking.dao;

import com.pronovich.hotelbooking.exception.DaoException;

public interface RoomDao {

    Integer findRoomTypeIdByName(String roomType) throws DaoException;
}
