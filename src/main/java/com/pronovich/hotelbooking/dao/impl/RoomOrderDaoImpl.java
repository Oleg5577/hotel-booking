package com.pronovich.hotelbooking.dao.impl;

import com.pronovich.hotelbooking.dao.RoomOrderDao;
import com.pronovich.hotelbooking.dao.RoomRequestDao;
import com.pronovich.hotelbooking.dao.UserDao;
import com.pronovich.hotelbooking.dao.connectionpool.ConnectionPool;
import com.pronovich.hotelbooking.dao.connectionpool.ProxyConnection;
import com.pronovich.hotelbooking.dao.daoutils.ResultSetConverter;
import com.pronovich.hotelbooking.entity.Room;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.entity.RequestStatus;
import com.pronovich.hotelbooking.exception.DaoException;
import com.pronovich.hotelbooking.utils.AmountUtils;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomOrderDaoImpl extends AbstractBaseDao implements RoomOrderDao {

    private static final String FIND_ALL_ORDERS_BY_USER_SQL = "SELECT `order_id`, `check_in`, `check_out`, `amount`, " +
            "`room_id`, `number`, `size`, `price`, " +
            "`room_type_id`, `type_name`, `is_paid`, `order_status` FROM `order` " +
            "LEFT JOIN `room` ON `order`.`fk_room_id` = `room`.`room_id` " +
            "LEFT JOIN `room_type` ON `room`.`fk_room_type_id` = `room_type`.`room_type_id` " +
            "WHERE `fk_user_id` = ? ORDER BY `order_status`, `check_in`";

    private static final String FIND_ALL_ORDERS_FOR_ALL_USERS_SQL = "SELECT `order_id`, `check_in`, `check_out`, `amount`, " +
            "`room_id`, `fk_user_id` , `number`, `size`, `price`, " +
            "`room_type_id`, `type_name`, `is_paid`, `order_status` FROM `order` " +
            "LEFT JOIN `room` ON `order`.`fk_room_id` = `room`.`room_id` " +
            "LEFT JOIN `room_type` ON `room`.`fk_room_type_id` = `room_type`.`room_type_id` ORDER BY `order_status`, `check_in`";

    private static final String FIND_ROOM_ORDER_BY_ID = "SELECT `order_id`, `check_in`, `check_out`, `amount`, " +
            "`room_id`, `fk_user_id` , `number`, `size`, `price`, " +
            "`room_type_id`, `type_name`, `is_paid`, `order_status` FROM `order` " +
            "LEFT JOIN `room` ON `order`.`fk_room_id` = `room`.`room_id` " +
            "LEFT JOIN `room_type` ON `room`.`fk_room_type_id` = `room_type`.`room_type_id` " +
            "WHERE `order_id` = ?";

    private static final String CREATE_ORDER_SQL = "INSERT INTO `order` " +
            "(check_in, check_out, amount, fk_room_id, fk_user_id) VALUES (?, ?, ?, ?, ?)";

    private static final String CHANGE_ORDER_STATUS_TO_CANCELED_SQL = "UPDATE `order` SET order_status = 'canceled' " +
            "WHERE order_id = ?";

    private static final String CHANGE_ORDER_STATUS_TO_CHECKED_IN_SQL = "UPDATE `order` SET order_status = 'checked_in' " +
            "WHERE order_id = ?";

    private static final String CHANGE_ORDER_STATUS_TO_CHECKED_OUT_SQL = "UPDATE `order` SET order_status = 'checked_out' " +
            "WHERE order_id = ?";

    private static final String CHANGE_ORDER_STATUS_TO_PAID_SQL = "UPDATE `order` SET is_paid = TRUE WHERE order_id = ?";

    @Override
    public List<RoomOrder> findAllOrdersByUser(User user) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<RoomOrder> roomOrders = new ArrayList<>();
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(FIND_ALL_ORDERS_BY_USER_SQL);
            statement.setInt(1, user.getId());

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roomOrders.add(ResultSetConverter.createRoomOrderEntity(resultSet, user));
            }
            return roomOrders;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }

    @Override
    public List<RoomOrder> findAllOrdersForAllUsers() throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<RoomOrder> roomOrders = new ArrayList<>();
        UserDao userDao = new UserDaoImpl();
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(FIND_ALL_ORDERS_FOR_ALL_USERS_SQL);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = userDao.findUserById( resultSet.getInt("fk_user_id") );
                roomOrders.add(ResultSetConverter.createRoomOrderEntity(resultSet, user));
            }
            return roomOrders;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }

    @Override
    public RoomOrder findOrderById(Integer orderId) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        RoomOrder roomOrder = null;
        UserDao userDao = new UserDaoImpl();
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(FIND_ROOM_ORDER_BY_ID);
            statement.setInt(1, orderId);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = userDao.findUserById( resultSet.getInt("fk_user_id") );
                roomOrder = ResultSetConverter.createRoomOrderEntity(resultSet, user);
            }
            return roomOrder;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }

    @Override
    public void createOrder(RoomRequest roomRequest, Room room) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        RoomRequestDao requestDao = new RoomRequestDaoImpl();
        try {
            connection = ConnectionPool.getPool().getConnection();

            statement = connection.prepareStatement(CREATE_ORDER_SQL);
            statement.setDate( 1, Date.valueOf(roomRequest.getCheckInDate()) );
            statement.setDate( 2, Date.valueOf(roomRequest.getCheckOutDate()) );
            statement.setBigDecimal(3,
                    AmountUtils.calculateAmount(room.getPrice(), roomRequest.getCheckInDate(), roomRequest.getCheckOutDate()));
            statement.setInt(4, room.getId());
            statement.setInt(5, roomRequest.getUser().getId());
            statement.executeUpdate();

            requestDao.changeStatusTo(roomRequest.getId(), RequestStatus.CONFIRMED);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement);
        }
    }

    @Override
    public void changeOrderStatusToCanceled(Integer orderId) throws DaoException {
        updateOrderById(orderId, CHANGE_ORDER_STATUS_TO_CANCELED_SQL);
    }

    @Override
    public void changeOrderStatusToCheckedIn(Integer orderId) throws DaoException {
        updateOrderById(orderId, CHANGE_ORDER_STATUS_TO_CHECKED_IN_SQL);
    }

    @Override
    public void changeOrderStatusToCheckedOut(Integer orderId) throws DaoException {
        updateOrderById(orderId, CHANGE_ORDER_STATUS_TO_CHECKED_OUT_SQL);
    }

    @Override
    public void changeOrderStatusToPaid(Integer orderId) throws DaoException {
        updateOrderById(orderId, CHANGE_ORDER_STATUS_TO_PAID_SQL);
    }

    private void updateOrderById(Integer orderId, String sql) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement);
        }
    }
}
