package com.siemens.SpringBootAPI.models;

import lombok.Data;

@Data
public class ProductRequest {

    private String name;

    private double price;

    private int quantity;
}
