package com.pronovich.hotelbooking.receiver.impl;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.RoomDao;
import com.pronovich.hotelbooking.dao.RoomRequestDao;
import com.pronovich.hotelbooking.dao.impl.RoomDaoImpl;
import com.pronovich.hotelbooking.dao.impl.RoomRequestDaoImpl;
import com.pronovich.hotelbooking.entity.Room;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.receiver.AdminReceiver;

import java.util.List;

public class AdminReceiverImpl implements AdminReceiver {

    public void findAllRoomsAccordingRequest(RequestContent content) {
        String requestId = content.getParameters().get("requestId");
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        RoomDao roomDao = new RoomDaoImpl();
        try {
            RoomRequest roomRequest = roomRequestDao.findRequestById(Integer.valueOf(requestId));
            List<Room> allRoomsByRequest = roomDao.findAllRoomsAccordingRequest(roomRequest);
        } catch (DaoException e) {
            //TODO add log??
        }

    }
}
