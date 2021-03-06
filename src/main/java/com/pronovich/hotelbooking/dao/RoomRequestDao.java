package com.pronovich.hotelbooking.dao;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.entity.RequestStatus;
import com.pronovich.hotelbooking.exception.DaoException;

import java.util.List;

public interface RoomRequestDao {

    void addRoomRequest(RequestContent requestContent) throws DaoException;

    List<RoomRequest> findAllRequestsByUser(User user) throws DaoException;

    List<RoomRequest> findAllRequestsForAllUser() throws DaoException;

    RoomRequest findRequestById(Integer requestId) throws DaoException;

    void changeStatusTo(Integer requestId, RequestStatus newStatus) throws DaoException;

    void removeRoomRequestById(Integer requestId) throws DaoException;

//    void changeRequestStatusToDenied(Integer requestId) throws DaoException;
}
