package com.example.demoimportsystem.daos;

import com.example.demoimportsystem.models.Order;

import java.util.List;

public interface OrderDAO {
    List<Order> getOrdersForOrderList(Long orderListId);
    int countOrdersByStatus(String status);
}
