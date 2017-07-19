package com.pronovich.hotelbooking.receiver.impl;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.UserDao;
import com.pronovich.hotelbooking.dao.impl.UserDaoImpl;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.receiver.UserReceiver;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class UserReceiverImpl implements UserReceiver {

    @Override
    public RequestContent signUp(RequestContent requestContent) {
        String email = requestContent.getParameters().get("email");
        String password = requestContent.getParameters().get("password");
        String repeatPassword = requestContent.getParameters().get("repeatPassword");
        String name = requestContent.getParameters().get("name");
        String surname = requestContent.getParameters().get("surname");
        String phoneNumber = requestContent.getParameters().get("phoneNumber");

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
            requestContent.addWrongValues(wrongRequestValues);
        } else {
            UserDao userDao = new UserDaoImpl();
            try {
                userDao.addUser(requestContent);
            } catch (DaoException e) {
                //TODO add log??
            }
        }
        return requestContent;
    }

    @Override
    public RequestContent signIn(RequestContent content) {
        String email = content.getParameters().get("email");
        String password = content.getParameters().get("password");
        //TODO Encrypt password!!

        String emailInDB = "email1";
        String passInDB = "pass1";

        boolean emailPass = email.equals(emailInDB);
        boolean passwordPass = passInDB.equals(passInDB);

        UserDao userDao = new UserDaoImpl();
        User user = null;
        try {
            user = userDao.findUserByEmailAndPassword(email, password);
        } catch (DaoException e) {
            //TODO add log??
        }

        //TODO success sign in or not
        if (user != null) {

        } else {

        }
        return content;
    }

    public RequestContent signOut(RequestContent content) {
        System.out.println("SignOut dao");
        return content;
    }
}
