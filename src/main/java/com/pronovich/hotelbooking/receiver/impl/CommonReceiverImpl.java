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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CommonReceiverImpl implements CommonReceiver {

    private static final Logger LOGGER = LogManager.getLogger(CommonReceiverImpl.class);

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String REPEAT_PASSWORD_PARAM = "repeatPassword";
    private static final String NAME_PARAM = "name";
    private static final String SURNAME_PARAM = "surname";
    private static final String PHONE_NUMBER_PARAM = "phoneNumber";
    private static final String USER_PARAM = "user";
    private static final String UPDATED_USER_PARAM = "updatedUser";

    private static final int MIN_PASSWORD_SIZE = 6;

    @Override
    public void signUp(RequestContent content) {
        //TODO validate in separate method
//        Map<String, String> wrongRequestValues = ContentValidator.signUpValidate(content);

        String email = content.getRequestParameters().get(EMAIL_PARAM);
        String password = content.getRequestParameters().get(PASSWORD_PARAM);
        String repeatPassword = content.getRequestParameters().get(REPEAT_PASSWORD_PARAM);
        String name = content.getRequestParameters().get(NAME_PARAM);
        String surname = content.getRequestParameters().get(SURNAME_PARAM);
        String phoneNumber = content.getRequestParameters().get(PHONE_NUMBER_PARAM);

//        //TODO add localization messages

        Map<String, String> wrongRequestValues = new HashMap<>();

        EmailValidator emailValidator = EmailValidator.getInstance();
        if (StringUtils.isEmpty(email)) {
            wrongRequestValues.put(EMAIL_PARAM, "Please enter a email");
        } else if (!emailValidator.isValid(email)) {
            wrongRequestValues.put(EMAIL_PARAM, "Email is not valid");
        } else if (emailExists(email)) {
            wrongRequestValues.put(EMAIL_PARAM, "Email already exists");
        }

        if (StringUtils.isEmpty(password)) {
            wrongRequestValues.put(PASSWORD_PARAM, "Please, enter a Password");
        } else if (password.length() < MIN_PASSWORD_SIZE) {
            wrongRequestValues.put(PASSWORD_PARAM, "Enter " + MIN_PASSWORD_SIZE + " or more characters");
        }

        if (StringUtils.isEmpty(repeatPassword)) {
            wrongRequestValues.put(REPEAT_PASSWORD_PARAM, "Please, repeat the Password");
        } else if (!password.equals(repeatPassword)) {
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

        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
        } else {
            try {
                byte[] salt = PasswordUtils.getSalt();
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
    }

    private boolean emailExists(String email) {
        UserDao userDao = new UserDaoImpl();
        boolean emailExists = false;
        try {
            emailExists = userDao.findUserByEmail(email) != null;
        } catch (DaoException e) {
            LOGGER.error("Check if email exists error", e);
        }
        return emailExists;
    }


    @Override
    public void signIn(RequestContent content) {
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

        User user = null;
        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
        } else {
            UserDao userDao = new UserDaoImpl();
            try {
                String encodedSalt = userDao.findPasswordSaltByEmail(email);
                if (encodedSalt == null) {
                    wrongRequestValues.put("emailOrPassword", "Password or Email are incorrect");
                    content.addWrongValues(wrongRequestValues);
                    return;
                }

                byte[] salt = Base64.getDecoder().decode(encodedSalt);
                String securePassword = PasswordUtils.getSecurePassword(password, salt);

                user = userDao.findUserByEmailAndPassword(email, securePassword);
                if (user == null) {
                    wrongRequestValues.put("emailOrPassword", "Password or Email are incorrect");
                    content.addWrongValues(wrongRequestValues);
                    return;
                }
            } catch (DaoException e) {
                LOGGER.error("Sign in error", e);
            }
        }
        content.addSessionAttribute("user", user);
    }



    @Override
    public void editUserInfo(RequestContent content) {
        String email = content.getRequestParameters().get(EMAIL_PARAM);
        String password = content.getRequestParameters().get(PASSWORD_PARAM);
        String name = content.getRequestParameters().get(NAME_PARAM);
        String surname = content.getRequestParameters().get(SURNAME_PARAM);
        String phoneNumber = content.getRequestParameters().get(PHONE_NUMBER_PARAM);
        User user = (User) content.getSessionAttributes().get(USER_PARAM);

        Map<String, String> wrongRequestValues = new HashMap<>();

        if (StringUtils.isEmpty(name)) {
            wrongRequestValues.put(NAME_PARAM, "Please enter a Name");
        }

        if (StringUtils.isEmpty(surname)) {
            wrongRequestValues.put(SURNAME_PARAM, "Please enter a Surname");
        }

        if (StringUtils.isEmpty(phoneNumber)) {
            wrongRequestValues.put(PHONE_NUMBER_PARAM, "Please enter a Phone number");
        }

        if (StringUtils.isEmpty(password)) {
            wrongRequestValues.put(PASSWORD_PARAM, "Please, enter a Password");
        }

        if (user == null) {
            wrongRequestValues.put(USER_PARAM, "Please sign in");
        }

        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
        } else {
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
                    wrongRequestValues.put(PASSWORD_PARAM,"Password is incorrect");
                    content.addWrongValues(wrongRequestValues);
                }
            } catch (DaoException e) {
                LOGGER.error("Sign up error", e);
            }
        }
    }

    @Override
    public void findRoomsDescription(RequestContent content) {
        RoomDao roomDao = new RoomDaoImpl();
        try {
            List<Room> roomList = roomDao.findRoomsWithUniqueType();
            content.addRequestAttributes("roomList", roomList);
        } catch (DaoException e) {
            LOGGER.error("Find rooms descriptions error", e);
        }
    }
}
