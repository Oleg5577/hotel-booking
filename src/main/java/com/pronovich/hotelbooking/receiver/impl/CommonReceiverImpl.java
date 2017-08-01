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
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.receiver.CommonReceiver;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonReceiverImpl implements CommonReceiver {

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String REPEAT_PASSWORD_PARAM = "repeatPassword";
    private static final String NAME_PARAM = "name";
    private static final String SURNAME_PARAM = "surname";
    private static final String PHONE_NUMBER_PARAM = "phoneNumber";

    private static final int MIN_PASSWORD_SIZE = 6;

    @Override
    public void signUp(RequestContent content) {
        String email = content.getRequestParameters().get(EMAIL_PARAM);
        String password = content.getRequestParameters().get(PASSWORD_PARAM);
        String repeatPassword = content.getRequestParameters().get(REPEAT_PASSWORD_PARAM);
        String name = content.getRequestParameters().get(NAME_PARAM);
        String surname = content.getRequestParameters().get(SURNAME_PARAM);
        String phoneNumber = content.getRequestParameters().get(PHONE_NUMBER_PARAM);

        //TODO add localization messages

        Map<String, String> wrongRequestValues = new HashMap<>();

        EmailValidator emailValidator = EmailValidator.getInstance();
        if (StringUtils.isEmpty(email)) {
            wrongRequestValues.put(EMAIL_PARAM, "Please enter a email");
        } else if ( !emailValidator.isValid(email) ) {
            wrongRequestValues.put(EMAIL_PARAM, "Email is not valid");
        } else if (emailExists(email)) {
            wrongRequestValues.put(EMAIL_PARAM, "Email already exists");
        }

        if (StringUtils.isEmpty(password)) {
            wrongRequestValues.put(PASSWORD_PARAM, "Please, enter a Password");
        } else if ( password.length() < MIN_PASSWORD_SIZE ) {
            wrongRequestValues.put(PASSWORD_PARAM, "Enter " + MIN_PASSWORD_SIZE + " or more characters");
        }

        if (StringUtils.isEmpty(repeatPassword)) {
            wrongRequestValues.put(REPEAT_PASSWORD_PARAM, "Please, repeat the Password");
        } else if ( !password.equals(repeatPassword) ) {
            wrongRequestValues.put(REPEAT_PASSWORD_PARAM, "Passwords don't match");
        }

        if (StringUtils.isEmpty(name)) {
            wrongRequestValues.put(NAME_PARAM, "Please enter a Name");
        }

        if (StringUtils.isEmpty(surname)) {
            wrongRequestValues.put(SURNAME_PARAM, "Please enter a Surname");
        }

        if (StringUtils.isEmpty(phoneNumber)) {
            wrongRequestValues.put(PHONE_NUMBER_PARAM, "Please enter a Phone number");
        }

        //TODO Encrypt password!!

        if ( !wrongRequestValues.isEmpty() ) {
            content.addWrongValues(wrongRequestValues);
        } else {
            try {
                UserDao userDao = new UserDaoImpl();
                userDao.addUser(content);
            } catch (DaoException e) {
                //TODO add log??
            }
        }
    }

    private boolean emailExists(String email) {
        UserDao userDao = new UserDaoImpl();
        boolean emailExists = false;
        try {
            emailExists = userDao.findUserByEmail(email) != null;
        } catch (DaoException e) {
            //TODO add log??
        }
        return emailExists;
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
