package com.pronovich.hotelbooking.dao;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;

public interface UserDao {

    void addUser(RequestContent requestContent)throws DaoException;

    User findUserByEmailAndPassword(String email, String password) throws DaoException;

    Integer findRoleIdByName(String role) throws DaoException;

    User findUserById(Integer userId) throws DaoException;

    User findUserByEmail(String email) throws DaoException;

    String findPasswordSaltByEmail(String email) throws DaoException;

    String findPasswordByEmail(String email) throws DaoException;

    void updateUser(RequestContent content) throws DaoException;
}
