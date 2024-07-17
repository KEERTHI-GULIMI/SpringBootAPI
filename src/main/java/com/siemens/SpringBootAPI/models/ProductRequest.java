package com.siemens.SpringBootAPI.models;

import com.siemens.SpringBootAPI.FactoryDesign.Categories;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class ProductRequest {

    private String name;

    private double price;

    private int quantity;

    private Categories category;
}
