package com.pronovich.hotelbooking.receiver.impl;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.RoomOrderDao;
import com.pronovich.hotelbooking.dao.RoomDao;
import com.pronovich.hotelbooking.dao.RoomRequestDao;
import com.pronovich.hotelbooking.dao.impl.RoomOrderDaoImpl;
import com.pronovich.hotelbooking.dao.impl.RoomDaoImpl;
import com.pronovich.hotelbooking.dao.impl.RoomRequestDaoImpl;
import com.pronovich.hotelbooking.entity.RequestStatus;
import com.pronovich.hotelbooking.entity.Room;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.receiver.AdminReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AdminReceiverImpl implements AdminReceiver {

    private static final Logger LOGGER = LogManager.getLogger(AdminReceiverImpl.class);
    private static final String ROOM_ORDER_ID = "roomOrderId";
    private static final String ROOM_REQUEST_ID = "roomRequestId";
    private static final String REQUEST_ID_PARAM = "requestId";
    private static final String LIST_ROOM_ORDERS_PARAM = "listRoomOrders";
    private static final String LIST_ROOM_REQUESTS_PARAM = "listRoomRequests";
    private static final String ROOM_ID_PARAM = "roomId";
    private static final String ROOM_ORDER_PARAM = "roomOrder";
    private static final String DAYS_NUMBER_PARAM = "daysNumber";
    private static final String ALL_ROOMS_ACCORDING_REQUEST_PARAM = "allRoomsAccordingRequest";

    public void findAllRoomsAccordingRequest(RequestContent content) {
        String requestId = content.getRequestParameters().get(REQUEST_ID_PARAM);
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        RoomDao roomDao = new RoomDaoImpl();
        try {
            RoomRequest roomRequest = roomRequestDao.findRequestById(Integer.valueOf(requestId));
            List<Room> allRoomsAccordingRequest = roomDao.findAllRoomsAccordingRequest(roomRequest);
            content.addRequestAttributes(ALL_ROOMS_ACCORDING_REQUEST_PARAM, allRoomsAccordingRequest);
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

            content.addRequestAttributes(LIST_ROOM_ORDERS_PARAM, roomOrders);
            content.addRequestAttributes(LIST_ROOM_REQUESTS_PARAM, roomRequests);
        } catch (DaoException e) {
            LOGGER.error("Find info for admin account error", e);
        }
    }

    @Override
    public void createOrder(RequestContent content) {
        String roomId = content.getRequestParameters().get(ROOM_ID_PARAM);
        String requestId = content.getRequestParameters().get(REQUEST_ID_PARAM);

        RoomDao roomDao = new RoomDaoImpl();
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        RoomOrderDao orderDao = new RoomOrderDaoImpl();
        try {
            Room room = roomDao.findRoomById(Integer.valueOf(roomId));
            RoomRequest roomRequest = roomRequestDao.findRequestById(Integer.valueOf(requestId));
            orderDao.createOrder(roomRequest, room);

            List<RoomOrder>  roomOrders = orderDao.findAllOrdersForAllUsers();
            List<RoomRequest> roomRequests = roomRequestDao.findAllRequestsForAllUser();

            content.addRequestAttributes(LIST_ROOM_ORDERS_PARAM, roomOrders);
            content.addRequestAttributes(LIST_ROOM_REQUESTS_PARAM, roomRequests);
        } catch (DaoException e) {
            LOGGER.error("Create order error", e);
        }
    }

    @Override
    public void cancelRequestByAdmin(RequestContent content) {
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        try {
            String requestId = content.getRequestParameters().get(ROOM_REQUEST_ID);
            roomRequestDao.changeStatusTo(Integer.valueOf(requestId), RequestStatus.DENIED);
        } catch (DaoException e) {
            LOGGER.error("Cancel request error" , e);
        }
    }

    @Override
    public void cancelOrderByAdmin(RequestContent content) {
        RoomOrderDao roomOrderDao = new RoomOrderDaoImpl();
        try {
            String orderId = content.getRequestParameters().get(ROOM_ORDER_ID);
            roomOrderDao.changeOrderStatusToCanceled(Integer.valueOf(orderId));
        } catch (DaoException e) {
            LOGGER.error("Cancel order error" , e);
        }
    }

    @Override
    public void changeOrderStatusToPaid(RequestContent content) {
        RoomOrderDao roomOrderDao = new RoomOrderDaoImpl();
        try {
            String orderId = content.getRequestParameters().get(ROOM_ORDER_ID);
            roomOrderDao.changeOrderStatusToPaid(Integer.valueOf(orderId));
        } catch (DaoException e) {
            LOGGER.error("Change order status to paid error" , e);
        }
    }

    @Override
    public void changeOrderStatusToCheckedIn(RequestContent content) {
        RoomOrderDao roomOrderDao = new RoomOrderDaoImpl();
        try {
            String orderId = content.getRequestParameters().get(ROOM_ORDER_ID);
            roomOrderDao.changeOrderStatusToCheckedIn(Integer.valueOf(orderId));
        } catch (DaoException e) {
            LOGGER.error("Change order status to checked-in error" , e);
        }
    }

    @Override
    public void changeOrderStatusToCheckedOut(RequestContent content) {
        RoomOrderDao roomOrderDao = new RoomOrderDaoImpl();
        try {
            String orderId = content.getRequestParameters().get(ROOM_ORDER_ID);
            roomOrderDao.changeOrderStatusToCheckedOut(Integer.valueOf(orderId));
        } catch (DaoException e) {
            LOGGER.error("Change order status to checked-out error" , e);
        }
    }

    @Override
    public void issueInvoice(RequestContent content) {
        RoomOrderDao roomOrderDao = new RoomOrderDaoImpl();
        try {
            String orderId = content.getRequestParameters().get(ROOM_ORDER_ID);

            RoomOrder roomOrder = roomOrderDao.findOrderById(Integer.valueOf(orderId));
            long daysNumber = calculateDaysBetweenDates(roomOrder.getCheckInDate(), roomOrder.getCheckOutDate());

            content.addRequestAttributes(ROOM_ORDER_PARAM, roomOrder);
            content.addRequestAttributes(DAYS_NUMBER_PARAM, daysNumber);
        } catch (DaoException e) {
            LOGGER.error("Issue invoice error" , e);
        }
    }

    private long calculateDaysBetweenDates(LocalDate checkInDate, LocalDate checkOutDate) {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
}
