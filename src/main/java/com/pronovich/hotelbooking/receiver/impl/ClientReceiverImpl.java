package com.pronovich.hotelbooking.receiver.impl;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.RoomOrderDao;
import com.pronovich.hotelbooking.dao.RoomRequestDao;
import com.pronovich.hotelbooking.dao.impl.RoomOrderDaoImpl;
import com.pronovich.hotelbooking.dao.impl.RoomRequestDaoImpl;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.receiver.ClientReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientReceiverImpl implements ClientReceiver {

    private static final Logger LOGGER = LogManager.getLogger(ClientReceiverImpl.class);

    private static final String USER_PARAM = "user";
    private static final String ROOM_REQUEST_ID = "roomRequestId";
    private static final String ROOM_ORDER_ID = "orderId";
    private static final String LIST_ROOM_REQUESTS = "listRoomRequests";
    private static final String LIST_ROOM_ORDERS = "listRoomOrders";
    private static final String CHECK_IN_REQUEST_PARAM = "checkInRequest";
    private static final String CHECK_OUT_REQUEST_PARAM = "checkOutRequest";
    private static final String ROOM_SIZE_REQUEST_PARAM = "roomSizeRequest";
    private static final String ROOM_TYPE_REQUEST_PARAM = "roomTypeRequest";

    @Override
    public void findInfoForClientAccount(RequestContent content) {
        RoomOrderDao orderDao = new RoomOrderDaoImpl();
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        try {
            User user = (User) content.getSessionAttributes().get(USER_PARAM);

            List<RoomRequest> roomRequests = roomRequestDao.findAllRequestsByUser(user);
            List<RoomOrder> roomOrders  = orderDao.findAllOrdersByUser(user);

            content.addRequestAttributes(LIST_ROOM_REQUESTS, roomRequests);
            content.addRequestAttributes(LIST_ROOM_ORDERS, roomOrders);
        } catch (DaoException e) {
            LOGGER.error("Fill content by Orders and Requests error", e);
        }
    }

    @Override
    public void addRoomRequest(RequestContent content) {
        String checkInRequest = content.getRequestParameters().get(CHECK_IN_REQUEST_PARAM);
        String checkOutRequest = content.getRequestParameters().get(CHECK_OUT_REQUEST_PARAM);
        String roomSizeRequest = content.getRequestParameters().get(ROOM_SIZE_REQUEST_PARAM);
        String roomTypeRequest = content.getRequestParameters().get(ROOM_TYPE_REQUEST_PARAM);
        User user = (User) content.getSessionAttributes().get(USER_PARAM);

        Map<String, String> wrongRequestValues = new HashMap<>();

        if (checkInRequest.isEmpty()) {
            wrongRequestValues.put(CHECK_IN_REQUEST_PARAM, "Enter check-in date");
        }
        if (checkOutRequest.isEmpty()) {
            wrongRequestValues.put(CHECK_OUT_REQUEST_PARAM, "Enter check-out date");
        }
        if ( !checkInRequest.isEmpty() && !checkOutRequest.isEmpty() ) {
            LocalDate checkInRequestDate = LocalDate.parse(checkInRequest);
            LocalDate checkOutRequestDate = LocalDate.parse(checkOutRequest);
            if (checkInRequestDate.isBefore(LocalDate.now())) {
                wrongRequestValues.put(CHECK_OUT_REQUEST_PARAM, "Check-in can not be at later then today");
            } else if ( !checkInRequestDate.isBefore(checkOutRequestDate)) {
                wrongRequestValues.put(CHECK_OUT_REQUEST_PARAM, "Check-out date has to be after check-in date");
            }
        }
        if (roomSizeRequest.isEmpty()) {
            wrongRequestValues.put(ROOM_SIZE_REQUEST_PARAM, "Choose quantity of guests");
        }
        if (roomTypeRequest.isEmpty()) {
            wrongRequestValues.put(ROOM_TYPE_REQUEST_PARAM, "Choose type of room");
        }
        if (user == null) {
            wrongRequestValues.put(USER_PARAM, "Sign in or Sign up");
        }

        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
        } else {
            RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
            try {
                roomRequestDao.addRoomRequest(content);
            } catch (DaoException e) {
                LOGGER.error("Add room request error" , e);
            }
        }
    }

    public void cancelRequestByClient(RequestContent content) {
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        try {
            String requestId = content.getRequestParameters().get(ROOM_REQUEST_ID);
//            roomRequestDao.changeRequestStatusToDenied(Integer.valueOf(requestId));
            roomRequestDao.removeRoomRequestById(Integer.valueOf(requestId));
        } catch (DaoException e) {
            LOGGER.error("Cancel request error" , e);
        }
    }

    public void cancelOrderByClient(RequestContent content) {
        RoomOrderDao roomOrderDao = new RoomOrderDaoImpl();
        try {
            String orderId = content.getRequestParameters().get(ROOM_ORDER_ID);
            roomOrderDao.changeOrderStatusToCanceled(Integer.valueOf(orderId));
        } catch (DaoException e) {
            LOGGER.error("Cancel order error" , e);
        }
    }
}
