package com.siemens.SpringBootAPI.models;


import lombok.Data;

@Data
public class ProductDetails {

    private Long id;

    private String name;

    private double price;

    private int quantity;

    private String status;

}

