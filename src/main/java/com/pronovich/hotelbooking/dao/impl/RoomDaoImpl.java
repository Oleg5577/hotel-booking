package com.pronovich.hotelbooking.dao.impl;

import com.pronovich.hotelbooking.dao.RoomDao;
import com.pronovich.hotelbooking.dao.connectionpool.ConnectionPool;
import com.pronovich.hotelbooking.dao.connectionpool.ProxyConnection;
import com.pronovich.hotelbooking.dao.daoutils.ResultSetConverter;
import com.pronovich.hotelbooking.entity.Room;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.exception.DaoException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoImpl extends AbstractBaseDao implements RoomDao {

    private static final String FIND_ROOM_TYPE_ID_SQL = "SELECT room_type_id FROM room_type WHERE type_name = ?";

    private static final String FIND_ALL_ROOMS_ACCORDING_REQUEST_SQL = "SELECT room_id, number, size, price, type_name, request_id " +
            "FROM room " +
            "LEFT JOIN room_type ON room.fk_room_type_id = room_type.room_type_id " +
            "LEFT JOIN room_request ON room_type.room_type_id = room_request.fk_room_type_id " +
            "LEFT JOIN `order` ON `order`.fk_room_id = room.room_id " +
            "WHERE type_name = ? AND size = ? AND room_id NOT IN (" +
                "SELECT fk_room_id FROM `order` WHERE ? > `order`.check_in AND ? < `order`.check_out)" +
            "GROUP BY room_id ORDER BY room_id";

    private static final String FIND_ROOM_BY_ID_SQL = "SELECT room_id, number, size, price, type_name " +
            "FROM room LEFT JOIN room_type ON room.fk_room_type_id = room_type.room_type_id " +
            "WHERE room_id = ?";

    private static final java.lang.String FIND_ALL_ROOMS_WITH_UNIQUE_TYPE_AND_SIZE_SQL = "SELECT  " +
            "room_id, number, type_name, size, price " +
            "FROM room LEFT JOIN room_type ON room.fk_room_type_id = room_type.room_type_id " +
            "GROUP BY type_name, size ORDER BY price";

    @Override
    public Room findRoomById(Integer roomId) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Room room = null;
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(FIND_ROOM_BY_ID_SQL);
            statement.setInt(1, roomId);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                room = ResultSetConverter.createRoomEntity(resultSet);
            }
            return room;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }

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

    @Override
    public List<Room> findAllRoomsAccordingRequest(RoomRequest roomRequest) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Room> allRoomsByRequest = new ArrayList<>();
        try {
            connection = ConnectionPool.getPool().getConnection();

            statement = connection.prepareStatement(FIND_ALL_ROOMS_ACCORDING_REQUEST_SQL);
            statement.setString(1, roomRequest.getRoomType().name().toLowerCase() );
            statement.setInt(2,  roomRequest.getRoomSize());
            statement.setDate(3,  Date.valueOf(roomRequest.getCheckOutDate()));
            statement.setDate(4,  Date.valueOf(roomRequest.getCheckInDate()));

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Room room = ResultSetConverter.createRoomEntity(resultSet);
                allRoomsByRequest.add(room);
            }
            return allRoomsByRequest;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }

    @Override
    public List<Room> findRoomsWithUniqueType() throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Room> allUniqueTypeRooms = new ArrayList<>();
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(FIND_ALL_ROOMS_WITH_UNIQUE_TYPE_AND_SIZE_SQL);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Room room = ResultSetConverter.createRoomEntity(resultSet);
                allUniqueTypeRooms.add(room);
            }
            return allUniqueTypeRooms;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }
}
