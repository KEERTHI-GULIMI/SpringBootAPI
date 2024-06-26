package com.siemens.SpringBootAPI.models;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class ProductRequest {

    private String name;

    private double price;

    private int quantity;

    private String category;
}
