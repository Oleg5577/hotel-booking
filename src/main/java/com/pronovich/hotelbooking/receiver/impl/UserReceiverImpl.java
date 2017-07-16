package com.pronovich.hotelbooking.receiver.impl;


import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.UserDao;
import com.pronovich.hotelbooking.dao.impl.UserDaoImpl;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.receiver.UserReceiver;

public class UserReceiverImpl implements UserReceiver {

    @Override
    public void signUp(RequestContent requestContent) {

        String email = requestContent.getParameters().get("email");
        String password = requestContent.getParameters().get("password");
        String repeatPassword = requestContent.getParameters().get("repeatPassword");
        String name = requestContent.getParameters().get("name");
        String surname = requestContent.getParameters().get("surname");
        String phoneNumber = requestContent.getParameters().get("phoneNumber");

        //TODO Encrypt password!!

        String emailInDB = "email1";
        String passInDB = "pass1";

        boolean emailPass = email.equals(emailInDB);
        boolean passwordPass = passInDB.equals(passInDB);

        UserDao userDao = new UserDaoImpl();
        try {
            userDao.addUser(requestContent);
        } catch (DaoException e) {
            //TODO add log??
        }
    }

    @Override
    public void signIn(RequestContent content) {
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
    }

    public void signOut(RequestContent content) {
        System.out.println("SignOut dao");
    }
}
