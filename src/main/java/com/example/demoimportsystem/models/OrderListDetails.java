package com.example.demoimportsystem.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class OrderListDetails {
    private long id;
    private long orderListId;
    private long merchandiseId;
    private int quantity;
    private LocalDate desiredDate;

    // Các thuộc tính bổ sung để hiển thị trong TableView
    private String merchandiseCode;
    private String merchandiseName;
    private String unit;

    public OrderListDetails() {
    }

    public OrderListDetails(long id, long orderListId, long merchandiseId, int quantity, LocalDate desiredDate) {
        this.id = id;
        this.orderListId = orderListId;
        this.merchandiseId = merchandiseId;
        this.quantity = quantity;
        this.desiredDate = desiredDate;
    }

}
