package com.pronovich.hotelbooking.dao;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;

public interface UserDao {

    void addUser(RequestContent requestContent)throws DaoException;

    User findUserByEmailAndPassword(String email, String password) throws DaoException;

    Integer getRoleIdByName(String role) throws DaoException;

//    UserEntity getUserByEmail(String email);

//    UserEntity getUserById(Integer userId);

}
