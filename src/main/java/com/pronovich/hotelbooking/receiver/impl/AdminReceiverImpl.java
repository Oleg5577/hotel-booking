package com.pronovich.hotelbooking.receiver.impl;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.RoomOrderDao;
import com.pronovich.hotelbooking.dao.RoomDao;
import com.pronovich.hotelbooking.dao.RoomRequestDao;
import com.pronovich.hotelbooking.dao.impl.RoomOrderDaoImpl;
import com.pronovich.hotelbooking.dao.impl.RoomDaoImpl;
import com.pronovich.hotelbooking.dao.impl.RoomRequestDaoImpl;
import com.pronovich.hotelbooking.entity.Room;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.receiver.AdminReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AdminReceiverImpl implements AdminReceiver {

    private static final Logger LOGGER = LogManager.getLogger(AdminReceiverImpl.class);
    private static final String ROOM_ORDER_ID = "roomOrderId";
    private static final String ROOM_REQUEST_ID = "roomRequestId";

    public void findAllRoomsAccordingRequest(RequestContent content) {
        String requestId = content.getRequestParameters().get("requestId");
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        RoomDao roomDao = new RoomDaoImpl();
        try {
            RoomRequest roomRequest = roomRequestDao.findRequestById(Integer.valueOf(requestId));
            List<Room> allRoomsAccordingRequest = roomDao.findAllRoomsAccordingRequest(roomRequest);
            content.addRequestAttributes("allRoomsAccordingRequest", allRoomsAccordingRequest);
        } catch (DaoException e) {
            LOGGER.error("Find room according request error", e);
        }
    }

    @Override
    public void findInfoForAdminAccount(RequestContent content) {
        RoomOrderDao orderDao = new RoomOrderDaoImpl();
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();

        try {
            List<RoomOrder>  roomOrders = orderDao.findAllOrdersForAllUsers();
            List<RoomRequest> roomRequests = roomRequestDao.findAllRequestsForAllUser();

            content.addRequestAttributes("listRoomOrders", roomOrders);
            content.addRequestAttributes("listRoomRequests", roomRequests);
        } catch (DaoException e) {
            LOGGER.error("Find info for admin account error", e);
        }
    }

    @Override
    public void createOrder(RequestContent content) {
        String roomId = content.getRequestParameters().get("roomId");
        String requestId = content.getRequestParameters().get("requestId");

        RoomDao roomDao = new RoomDaoImpl();
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        RoomOrderDao orderDao = new RoomOrderDaoImpl();
        try {
            Room room = roomDao.findRoomById(Integer.valueOf(roomId));
            RoomRequest roomRequest = roomRequestDao.findRequestById(Integer.valueOf(requestId));
            orderDao.createOrder(roomRequest, room);

            List<RoomOrder>  roomOrders = orderDao.findAllOrdersForAllUsers();
            List<RoomRequest> roomRequests = roomRequestDao.findAllRequestsForAllUser();

            content.addRequestAttributes("listRoomOrders", roomOrders);
            content.addRequestAttributes("listRoomRequests", roomRequests);
        } catch (DaoException e) {
            LOGGER.error("Create order error", e);
        }
    }

    @Override
    public void cancelRequestByAdmin(RequestContent content) {
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        try {
            String requestId = content.getRequestParameters().get(ROOM_REQUEST_ID);
            roomRequestDao.removeRequestById(Integer.valueOf(requestId));
        } catch (DaoException e) {
            LOGGER.error("Cancel request error" , e);
        }
    }

    @Override
    public void cancelOrderByAdmin(RequestContent content) {
        RoomOrderDao roomOrderDao = new RoomOrderDaoImpl();
        try {
            String orderId = content.getRequestParameters().get(ROOM_ORDER_ID);
            roomOrderDao.removeOrderById(Integer.valueOf(orderId));
        } catch (DaoException e) {
            LOGGER.error("Cancel order error" , e);
        }
    }
}
