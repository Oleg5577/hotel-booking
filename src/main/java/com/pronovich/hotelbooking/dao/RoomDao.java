package com.pronovich.hotelbooking.dao;

import com.pronovich.hotelbooking.entity.Room;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.exception.DaoException;

import java.util.List;

public interface RoomDao {

    Room findRoomById(Integer roomId) throws DaoException;

    Integer findRoomTypeIdByName(String roomType) throws DaoException;

    List<Room> findAllRoomsAccordingRequest(RoomRequest roomRequest) throws DaoException;
}
