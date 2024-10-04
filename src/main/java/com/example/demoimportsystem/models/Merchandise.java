package com.example.demoimportsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "merchandise")
public class Merchandise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "merchandise_code", nullable = false)
    private String merchandiseCode;

    @Column(name = "merchandise_name", nullable = false)
    private String merchandiseName;

    private String type;
    private String unit;
}

