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
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.receiver.UserReceiver;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserReceiverImpl implements UserReceiver {

    @Override
    public void signUp(RequestContent content) {
        String email = content.getParameters().get("email");
        String password = content.getParameters().get("password");
        String repeatPassword = content.getParameters().get("repeatPassword");
        String name = content.getParameters().get("name");
        String surname = content.getParameters().get("surname");
        String phoneNumber = content.getParameters().get("phoneNumber");

        // TODO добавить валидацию данных запроса Sign Up
        //TODO add localization messages

        Map<String,String> wrongRequestValues = new HashMap<>();

        if (StringUtils.isEmpty(email)) {
            wrongRequestValues.put("email", "Please enter a email");
        }
        if ( StringUtils.isEmpty(password)) {
            wrongRequestValues.put("password", "Please, enter a Password");
        }
        if ( StringUtils.isEmpty(repeatPassword)) {
            wrongRequestValues.put("repeatPassword", "Please, repeat Password");
        } else if ( !password.equals(repeatPassword)) {
            wrongRequestValues.put("repeatPassword", "Passwords don't match");
        }
        if (StringUtils.isEmpty(name)) {
            wrongRequestValues.put("name", "Please enter a Name");
        }
        if (StringUtils.isEmpty(surname)) {
            wrongRequestValues.put("surname", "Please enter a Surname");
        }
        if (StringUtils.isEmpty(phoneNumber)) {
            wrongRequestValues.put("phoneNumber", "Please enter a Phone number");
        }

        //TODO Encrypt password!!

        if ( !wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
        } else {
            UserDao userDao = new UserDaoImpl();
            try {
                userDao.addUser(content);
            } catch (DaoException e) {
                //TODO add log??
            }
        }
    }

    @Override
    public User signIn(RequestContent content) {
        String email = content.getParameters().get("email");
        String password = content.getParameters().get("password");

        //TODO add validation and localization for wrong messages
        Map<String,String> wrongRequestValues = new HashMap<>();
        if (StringUtils.isEmpty(email)) {
            wrongRequestValues.put("email", "Please enter a email");
        }
        if ( StringUtils.isEmpty(password)) {
            wrongRequestValues.put("password", "Please, enter a Password");
        }

        //TODO Encrypt password!!

        User user = null;
        List<RoomRequest> roomRequests = null;
        List<RoomOrder> roomOrders = null;
        if ( !wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
        } else {
            UserDao userDao = new UserDaoImpl();
            OrderDao orderDao = new OrderDaoImpl();
            try {
                user = userDao.findUserByEmailAndPassword(email, password);
                roomOrders = orderDao.findAllOrdersByUserId(user.getId());

                content.addSessionAttribute("roomOrders", roomOrders);
            } catch (DaoException e) {
                //TODO add log??
            }
        }

        return user;
    }

    @Override
    public RequestContent signOut(RequestContent content) {
        //TODO ???? remove method??
        System.out.println("SignOut dao");
        return content;
    }

    @Override
    public void addRoomRequest(RequestContent content) {
        String checkInRequest = content.getParameters().get("checkInRequest");
        String checkOutRequest = content.getParameters().get("checkOutRequest");
        String roomSizeRequest = content.getParameters().get("roomSizeRequest");
        String roomTypeRequest = content.getParameters().get("roomTypeRequest");
        User user = (User) content.getSessionAttributes().get("user");

        Map<String,String> wrongRequestValues = new HashMap<>();
        //TODO add validation and localization for wrong messages

        if ( !wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
        } else {
            RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
            try {
                roomRequestDao.addRoomRequest(content);
            } catch (DaoException e) {
                //TODO add log??
            }
        }

    }
}
