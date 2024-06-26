package com.siemens.SpringBootAPI.models;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class OrderDetails {

    private Integer orderId;

    private int orderQuantity;

    private Integer userId;

    private Integer productId;
}
