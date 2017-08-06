package com.pronovich.hotelbooking.receiver.impl;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.RoomRequestDao;
import com.pronovich.hotelbooking.dao.impl.RoomRequestDaoImpl;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.receiver.ClientReceiver;

import java.time.LocalDate;
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

        if (checkInRequest.isEmpty()) {
            wrongRequestValues.put("checkInRequest", "Enter check-in date");
        }
        if (checkOutRequest.isEmpty()) {
            wrongRequestValues.put("checkOutRequest", "Enter check-out date");
        }
        if ( !checkInRequest.isEmpty() && !checkOutRequest.isEmpty() ) {
            LocalDate checkInRequestDate = LocalDate.parse(checkInRequest);
            LocalDate checkOutRequestDate = LocalDate.parse(checkOutRequest);
            if ( !checkInRequestDate.isBefore(checkOutRequestDate)) {
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

        //TODO add localization for wrong messages

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
