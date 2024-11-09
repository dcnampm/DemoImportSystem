package com.example.demoimportsystem.daos;

import com.example.demoimportsystem.models.OrderList;

import java.util.List;

public interface OrderListDAO {
    List<OrderList> getAllOrderLists();
    int countOrderListsByStatus(String status);
}
