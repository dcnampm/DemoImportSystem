package com.example.demoimportsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "order") // Tên bảng trong cơ sở dữ liệu
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_list_id", nullable = false)
    private Long orderListId;

    @Column(name = "order_code", length = 50)
    private String orderCode;

    @Column(name = "site")
    private String site;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    private String orderDetails;

    public Order() {}


    // Enum cho trạng thái đơn hàng
    @Getter
    public enum OrderStatus {
        CREATED("Đã tạo"),
        DELIVERING("Đang vận chuyển"),
        DELIVERED("Đã giao"),
        CANCELED("Đã hủy");

        private String vietnamese;

        OrderStatus(String vietnamese) {
            this.vietnamese = vietnamese;
        }

        public static OrderStatus fromVietnamese(String vietnamese) {
            for (OrderStatus status : OrderStatus.values()) {
                if (status.getVietnamese().equalsIgnoreCase(vietnamese)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("No enum constant with vietnamese value " + vietnamese);
        }
    }
}
