package com.siemens.SpringBootAPI.controller;


import com.siemens.SpringBootAPI.entity.Order;
import com.siemens.SpringBootAPI.models.*;
import com.siemens.SpringBootAPI.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<OrderDetails> saveProduct(@PathVariable Integer userId, @RequestBody OrderRequest orderRequest) throws myException {
        OrderDetails newOrder = orderService.saveOrder(userId, orderRequest);
        return ResponseEntity.ok(newOrder);
    }

    @GetMapping("/users/orders")
    public List<OrderDetails> getAllOrders() {
        return orderService.getAllOrders();
    }


    @GetMapping("/users/{userId}/orders/{orderId}")
    public ResponseEntity<Object> getOrderById(@PathVariable Integer userId, @PathVariable Integer orderId) throws myException {

        OrderDetails order = orderService.getOrderById(userId, orderId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/users/{userId}/orders/{orderId}")
    public ResponseEntity<Object> updateOrder(@PathVariable Integer userId, @PathVariable Integer orderId, @RequestBody OrderRequest orderRequest) throws myException {

        OrderDetails updateOrder = orderService.updateOrder(userId, orderId, orderRequest);
        return ResponseEntity.ok(updateOrder);

    }

    @GetMapping("/users/{userId}/orders")
    public List<OrderDetails> getAllOrdersByUserId(@PathVariable Integer userId, Order order) throws myException {

        return orderService.getAllOrdersByUserId(userId, order);
    }

}




