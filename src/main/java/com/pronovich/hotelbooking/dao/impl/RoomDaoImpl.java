package com.pronovich.hotelbooking.dao.impl;

import com.pronovich.hotelbooking.dao.RoomDao;
import com.pronovich.hotelbooking.dao.connectionpool.ConnectionPool;
import com.pronovich.hotelbooking.dao.connectionpool.ProxyConnection;
import com.pronovich.hotelbooking.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDaoImpl extends AbstractBaseDao implements RoomDao {

    private static final String FIND_ROOM_TYPE_ID_SQL = "SELECT room_type_id FROM room_type WHERE type_name = ?";

    @Override
    public Integer findRoomTypeIdByName(String roomType) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer roomTypeId = null;
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(FIND_ROOM_TYPE_ID_SQL);
            statement.setString(1, roomType);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                roomTypeId = resultSet.getInt("room_type_id");
            }
            return roomTypeId;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }
}
