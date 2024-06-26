package com.siemens.SpringBootAPI.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(nullable = false)
    private int orderQuantity;


    @Column(nullable = false)
    private Integer userId;


    @Column(nullable = false)
    private Integer productId;

}
