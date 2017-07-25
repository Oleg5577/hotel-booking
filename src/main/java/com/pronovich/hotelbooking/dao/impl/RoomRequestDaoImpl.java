package com.pronovich.hotelbooking.dao.impl;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.dao.RoomDao;
import com.pronovich.hotelbooking.dao.RoomRequestDao;
import com.pronovich.hotelbooking.dao.connectionpool.ConnectionPool;
import com.pronovich.hotelbooking.dao.connectionpool.ProxyConnection;
import com.pronovich.hotelbooking.dao.daoutils.ResultSetConverter;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.exception.DaoException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomRequestDaoImpl extends AbstractBaseDao implements RoomRequestDao {

    private static final String ADD_ROOM_REQUEST_SQL = "INSERT INTO hotel_booking_db.room_request " +
            "(check_in, check_out, room_size, user_id, room_type_id) VALUES (?, ?, ?, ?, ?)";

    private static final String FIND_ALL_REQUESTS_BY_USER_SQL = "SELECT `request_id`, `check_in`, `check_out`, `room_size`, " +
            "`request_status`, `type_name` FROM `hotel_booking_db`.`room_request` " +
            "LEFT JOIN `room_type` ON `room_request`.`fk_room_type_id` = `room_type`.`room_type_id` " +
            "WHERE `fk_user_id` = ?;";

    @Override
    public void addRoomRequest(RequestContent requestContent) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(ADD_ROOM_REQUEST_SQL);

            Map<String, String> requestParameters = requestContent.getParameters();
            User user = (User) requestContent.getSessionAttributes().get("user");

            RoomDao roomDao = new RoomDaoImpl();

            statement.setDate(1, Date.valueOf( requestParameters.get("checkInRequest")) );
            statement.setDate(2, Date.valueOf( requestParameters.get("checkOutRequest")) );
            statement.setInt(3, Integer.valueOf(requestParameters.get("roomSizeRequest")) );
            statement.setInt(4, user.getId());
            statement.setInt(5, roomDao.findRoomTypeIdByName(requestParameters.get("roomTypeRequest")));

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
            statement = connection.prepareStatement(FIND_ALL_REQUESTS_BY_USER_SQL);
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
}
