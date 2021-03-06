package com.pronovich.hotelbooking.dao.impl;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.RoomDao;
import com.pronovich.hotelbooking.dao.RoomRequestDao;
import com.pronovich.hotelbooking.dao.UserDao;
import com.pronovich.hotelbooking.dao.connectionpool.ConnectionPool;
import com.pronovich.hotelbooking.dao.connectionpool.ProxyConnection;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.entity.RequestStatus;
import com.pronovich.hotelbooking.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomRequestDaoImpl extends AbstractBaseDao implements RoomRequestDao {

    private static final String USER_PARAM = "user";
    private static final String ROOM_TYPE_REQUEST_PARAM = "roomTypeRequest";
    private static final String CHECK_IN_REQUEST_PARAM = "checkInRequest";
    private static final String CHECK_OUT_REQUEST_PARAM = "checkOutRequest";
    private static final String ROOM_SIZE_REQUEST_PARAM = "roomSizeRequest";
    private static final String FK_USER_ID_PARAM = "fk_user_id";

    private static final String ADD_ROOM_REQUEST_SQL = "INSERT INTO hotel_booking_db.room_request " +
            "(check_in, check_out, room_size, fk_user_id, fk_room_type_id) VALUES (?, ?, ?, ?, ?)";

    private static final String FIND_ALL_ROOM_REQUESTS_BY_USER_SQL = "SELECT `request_id`, `check_in`, `check_out`, `room_size`, " +
            "`request_status`, `type_name` FROM `hotel_booking_db`.`room_request` " +
            "LEFT JOIN `room_type` ON `room_request`.`fk_room_type_id` = `room_type`.`room_type_id` " +
            "WHERE `fk_user_id` = ? ORDER BY `request_status`, `check_in`;";

    private static final String FIND_ALL_ROOM_REQUESTS_FOR_ALL_USERS_SQL = "SELECT `request_id`, `check_in`, `check_out`, `room_size`, " +
            "`request_status`, `type_name`, `fk_user_id` FROM `hotel_booking_db`.`room_request` " +
            "LEFT JOIN `room_type` ON `room_request`.`fk_room_type_id` = `room_type`.`room_type_id` ORDER BY `request_status`, `check_in`";

    private static final String FIND_ROOM_REQUEST_BY_ID_SQL = "SELECT request_id, check_in, check_out, room_size," +
            " request_status, fk_user_id, type_name FROM room_request " +
            "LEFT JOIN room_type ON room_request.fk_room_type_id = room_type.room_type_id " +
            "WHERE request_id = ?";

    private static final String UPDATE_ROOM_REQUEST_STATUS_SQL = "UPDATE room_request SET request_status = ? " +
            "WHERE request_id = ?";

    private static final String REMOVE_REQUEST_BY_ID_SQL = "DELETE FROM room_request " +
            "WHERE request_id = ?";

    @Override
    public void addRoomRequest(RequestContent requestContent) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(ADD_ROOM_REQUEST_SQL);

            Map<String, String> requestParameters = requestContent.getRequestParameters();
            User user = (User) requestContent.getSessionAttributes().get(USER_PARAM);

            RoomDao roomDao = new RoomDaoImpl();
            int roomId = roomDao.findRoomTypeIdByName(requestParameters.get(ROOM_TYPE_REQUEST_PARAM));

            statement.setDate(1, Date.valueOf( requestParameters.get(CHECK_IN_REQUEST_PARAM)) );
            statement.setDate(2, Date.valueOf( requestParameters.get(CHECK_OUT_REQUEST_PARAM)) );
            statement.setInt(3, Integer.valueOf(requestParameters.get(ROOM_SIZE_REQUEST_PARAM)) );
            statement.setInt(4, user.getId());
            statement.setInt(5, roomId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement);
        }
    }

    @Override
    public List<RoomRequest> findAllRequestsByUser(User user) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<RoomRequest> roomRequests = new ArrayList<>();
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(FIND_ALL_ROOM_REQUESTS_BY_USER_SQL);
            statement.setInt(1, user.getId());

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roomRequests.add(ResultSetConverter.createRequestEntity(resultSet, user));
            }
            return roomRequests;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }

    @Override
    public List<RoomRequest> findAllRequestsForAllUser() throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<RoomRequest> roomRequests = new ArrayList<>();
        UserDao userDao = new UserDaoImpl();
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(FIND_ALL_ROOM_REQUESTS_FOR_ALL_USERS_SQL);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = userDao.findUserById( resultSet.getInt(FK_USER_ID_PARAM) );
                roomRequests.add(ResultSetConverter.createRequestEntity(resultSet, user));
            }
            return roomRequests;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }

    @Override
    public RoomRequest findRequestById(Integer requestId) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        RoomRequest roomRequest = null;
        UserDao userDao = new UserDaoImpl();
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(FIND_ROOM_REQUEST_BY_ID_SQL);
            statement.setInt(1, requestId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = userDao.findUserById(resultSet.getInt(FK_USER_ID_PARAM));
                roomRequest = ResultSetConverter.createRequestEntity(resultSet, user);
            }
            return roomRequest;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }

    @Override
    public void changeStatusTo(Integer requestId, RequestStatus newStatus) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(UPDATE_ROOM_REQUEST_STATUS_SQL);
            statement.setString(1, newStatus.toString().toLowerCase());
            statement.setInt(2, requestId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement);
        }
    }

    @Override
    public void removeRoomRequestById(Integer requestId) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(REMOVE_REQUEST_BY_ID_SQL);
            statement.setInt(1, requestId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement);
        }
    }
}
