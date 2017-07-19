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

        if ( !wrongRequestValues.isEmpty()) {
            content.addWrongValues(wrongRequestValues);
        } else {
            UserDao userDao = new UserDaoImpl();
            try {
                user = userDao.findUserByEmailAndPassword(email, password);
            } catch (DaoException e) {
                //TODO add log??
            }
        }
        return user;
    }

    public RequestContent signOut(RequestContent content) {
        System.out.println("SignOut dao");
        return content;
    }
}
