package com.siemens.SpringBootAPI.models;



import lombok.Data;

@Data
public class OrderRequest {

    private int orderQuantity;

    private Integer productId;

}
