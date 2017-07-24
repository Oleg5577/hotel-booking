package com.pronovich.hotelbooking.dao.impl;

import com.pronovich.hotelbooking.dao.OrderDao;
import com.pronovich.hotelbooking.dao.connectionpool.ConnectionPool;
import com.pronovich.hotelbooking.dao.connectionpool.ProxyConnection;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl extends AbstractBaseDao implements OrderDao {

    private static final String FIND_ALL_ORDERS_BY_USER_ID_SQL = "SELECT order_id, check_in, check_out, amount, fk_room_id " +
            "FROM order WHERE fk_user_id = ?";

    @Override
    public List<RoomOrder> findAllOrdersByUserId(Integer userId) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<RoomOrder> roomOrders = new ArrayList<>();
        try {
            connection = ConnectionPool.getPool().getConnection();
            statement = connection.prepareStatement(FIND_ALL_ORDERS_BY_USER_ID_SQL);
            statement.setString(1, userId.toString());

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                //TODO
            }
            return roomOrders;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeDbResources(connection, statement, resultSet);
        }
    }
}
