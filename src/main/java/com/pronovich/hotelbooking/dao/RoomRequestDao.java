package com.pronovich.hotelbooking.dao;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.exception.DaoException;

public interface RoomRequestDao {

    void addRoomRequest(RequestContent requestContent) throws DaoException;

}
