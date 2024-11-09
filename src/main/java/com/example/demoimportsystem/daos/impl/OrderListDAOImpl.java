package com.example.demoimportsystem.daos.impl;

import com.example.demoimportsystem.daos.OrderListDAO;
import com.example.demoimportsystem.models.OrderList;
import com.example.demoimportsystem.models.User;
import com.example.demoimportsystem.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderListDAOImpl implements OrderListDAO {

    Connection connection = DBConnection.getConnection();

    @Override
    public List<OrderList> getAllOrderLists() {
        List<OrderList> orderLists = new ArrayList<>();
        String query = "SELECT ol.id, ol.list_code, u.username, ol.created_date, ol.status "
                + "FROM order_list ol JOIN user u ON ol.created_by = u.id";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                OrderList orderList = new OrderList();
                orderList.setId(resultSet.getLong("id"));
                orderList.setListCode(resultSet.getString("list_code"));

                // Tạo đối tượng User và set username từ kết quả truy vấn
                User createdBy = new User();
                createdBy.setUsername(resultSet.getString("username"));
                orderList.setCreatedBy(createdBy);  // Gán đối tượng User vào createdBy

                orderList.setCreatedDate(resultSet.getDate("created_date").toLocalDate());
                orderList.setStatus(OrderList.Status.fromVietnamese(resultSet.getString("status")));
                orderLists.add(orderList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderLists;
    }

    @Override
    public int countOrderListsByStatus(String status) {
        String query = "SELECT COUNT(*) AS count FROM order_list WHERE status = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query))
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
