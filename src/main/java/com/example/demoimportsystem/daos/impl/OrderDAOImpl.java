package com.example.demoimportsystem.daos.impl;

import com.example.demoimportsystem.daos.OrderDAO;
import com.example.demoimportsystem.models.Order;
import com.example.demoimportsystem.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    Connection conn = DBConnection.getConnection();

    @Override
    public List<Order> getOrdersForOrderList(Long orderListId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT o.order_code, o.site, o.delivery_date, o.status, "
                + "(SELECT GROUP_CONCAT(CONCAT(om.quantity, ' x ', m.merchandise_name) SEPARATOR '\n') "
                + "FROM order_merchandise om "
                + "JOIN merchandise m ON om.merchandise_id = m.id "
                + "WHERE om.order_id = o.id) AS order_details "
                + "FROM `order` o "
                + "WHERE o.order_list_id = ?"; // Lọc theo order_list_id

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            // Truyền orderListId vào câu truy vấn
            stmt.setLong(1, orderListId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setOrderCode(resultSet.getString("order_code"));
                    order.setSite(resultSet.getString("site"));
                    order.setDeliveryDate(resultSet.getDate("delivery_date").toLocalDate());
                    order.setStatus(Order.OrderStatus.fromVietnamese(resultSet.getString("status")));
                    order.setOrderDetails(resultSet.getString("order_details")); // Gán chi tiết đơn hàng
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public int countOrdersByStatus(String status) {
        String query = "SELECT COUNT(*) AS count FROM `order` WHERE status = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query))
        {
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
