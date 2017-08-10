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

    private static final String USER = "user";
    private static final String ROOM_REQUEST_ID = "roomRequestId";
    private static final String ROOM_ORDER_ID = "orderId";


    @Override
    public void findInfoForClientAccount(RequestContent content) {
        RoomOrderDao orderDao = new RoomOrderDaoImpl();
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        try {
            User user = (User) content.getSessionAttributes().get(USER);

            List<RoomOrder> roomOrders  = orderDao.findAllOrdersByUser(user);
            List<RoomRequest> roomRequests = roomRequestDao.findAllRequestsByUser(user);

            content.addSessionAttribute("listRoomOrders", roomOrders);
            content.addSessionAttribute("listRoomRequests", roomRequests);
        } catch (DaoException e) {
            LOGGER.error("Fill content by Orders and Requests error", e);
        }
    }

    @Override
    public void addRoomRequest(RequestContent content) {
        String checkInRequest = content.getRequestParameters().get("checkInRequest");
        String checkOutRequest = content.getRequestParameters().get("checkOutRequest");
        String roomSizeRequest = content.getRequestParameters().get("roomSizeRequest");
        String roomTypeRequest = content.getRequestParameters().get("roomTypeRequest");
        User user = (User) content.getSessionAttributes().get("user");

        Map<String, String> wrongRequestValues = new HashMap<>();

        if (checkInRequest.isEmpty()) {
            wrongRequestValues.put("checkInRequest", "Enter check-in date");
        }
        if (checkOutRequest.isEmpty()) {
            wrongRequestValues.put("checkOutRequest", "Enter check-out date");
        }
        if ( !checkInRequest.isEmpty() && !checkOutRequest.isEmpty() ) {
            LocalDate checkInRequestDate = LocalDate.parse(checkInRequest);
            LocalDate checkOutRequestDate = LocalDate.parse(checkOutRequest);
            if (checkInRequestDate.isBefore(LocalDate.now())) {
                wrongRequestValues.put("checkOutRequest", "Check-in can not be at later then today");
            } else if ( !checkInRequestDate.isBefore(checkOutRequestDate)) {
                wrongRequestValues.put("checkOutRequest", "Check-out date has to be after check-in date");
            }
        }
        if (roomSizeRequest.isEmpty()) {
            wrongRequestValues.put("roomSizeRequest", "Choose quantity of guests");
        }
        if (roomTypeRequest.isEmpty()) {
            wrongRequestValues.put("roomTypeRequest", "Choose type of room");
        }
        if (user == null) {
            wrongRequestValues.put("user", "Sign in or Sign up");
        }

        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
        } else {
            RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
            try {
                roomRequestDao.addRoomRequest(content);

                List<RoomRequest> roomRequests = roomRequestDao.findAllRequestsByUser(user);
                content.addSessionAttribute("listRoomRequests", roomRequests);
            } catch (DaoException e) {
                LOGGER.error("Add room request error" , e);
            }
        }
    }

    public void cancelRequest(RequestContent content) {
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        try {
            String requestId = content.getRequestParameters().get(ROOM_REQUEST_ID);
            roomRequestDao.removeRequestById(Integer.valueOf(requestId));
        } catch (DaoException e) {
            LOGGER.error("Cancel request error" , e);
        }
    }

    @Override
    public void cancelOrder(RequestContent content) {
        RoomOrderDao roomOrderDao = new RoomOrderDaoImpl();
        try {
            String orderId = content.getRequestParameters().get(ROOM_ORDER_ID);
            roomOrderDao.removeOrderById(Integer.valueOf(orderId));
        } catch (DaoException e) {
            LOGGER.error("Cancel order error" , e);
        }
    }
}
