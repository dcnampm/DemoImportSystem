//package com.example.demoimportsystem.daos;
//
//import com.example.demoimportsystem.models.OrderListDetails;
//import com.example.demoimportsystem.utils.DBConnection;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class OrderListDetailsDAO {
//    public List<OrderListDetails> getDetailsByOrderListId(long orderListId) {
//        List<OrderListDetails> details = new ArrayList<>();
//        String query = "SELECT * FROM order_list_details WHERE order_list_id = ?";
//
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setLong(1, orderListId);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                OrderListDetails detail = new OrderListDetails();
//                detail.setId(resultSet.getLong("id"));
//                detail.setMerchandiseCode(resultSet.getString("merchandise_code"));
//                detail.setMerchandiseName(resultSet.getString("merchandise_name"));
//                detail.setQuantity(resultSet.getInt("quantity"));
//                detail.setUnit(resultSet.getString("unit"));
//                detail.setDesiredDate(resultSet.getDate("desired_date").toLocalDate());
//                details.add(detail);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return details;
//    }
//}
