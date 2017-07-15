package com.pronovich.hotelbooking.receiver.impl;


import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.UserDao;
import com.pronovich.hotelbooking.dao.impl.UserDaoImpl;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.receiver.UserReceiver;

public class UserReceiverImpl implements UserReceiver {

    @Override
    public void signIn(RequestContent content) {
        String email = content.getRequestParameters().get("email");
        String password = content.getRequestParameters().get("password");
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
    }

    public void signOut(RequestContent content) {
        System.out.println("SignOut dao");
    }
}
