package com.example.demoimportsystem.daos;

import com.example.demoimportsystem.models.OrderListDetails;

import java.util.List;
public interface MerchandiseDAO {
    List<OrderListDetails> getMerchandiseDetailsForOrderList(Long orderListId);
}
