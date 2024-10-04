package com.example.demoimportsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "order_list")
public class OrderList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "list_code", nullable = false)
    private String listCode;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('Đã gửi', 'Đã hủy', 'Đang xử lý', 'Đã hoàn thành')")
    private Status status;

    public OrderList(Long id, String listCode, User createdBy, LocalDate createdDate, Status status) {
        this.id = id;
        this.listCode = listCode;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.status = status;
    }

    public OrderList() {}

    public enum Status {
        SENT("Đã gửi"),
        CANCELED("Đã hủy"),
        PROCESSING("Đang xử lý"),
        COMPLETED("Đã hoàn thành");

        private String vietnamese;

        Status(String vietnamese) {
            this.vietnamese = vietnamese;
        }

        public String getVietnamese() {
            return vietnamese;
        }

        public static Status fromVietnamese(String vietnamese) {
            for (Status status : Status.values()) {
                if (status.getVietnamese().equalsIgnoreCase(vietnamese)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("No enum constant with vietnamese value " + vietnamese);
        }
    }
}

