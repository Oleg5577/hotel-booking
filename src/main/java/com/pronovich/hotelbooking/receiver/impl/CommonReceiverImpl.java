package com.pronovich.hotelbooking.receiver.impl;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.RoomDao;
import com.pronovich.hotelbooking.dao.UserDao;
import com.pronovich.hotelbooking.dao.impl.RoomDaoImpl;
import com.pronovich.hotelbooking.dao.impl.UserDaoImpl;
import com.pronovich.hotelbooking.entity.Room;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.receiver.CommonReceiver;
import com.pronovich.hotelbooking.utils.PasswordUtils;
import com.pronovich.hotelbooking.validator.CommonReceiverValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CommonReceiverImpl implements CommonReceiver {

    private static final Logger LOGGER = LogManager.getLogger(CommonReceiverImpl.class);

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String USER_PARAM = "user";
    private static final String UPDATED_USER_PARAM = "updatedUser";
    private static final String EMAIL_OR_PASSWORD_PARAM = "emailOrPassword";
    private static final String ROOM_LIST_PARAM = "roomList";

    @Override
    public void signUp(RequestContent content) {
        Map<String, String> wrongRequestValues = CommonReceiverValidator.signUpValidate(content);

        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
            return;
        }

        try {
            byte[] salt = PasswordUtils.getSalt();
            String password = content.getRequestParameters().get(PASSWORD_PARAM);
            String securePassword = PasswordUtils.getSecurePassword(password, salt);

            String encodedSalt = Base64.getEncoder().encodeToString(salt);

            content.addRequestAttributes("securePassword", securePassword);
            content.addRequestAttributes("encodedSalt", encodedSalt);

            UserDao userDao = new UserDaoImpl();
            userDao.addUser(content);
        } catch (DaoException e) {
            LOGGER.error("Sign up error", e);
        }
    }

    @Override
    public void signIn(RequestContent content) {
        Map<String, String> wrongRequestValues = CommonReceiverValidator.signInValidate(content);

        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
            return;
        }

        User user = null;
        String email = content.getRequestParameters().get(EMAIL_PARAM);
        String password = content.getRequestParameters().get(PASSWORD_PARAM);
        UserDao userDao = new UserDaoImpl();
        try {
            String encodedSalt = userDao.findPasswordSaltByEmail(email);
            byte[] salt = Base64.getDecoder().decode(encodedSalt);
            String securePassword = PasswordUtils.getSecurePassword(password, salt);

            user = userDao.findUserByEmailAndPassword(email, securePassword);
            if (user == null) {
                wrongRequestValues.put(EMAIL_OR_PASSWORD_PARAM, "Password or Email are incorrect");
                content.addWrongValues(wrongRequestValues);
                return;
            }
        } catch (DaoException e) {
            LOGGER.error("Sign in error", e);
        }
        content.addSessionAttribute(USER_PARAM, user);
    }

    @Override
    public void editUserInfo(RequestContent content) {
        Map<String, String> wrongRequestValues = CommonReceiverValidator.editUserInfoValidate(content);

        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
            return;
        }

        String email = content.getRequestParameters().get(EMAIL_PARAM);
        String password = content.getRequestParameters().get(PASSWORD_PARAM);
        try {
            UserDao userDao = new UserDaoImpl();
            String encodedSalt = userDao.findPasswordSaltByEmail(email);
            byte[] salt = Base64.getDecoder().decode(encodedSalt);

            String securePassword = PasswordUtils.getSecurePassword(password, salt);
            String realPassword = userDao.findPasswordByEmail(email);

            if (securePassword.equals(realPassword)) {
                userDao.updateUser(content);
                User updatedUser = userDao.findUserByEmail(email);
                content.addSessionAttribute(UPDATED_USER_PARAM, updatedUser);
            } else {
                wrongRequestValues.put(PASSWORD_PARAM, "Password is incorrect");
                content.addWrongValues(wrongRequestValues);
            }
        } catch (DaoException e) {
            LOGGER.error("Sign up error", e);
        }
    }

    @Override
    public void findRoomsDescription(RequestContent content) {
        RoomDao roomDao = new RoomDaoImpl();
        try {
            List<Room> roomList = roomDao.findRoomsWithUniqueType();
            content.addRequestAttributes(ROOM_LIST_PARAM, roomList);
        } catch (DaoException e) {
            LOGGER.error("Find rooms descriptions error", e);
        }
    }
}
