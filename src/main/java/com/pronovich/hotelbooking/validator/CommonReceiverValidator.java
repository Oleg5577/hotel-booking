package com.pronovich.hotelbooking.validator;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.UserDao;
import com.pronovich.hotelbooking.dao.impl.UserDaoImpl;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class CommonReceiverValidator {

    private static final Logger LOGGER = LogManager.getLogger(CommonReceiverValidator.class);

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String REPEAT_PASSWORD_PARAM = "repeatPassword";
    private static final String NEW_PASSWORD_PARAM = "newPassword";
    private static final String REPEAT_NEW_PASSWORD_PARAM = "repeatNewPassword";
    private static final String NAME_PARAM = "name";
    private static final String SURNAME_PARAM = "surname";
    private static final String PHONE_NUMBER_PARAM = "phoneNumber";
    private static final String EMAIL_OR_PASSWORD_PARAM = "emailOrPassword";
    private static final String USER_PARAM = "user";

    private static final int MIN_PASSWORD_SIZE = 6;

    private static final String BUNDLE = "property/wrongValues";

    public static Map<String, String> signUpValidate(RequestContent content) {
        String email = content.getRequestParameters().get(EMAIL_PARAM);
        String password = content.getRequestParameters().get(PASSWORD_PARAM);
        String repeatPassword = content.getRequestParameters().get(REPEAT_PASSWORD_PARAM);
        String name = content.getRequestParameters().get(NAME_PARAM);
        String surname = content.getRequestParameters().get(SURNAME_PARAM);
        String phoneNumber = content.getRequestParameters().get(PHONE_NUMBER_PARAM);

        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, Locale.getDefault());

        Map<String, String> wrongRequestValues = new HashMap<>();

        EmailValidator emailValidator = EmailValidator.getInstance();
        if (StringUtils.isEmpty(email)) {
            wrongRequestValues.put(EMAIL_PARAM, resourceBundle.getString("email-empty"));
        } else if (!emailValidator.isValid(email)) {
            wrongRequestValues.put(EMAIL_PARAM, resourceBundle.getString("email-is-not-valid"));
        } else if (emailExists(email)) {
            wrongRequestValues.put(EMAIL_PARAM, resourceBundle.getString("email-already-exists"));
        }

        if (StringUtils.isEmpty(password)) {
            wrongRequestValues.put(PASSWORD_PARAM, resourceBundle.getString("password-empty"));
        } else if (password.length() < MIN_PASSWORD_SIZE) {
            wrongRequestValues.put(PASSWORD_PARAM, resourceBundle.getString("password-too-short-begin")
                    + " " + MIN_PASSWORD_SIZE + " " + resourceBundle.getString("password-too-short-end"));
        }

        if (StringUtils.isEmpty(repeatPassword)) {
            wrongRequestValues.put(REPEAT_PASSWORD_PARAM, resourceBundle.getString("password-repeat-empty"));
        } else if (!password.equals(repeatPassword)) {
            wrongRequestValues.put(REPEAT_PASSWORD_PARAM, resourceBundle.getString("password-repeat-not-match"));
        }

        if (StringUtils.isEmpty(name)) {
            wrongRequestValues.put(NAME_PARAM, resourceBundle.getString("name-empty"));
        }

        if (StringUtils.isEmpty(surname)) {
            wrongRequestValues.put(SURNAME_PARAM, resourceBundle.getString("surname-empty"));
        }

        if (StringUtils.isEmpty(phoneNumber)) {
            wrongRequestValues.put(PHONE_NUMBER_PARAM, resourceBundle.getString("phone-number-empty"));
        }
        return wrongRequestValues;
    }

    public static Map<String, String> signInValidate(RequestContent content) {
        String email = content.getRequestParameters().get(EMAIL_PARAM);
        String password = content.getRequestParameters().get(PASSWORD_PARAM);

        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, Locale.getDefault());

        Map<String, String> wrongRequestValues = new HashMap<>();
        if (StringUtils.isEmpty(email)) {
            wrongRequestValues.put(EMAIL_PARAM, resourceBundle.getString("email-empty"));
        } else if (!emailExists(email) && !StringUtils.isEmpty(password)) {
            wrongRequestValues.put(EMAIL_OR_PASSWORD_PARAM, resourceBundle.getString("email-or-password-incorrect"));
        }

        if (StringUtils.isEmpty(password)) {
            wrongRequestValues.put(PASSWORD_PARAM, resourceBundle.getString("password-empty"));
        }
        return wrongRequestValues;
    }

    public static Map<String, String> editUserInfoValidate(RequestContent content) {
        String email = content.getRequestParameters().get(EMAIL_PARAM);
        String password = content.getRequestParameters().get(PASSWORD_PARAM);
        String name = content.getRequestParameters().get(NAME_PARAM);
        String surname = content.getRequestParameters().get(SURNAME_PARAM);
        String phoneNumber = content.getRequestParameters().get(PHONE_NUMBER_PARAM);
        User user = (User) content.getSessionAttributes().get(USER_PARAM);

        Map<String, String> wrongRequestValues = new HashMap<>();

        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, Locale.getDefault());

        if (user == null) {
            wrongRequestValues.put(USER_PARAM, resourceBundle.getString("user-unauthorized"));
        } else if (!emailBelongsUser(email, user)) {
            wrongRequestValues.put(USER_PARAM, resourceBundle.getString("user-edit-foreign-account"));
        }

        if (StringUtils.isEmpty(name)) {
            wrongRequestValues.put(NAME_PARAM, resourceBundle.getString("name-empty"));
        }

        if (StringUtils.isEmpty(surname)) {
            wrongRequestValues.put(SURNAME_PARAM, resourceBundle.getString("surname-empty"));
        }

        if (StringUtils.isEmpty(phoneNumber)) {
            wrongRequestValues.put(PHONE_NUMBER_PARAM, resourceBundle.getString("phone-number-empty"));
        }

        if (StringUtils.isEmpty(password)) {
            wrongRequestValues.put(PASSWORD_PARAM, resourceBundle.getString("password-empty"));
        }
        return wrongRequestValues;
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

    public static Map<String,String> changePasswordValidate(RequestContent content) {
        String password = content.getRequestParameters().get(PASSWORD_PARAM);
        String newPassword = content.getRequestParameters().get(NEW_PASSWORD_PARAM);
        String repeatNewPassword = content.getRequestParameters().get(REPEAT_NEW_PASSWORD_PARAM);
        User user = (User) content.getSessionAttributes().get(USER_PARAM);

        Map<String, String> wrongRequestValues = new HashMap<>();

        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, Locale.getDefault());

        if (user == null) {
            wrongRequestValues.put(USER_PARAM, resourceBundle.getString("user-unauthorized"));
        }

        if (StringUtils.isEmpty(password)) {
            wrongRequestValues.put(PASSWORD_PARAM, resourceBundle.getString("password-empty"));
        }

        if (StringUtils.isEmpty(newPassword)) {
            wrongRequestValues.put(NEW_PASSWORD_PARAM, resourceBundle.getString("password-empty"));
        } else if (newPassword.length() < MIN_PASSWORD_SIZE) {
            wrongRequestValues.put(NEW_PASSWORD_PARAM, resourceBundle.getString("password-too-short-begin")
                    + " " + MIN_PASSWORD_SIZE + " " + resourceBundle.getString("password-too-short-end"));
        } else if (newPassword.equals(password)) {
            wrongRequestValues.put(NEW_PASSWORD_PARAM, resourceBundle.getString("password-equals-new-password"));
        }

        if (StringUtils.isEmpty(repeatNewPassword)) {
            wrongRequestValues.put(REPEAT_NEW_PASSWORD_PARAM, resourceBundle.getString("password-empty"));
        } else if (!repeatNewPassword.equals(newPassword)) {
            wrongRequestValues.put(REPEAT_NEW_PASSWORD_PARAM, resourceBundle.getString("password-repeat-not-match"));
        }
        return wrongRequestValues;
    }
}
