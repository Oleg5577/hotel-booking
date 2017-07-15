package com.pronovich.hotelbooking.dao;

import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;

import java.util.Map;

public interface UserDao {

    void addUser(Map<String, String> userValues);

    User findUserByEmailAndPassword(String email, String password) throws DaoException;

//    UserEntity getUserByEmail(String email);

//    UserEntity getUserById(Integer userId);

}
