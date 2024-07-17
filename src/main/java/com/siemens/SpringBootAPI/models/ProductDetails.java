package com.siemens.SpringBootAPI.models;


import com.siemens.SpringBootAPI.FactoryDesign.Categories;
import lombok.Data;

@Data
public class ProductDetails {

    private Integer id;

    private String name;

    private double price;

    private int quantity;

    private String status;

    private String category;

}

