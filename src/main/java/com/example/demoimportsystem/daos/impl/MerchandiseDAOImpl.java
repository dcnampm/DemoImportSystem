package com.example.demoimportsystem.daos.impl;

import com.example.demoimportsystem.daos.MerchandiseDAO;
import com.example.demoimportsystem.models.OrderListDetails;
import com.example.demoimportsystem.utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MerchandiseDAOImpl implements MerchandiseDAO {
    @Override
    public List<OrderListDetails> getMerchandiseDetailsForOrderList(Long orderListId) {
        List<OrderListDetails> merchandiseDetails = new ArrayList<>();
        String query = "SELECT m.merchandise_code, m.merchandise_name, d.quantity, m.unit, d.desired_date "
                + "FROM merchandise m "
                + "JOIN order_list_details d ON m.id = d.merchandise_id "
                + "WHERE d.order_list_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, orderListId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    OrderListDetails orderListDetails = new OrderListDetails();
                    orderListDetails.setMerchandiseCode(rs.getString("merchandise_code"));
                    orderListDetails.setMerchandiseName(rs.getString("merchandise_name"));
                    orderListDetails.setQuantity(rs.getInt("quantity"));
                    orderListDetails.setUnit(rs.getString("unit"));
                    orderListDetails.setDesiredDate(LocalDate.parse(rs.getDate("desired_date").toString()));
                    merchandiseDetails.add(orderListDetails);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return merchandiseDetails;
    }
}

