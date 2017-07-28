package com.pronovich.hotelbooking.receiver.impl;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.OrderDao;
import com.pronovich.hotelbooking.dao.RoomDao;
import com.pronovich.hotelbooking.dao.RoomRequestDao;
import com.pronovich.hotelbooking.dao.impl.OrderDaoImpl;
import com.pronovich.hotelbooking.dao.impl.RoomDaoImpl;
import com.pronovich.hotelbooking.dao.impl.RoomRequestDaoImpl;
import com.pronovich.hotelbooking.entity.Room;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.receiver.AdminReceiver;

import java.util.List;

public class AdminReceiverImpl implements AdminReceiver {

    public void findAllRoomsAccordingRequest(RequestContent content) {
        String requestId = content.getRequestParameters().get("requestId");
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        RoomDao roomDao = new RoomDaoImpl();
        try {
            RoomRequest roomRequest = roomRequestDao.findRequestById(Integer.valueOf(requestId));
            List<Room> allRoomsAccordingRequest = roomDao.findAllRoomsAccordingRequest(roomRequest);
            content.addRequestAttributes("allRoomsAccordingRequest", allRoomsAccordingRequest);
        } catch (DaoException e) {
            //TODO add log??
        }
    }

    @Override
    public void findInfoForAdminAccount(RequestContent content) {
        OrderDao orderDao = new OrderDaoImpl();
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();

        try {
            List<RoomOrder>  roomOrders = orderDao.findAllOrdersForAllUsers();
            List<RoomRequest> roomRequests = roomRequestDao.findAllRequestsForAllUser();

            content.addRequestAttributes("listRoomOrders", roomOrders);
            content.addRequestAttributes("listRoomRequests", roomRequests);
        } catch (DaoException e) {
            //TODO add log??
        }
    }

    @Override
    public void assignRoomToRequest(RequestContent content) {
        //TODO
        OrderDao orderDao = new OrderDaoImpl();
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        try {


            List<RoomOrder>  roomOrders = orderDao.findAllOrdersForAllUsers();
            List<RoomRequest> roomRequests = roomRequestDao.findAllRequestsForAllUser();

            content.addRequestAttributes("listRoomOrders", roomOrders);
            content.addRequestAttributes("listRoomRequests", roomRequests);
        } catch (DaoException e) {
            //TODO add log??
        }
    }
}
