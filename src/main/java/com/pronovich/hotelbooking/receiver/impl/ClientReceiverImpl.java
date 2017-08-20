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
import com.pronovich.hotelbooking.validator.ClientReceiverValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class ClientReceiverImpl implements ClientReceiver {

    private static final Logger LOGGER = LogManager.getLogger(ClientReceiverImpl.class);

    private static final String USER_PARAM = "user";
    private static final String ROOM_REQUEST_ID = "roomRequestId";
    private static final String ROOM_ORDER_ID = "orderId";
    private static final String LIST_ROOM_REQUESTS = "listRoomRequests";
    private static final String LIST_ROOM_ORDERS = "listRoomOrders";

    @Override
    public void findInfoForClientAccount(RequestContent content) {
        RoomOrderDao orderDao = new RoomOrderDaoImpl();
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        try {
            User user = (User) content.getSessionAttributes().get(USER_PARAM);

            List<RoomRequest> roomRequests = roomRequestDao.findAllRequestsByUser(user);
            List<RoomOrder> roomOrders = orderDao.findAllOrdersByUser(user);

            content.addRequestAttributes(LIST_ROOM_REQUESTS, roomRequests);
            content.addRequestAttributes(LIST_ROOM_ORDERS, roomOrders);
        } catch (DaoException e) {
            LOGGER.error("Fill content by Orders and Requests error", e);
        }
    }

    @Override
    public void addRoomRequest(RequestContent content) {
        Map<String, String> wrongRequestValues = ClientReceiverValidator.addRoomRequestValidate(content);

        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
            return;
        }

        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        try {
            roomRequestDao.addRoomRequest(content);
        } catch (DaoException e) {
            LOGGER.error("Add room request error", e);
        }
    }

    public void cancelRequestByClient(RequestContent content) {
        RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
        try {
            String requestId = content.getRequestParameters().get(ROOM_REQUEST_ID);
            roomRequestDao.removeRoomRequestById(Integer.valueOf(requestId));
        } catch (DaoException e) {
            LOGGER.error("Cancel request error", e);
        }
    }

    public void cancelOrderByClient(RequestContent content) {
        RoomOrderDao roomOrderDao = new RoomOrderDaoImpl();
        try {
            String orderId = content.getRequestParameters().get(ROOM_ORDER_ID);
            roomOrderDao.changeOrderStatusToCanceled(Integer.valueOf(orderId));
        } catch (DaoException e) {
            LOGGER.error("Cancel order error", e);
        }
    }
}
