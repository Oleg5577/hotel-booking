package com.pronovich.hotelbooking.validator;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.UserDao;
import com.pronovich.hotelbooking.dao.impl.UserDaoImpl;
import com.pronovich.hotelbooking.exception.DaoException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ContentValidator {

    private static final Logger LOGGER = LogManager.getLogger(ContentValidator.class);

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String REPEAT_PASSWORD_PARAM = "repeatPassword";
    private static final String NAME_PARAM = "name";
    private static final String SURNAME_PARAM = "surname";
    private static final String PHONE_NUMBER_PARAM = "phoneNumber";

    private static final int MIN_PASSWORD_SIZE = 6;

    public static Map<String, String> signUpValidate(RequestContent content) {
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
        return wrongRequestValues;
    }

    private static boolean emailExists(String email) {
        UserDao userDao = new UserDaoImpl();
        boolean emailExists = false;
        try {
            emailExists = userDao.findUserByEmail(email) != null;
        } catch (DaoException e) {
            LOGGER.error("Check if email exists error", e);
        }
        return emailExists;
    }
}
