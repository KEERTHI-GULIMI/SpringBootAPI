package com.siemens.SpringBootAPI.models;

import lombok.Data;

@Data
public class UpdateProductRequest {

    private Long id;

    private String name;

    private double price;

    private int quantity;

    private String category;
}
