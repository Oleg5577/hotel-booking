package com.pronovich.hotelbooking.receiver.impl;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.RoomOrderDao;
import com.pronovich.hotelbooking.dao.RoomRequestDao;
import com.pronovich.hotelbooking.dao.UserDao;
import com.pronovich.hotelbooking.dao.impl.RoomOrderDaoImpl;
import com.pronovich.hotelbooking.dao.impl.RoomRequestDaoImpl;
import com.pronovich.hotelbooking.dao.impl.UserDaoImpl;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.entity.Role;
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.receiver.CommonReceiver;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonReceiverImpl implements CommonReceiver {

    @Override
    public void signUp(RequestContent content) {
        String email = content.getRequestParameters().get("email");
        String password = content.getRequestParameters().get("password");
        String repeatPassword = content.getRequestParameters().get("repeatPassword");
        String name = content.getRequestParameters().get("name");
        String surname = content.getRequestParameters().get("surname");
        String phoneNumber = content.getRequestParameters().get("phoneNumber");

        // TODO добавить валидацию данных запроса Sign Up
        //TODO add localization messages

        Map<String, String> wrongRequestValues = new HashMap<>();

        if (StringUtils.isEmpty(email)) {
            wrongRequestValues.put("email", "Please enter a email");
        }
        if (StringUtils.isEmpty(password)) {
            wrongRequestValues.put("password", "Please, enter a Password");
        }
        if (StringUtils.isEmpty(repeatPassword)) {
            wrongRequestValues.put("repeatPassword", "Please, repeat Password");
        } else if (!password.equals(repeatPassword)) {
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

        if (!wrongRequestValues.isEmpty()) {
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
        String email = content.getRequestParameters().get("email");
        String password = content.getRequestParameters().get("password");

        //TODO add validation and localization for wrong messages
        Map<String, String> wrongRequestValues = new HashMap<>();
        if (StringUtils.isEmpty(email)) {
            wrongRequestValues.put("email", "Please enter a email");
        }
        if (StringUtils.isEmpty(password)) {
            wrongRequestValues.put("password", "Please, enter a Password");
        }

        //TODO Encrypt password!!

        User user = null;
        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
        } else {
            UserDao userDao = new UserDaoImpl();
            RoomOrderDao orderDao = new RoomOrderDaoImpl();
            RoomRequestDao roomRequestDao = new RoomRequestDaoImpl();
            try {
                user = userDao.findUserByEmailAndPassword(email, password);
                if (user == null) {
                    wrongRequestValues.put("emailOrPassword" , "Password or Email are incorrect");
                    content.addWrongValues(wrongRequestValues);
                } else {
                    List<RoomOrder>  roomOrders = orderDao.findAllOrdersByUser(user);
                    List<RoomRequest>  roomRequests = roomRequestDao.findAllRequestsByUser(user);

                    content.addSessionAttribute("listRoomOrders", roomOrders);
                    content.addSessionAttribute("listRoomRequests", roomRequests);
                }
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
}
