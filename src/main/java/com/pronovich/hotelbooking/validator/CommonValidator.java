package com.pronovich.hotelbooking.validator;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class CommonValidator {

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String REPEAT_PASSWORD_PARAM = "repeatPassword";
    private static final String NEW_PASSWORD_PARAM = "newPassword";
    private static final String REPEAT_NEW_PASSWORD_PARAM = "repeatNewPassword";
    private static final String NAME_PARAM = "name";
    private static final String SURNAME_PARAM = "surname";
    private static final String PHONE_NUMBER_PARAM = "phoneNumber";
    private static final String USER_PARAM = "user";

    private static final int MIN_PASSWORD_SIZE = 6;

    private static final String BUNDLE = "property/wrongValues";

    private static final String EMAIL_EMPTY = "email-empty";
    private static final String EMAIL_IS_NOT_VALID = "email-is-not-valid";
    private static final String PASSWORD_EMPTY = "password-empty";
    private static final String PASSWORD_TOO_SHORT_BEGIN = "password-too-short-begin";
    private static final String PASSWORD_TOO_SHORT_END = "password-too-short-end";
    private static final String PASSWORD_REPEAT_EMPTY = "password-repeat-empty";
    private static final String PASSWORD_REPEAT_NOT_MATCH = "password-repeat-not-match";
    private static final String NAME_EMPTY = "name-empty";
    private static final String SURNAME_EMPTY = "surname-empty";
    private static final String PHONE_NUMBER_EMPTY = "phone-number-empty";
    private static final String USER_UNAUTHORIZED = "user-unauthorized";
    private static final String PASSWORD_EQUALS_NEW_PASSWORD = "password-equals-new-password";

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
            wrongRequestValues.put(EMAIL_PARAM, resourceBundle.getString(EMAIL_EMPTY));
        } else if (!emailValidator.isValid(email)) {
            wrongRequestValues.put(EMAIL_PARAM, resourceBundle.getString(EMAIL_IS_NOT_VALID));
        }

        if (StringUtils.isEmpty(password)) {
            wrongRequestValues.put(PASSWORD_PARAM, resourceBundle.getString(PASSWORD_EMPTY));
        } else if (password.length() < MIN_PASSWORD_SIZE) {
            wrongRequestValues.put(PASSWORD_PARAM, resourceBundle.getString(PASSWORD_TOO_SHORT_BEGIN)
                    + " " + MIN_PASSWORD_SIZE + " " + resourceBundle.getString(PASSWORD_TOO_SHORT_END));
        }

        if (StringUtils.isEmpty(repeatPassword)) {
            wrongRequestValues.put(REPEAT_PASSWORD_PARAM, resourceBundle.getString(PASSWORD_REPEAT_EMPTY));
        } else if (!password.equals(repeatPassword)) {
            wrongRequestValues.put(REPEAT_PASSWORD_PARAM, resourceBundle.getString(PASSWORD_REPEAT_NOT_MATCH));
        }

        if (StringUtils.isEmpty(name)) {
            wrongRequestValues.put(NAME_PARAM, resourceBundle.getString(NAME_EMPTY));
        }

        if (StringUtils.isEmpty(surname)) {
            wrongRequestValues.put(SURNAME_PARAM, resourceBundle.getString(SURNAME_EMPTY));
        }

        if (StringUtils.isEmpty(phoneNumber)) {
            wrongRequestValues.put(PHONE_NUMBER_PARAM, resourceBundle.getString(PHONE_NUMBER_EMPTY));
        }
        return wrongRequestValues;
    }

    public static Map<String, String> signInValidate(RequestContent content) {
        String email = content.getRequestParameters().get(EMAIL_PARAM);
        String password = content.getRequestParameters().get(PASSWORD_PARAM);

        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, Locale.getDefault());

        Map<String, String> wrongRequestValues = new HashMap<>();
        if (StringUtils.isEmpty(email)) {
            wrongRequestValues.put(EMAIL_PARAM, resourceBundle.getString(EMAIL_EMPTY));
        }

        if (StringUtils.isEmpty(password)) {
            wrongRequestValues.put(PASSWORD_PARAM, resourceBundle.getString(PASSWORD_EMPTY));
        }
        return wrongRequestValues;
    }

    public static Map<String, String> editUserInfoValidate(RequestContent content) {
        String password = content.getRequestParameters().get(PASSWORD_PARAM);
        String name = content.getRequestParameters().get(NAME_PARAM);
        String surname = content.getRequestParameters().get(SURNAME_PARAM);
        String phoneNumber = content.getRequestParameters().get(PHONE_NUMBER_PARAM);

        Map<String, String> wrongRequestValues = new HashMap<>();

        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, Locale.getDefault());

        if (StringUtils.isEmpty(name)) {
            wrongRequestValues.put(NAME_PARAM, resourceBundle.getString(NAME_EMPTY));
        }

        if (StringUtils.isEmpty(surname)) {
            wrongRequestValues.put(SURNAME_PARAM, resourceBundle.getString(SURNAME_EMPTY));
        }

        if (StringUtils.isEmpty(phoneNumber)) {
            wrongRequestValues.put(PHONE_NUMBER_PARAM, resourceBundle.getString(PHONE_NUMBER_EMPTY));
        }

        if (StringUtils.isEmpty(password)) {
            wrongRequestValues.put(PASSWORD_PARAM, resourceBundle.getString(PASSWORD_EMPTY));
        }
        return wrongRequestValues;
    }

    public static Map<String,String> changePasswordValidate(RequestContent content) {
        String password = content.getRequestParameters().get(PASSWORD_PARAM);
        String newPassword = content.getRequestParameters().get(NEW_PASSWORD_PARAM);
        String repeatNewPassword = content.getRequestParameters().get(REPEAT_NEW_PASSWORD_PARAM);
        User user = (User) content.getSessionAttributes().get(USER_PARAM);

        Map<String, String> wrongRequestValues = new HashMap<>();

        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, Locale.getDefault());

        if (user == null) {
            wrongRequestValues.put(USER_PARAM, resourceBundle.getString(USER_UNAUTHORIZED));
        }

        if (StringUtils.isEmpty(password)) {
            wrongRequestValues.put(PASSWORD_PARAM, resourceBundle.getString(PASSWORD_EMPTY));
        }

        if (StringUtils.isEmpty(newPassword)) {
            wrongRequestValues.put(NEW_PASSWORD_PARAM, resourceBundle.getString(PASSWORD_EMPTY));
        } else if (newPassword.length() < MIN_PASSWORD_SIZE) {
            wrongRequestValues.put(NEW_PASSWORD_PARAM, resourceBundle.getString(PASSWORD_TOO_SHORT_BEGIN)
                    + " " + MIN_PASSWORD_SIZE + " " + resourceBundle.getString(PASSWORD_TOO_SHORT_END));
        } else if (newPassword.equals(password)) {
            wrongRequestValues.put(NEW_PASSWORD_PARAM, resourceBundle.getString(PASSWORD_EQUALS_NEW_PASSWORD));
        }

        if (StringUtils.isEmpty(repeatNewPassword)) {
            wrongRequestValues.put(REPEAT_NEW_PASSWORD_PARAM, resourceBundle.getString(PASSWORD_EMPTY));
        } else if (!repeatNewPassword.equals(newPassword)) {
            wrongRequestValues.put(REPEAT_NEW_PASSWORD_PARAM, resourceBundle.getString(PASSWORD_REPEAT_NOT_MATCH));
        }
        return wrongRequestValues;
    }
}
