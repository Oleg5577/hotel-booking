package com.pronovich.hotelbooking.receiver.impl;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.OrderDao;
import com.pronovich.hotelbooking.dao.RoomRequestDao;
import com.pronovich.hotelbooking.dao.UserDao;
import com.pronovich.hotelbooking.dao.impl.OrderDaoImpl;
import com.pronovich.hotelbooking.dao.impl.RoomRequestDaoImpl;
import com.pronovich.hotelbooking.dao.impl.UserDaoImpl;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.entity.characteristic.Role;
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.receiver.ClientReceiver;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientReceiverImpl implements ClientReceiver {

    @Override
    public void addRoomRequest(RequestContent content) {
        String checkInRequest = content.getRequestParameters().get("checkInRequest");
        String checkOutRequest = content.getRequestParameters().get("checkOutRequest");
        String roomSizeRequest = content.getRequestParameters().get("roomSizeRequest");
        String roomTypeRequest = content.getRequestParameters().get("roomTypeRequest");
        User user = (User) content.getSessionAttributes().get("user");

        Map<String, String> wrongRequestValues = new HashMap<>();
        //TODO add validation and localization for wrong messages

        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
        } else {
            RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
            try {
                roomRequestDao.addRoomRequest(content);

                List<RoomRequest> roomRequests = roomRequestDao.findAllRequestsByUser(user);
                content.addSessionAttribute("listRoomRequests", roomRequests);
            } catch (DaoException e) {
                //TODO add log??
            }
        }
    }
}
