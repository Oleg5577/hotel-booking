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
import com.pronovich.hotelbooking.util.PasswordUtility;
import com.pronovich.hotelbooking.validator.CommonValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CommonReceiverImpl implements CommonReceiver {

    private static final Logger LOGGER = LogManager.getLogger(CommonReceiverImpl.class);

    private static final String BUNDLE = "property/wrongValues";

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String NEW_PASSWORD_PARAM = "newPassword";
    private static final String USER_PARAM = "user";
    private static final String UPDATED_USER_PARAM = "updatedUser";
    private static final String EMAIL_OR_PASSWORD_PARAM = "emailOrPassword";
    private static final String ROOM_LIST_PARAM = "roomList";
    private static final String SECURE_PASSWORD_PARAM = "securePassword";
    private static final String ENCODED_SALT_PARAM = "encodedSalt";

    @Override
    public void signUp(RequestContent content) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, Locale.getDefault());
        Map<String, String> wrongRequestValues = CommonValidator.signUpValidate(content);

        String email = content.getRequestParameters().get(EMAIL_PARAM);

        if (emailExists(email)) {
            wrongRequestValues.put(EMAIL_PARAM, resourceBundle.getString("email-already-exists"));
        }

        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
            return;
        }

        try {
            byte[] salt = PasswordUtility.getSalt();
            String password = content.getRequestParameters().get(PASSWORD_PARAM);
            String securePassword = PasswordUtility.getSecurePassword(password, salt);

            String encodedSalt = Base64.getEncoder().encodeToString(salt);

            content.addRequestAttributes(SECURE_PASSWORD_PARAM, securePassword);
            content.addRequestAttributes(ENCODED_SALT_PARAM, encodedSalt);

            UserDao userDao = new UserDaoImpl();
            userDao.addUser(content);
        } catch (DaoException e) {
            LOGGER.error("Sign up error", e);
        }
    }

    @Override
    public void signIn(RequestContent content) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, Locale.getDefault());
        Map<String, String> wrongRequestValues = CommonValidator.signInValidate(content);

        User user = null;

        String email = content.getRequestParameters().get(EMAIL_PARAM);
        String password = content.getRequestParameters().get(PASSWORD_PARAM);

        if (!emailExists(email) && !StringUtils.isEmpty(password)) {
            wrongRequestValues.put(EMAIL_OR_PASSWORD_PARAM, resourceBundle.getString("email-or-password-incorrect"));
        }

        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
            return;
        }

        UserDao userDao = new UserDaoImpl();
        try {
            String encodedSalt = userDao.findPasswordSaltByEmail(email);
            byte[] salt = Base64.getDecoder().decode(encodedSalt);
            String securePassword = PasswordUtility.getSecurePassword(password, salt);

            user = userDao.findUserByEmailAndPassword(email, securePassword);
            if (user == null) {
                wrongRequestValues.put(EMAIL_OR_PASSWORD_PARAM, resourceBundle.getString("email-or-password-incorrect"));
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
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, Locale.getDefault());
        Map<String, String> wrongRequestValues = CommonValidator.editUserInfoValidate(content);

        String email = content.getRequestParameters().get(EMAIL_PARAM);
        String password = content.getRequestParameters().get(PASSWORD_PARAM);

        User user = (User) content.getSessionAttributes().get(USER_PARAM);
        if (user == null) {
            wrongRequestValues.put(USER_PARAM, resourceBundle.getString("user-unauthorized"));
        } else if (!emailBelongsUser(email, user)) {
            wrongRequestValues.put(USER_PARAM, resourceBundle.getString("user-edit-foreign-account"));
        }

        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
            return;
        }

        try {
            UserDao userDao = new UserDaoImpl();
            String encodedSalt = userDao.findPasswordSaltByEmail(email);
            byte[] salt = Base64.getDecoder().decode(encodedSalt);

            String securePassword = PasswordUtility.getSecurePassword(password, salt);
            String realPassword = userDao.findPasswordByEmail(email);

            if (securePassword.equals(realPassword)) {
                userDao.updateUser(content);
                User updatedUser = userDao.findUserByEmail(email);
                content.addSessionAttribute(UPDATED_USER_PARAM, updatedUser);
            } else {
                wrongRequestValues.put(PASSWORD_PARAM, resourceBundle.getString("password-incorrect"));
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

    @Override
    public void changePassword(RequestContent content) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, Locale.getDefault());
        Map<String, String> wrongRequestValues = CommonValidator.changePasswordValidate(content);

        if (!wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
            return;
        }

        User user = (User) content.getSessionAttributes().get(USER_PARAM);
        String password = content.getRequestParameters().get(PASSWORD_PARAM);
        String newPassword = content.getRequestParameters().get(NEW_PASSWORD_PARAM);

        String email = user.getEmail();
        UserDao userDao = new UserDaoImpl();
        try {
            String encodedSalt = userDao.findPasswordSaltByEmail(email);
            byte[] salt = Base64.getDecoder().decode(encodedSalt);
            String securePassword = PasswordUtility.getSecurePassword(password, salt);

            if (isCorrectPassword(email, securePassword)) {
                String newSecurePassword = PasswordUtility.getSecurePassword(newPassword, salt);
                userDao.changePasswordForUser(email, newSecurePassword);
            } else {
                wrongRequestValues.put(PASSWORD_PARAM, resourceBundle.getString("password-incorrect"));
                content.addWrongValues(wrongRequestValues);
            }
        } catch (DaoException e) {
            LOGGER.error("Sign in error", e);
        }
    }

    private boolean isCorrectPassword(String email, String securePassword) throws DaoException {
        UserDao userDao = new UserDaoImpl();
        return userDao.findUserByEmailAndPassword(email, securePassword) != null;

    }

    private static boolean emailBelongsUser(String email, User user) {
        return email.equals(user.getEmail());
    }

    private static boolean emailExists(String email) {
        UserDao userDao = new UserDaoImpl();
        boolean emailExists = false;
        try {
            emailExists = userDao.findUserByEmail(email) != null;
        } catch (DaoException e) {
            LOGGER.error("Checking if email exists error", e);
        }
        return emailExists;
    }
}
