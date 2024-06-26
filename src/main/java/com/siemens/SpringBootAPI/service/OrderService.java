package com.siemens.SpringBootAPI.service;


import com.siemens.SpringBootAPI.entity.Order;
import com.siemens.SpringBootAPI.entity.Product;
import com.siemens.SpringBootAPI.entity.User;
import com.siemens.SpringBootAPI.models.*;
import com.siemens.SpringBootAPI.repository.OrderRepository;
import com.siemens.SpringBootAPI.repository.ProductRepository;
import com.siemens.SpringBootAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    public OrderDetails saveOrder(Integer userId, OrderRequest orderRequest) throws myException {


        Optional<Product> existingProduct = productRepository.findById(orderRequest.getProductId());
        Optional<User> existingUser = userRepository.findById(userId);
        Order order = new Order();

        if (existingProduct.isPresent() && existingUser.isPresent()) {
            Product product = existingProduct.get();
            if (orderRequest.getOrderQuantity() <= product.getQuantity()) {
                int quantity = (product.getQuantity() - orderRequest.getOrderQuantity());
                product.setQuantity(quantity);
                if(product.getQuantity()==0){
                    product.setStatus("Not Available");
                }
                order.setUserId(userId);
                order.setProductId(orderRequest.getProductId());
                order.setOrderQuantity(orderRequest.getOrderQuantity());
                Order updatedOrder = orderRepository.save(order);
                return convertOrderToOrderDetails(updatedOrder);
            } else {
                throw new myException(" OrderQuantity is greater than Product Quantity ");
            }

        } else {
            throw new myException(" product is not present ");
        }

    }

    private OrderDetails convertOrderToOrderDetails(Order order) {


        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(order.getOrderId());
        orderDetails.setUserId(order.getUserId());
        orderDetails.setOrderQuantity(order.getOrderQuantity());
        orderDetails.setProductId(order.getProductId());

        return orderDetails;
    }


    public List<OrderDetails> getAllOrders() {

        List<Order> allOrders = orderRepository.findAll();
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        allOrders.forEach(item -> orderDetailsList.add(convertOrderToOrderDetails(item)));
        return orderDetailsList;
    }


    public OrderDetails getOrderById(Integer userId, Integer orderId) throws myException {

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Optional<User> existingUser = userRepository.findById(userId);

        if (existingUser.isPresent()) {
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                if (order.getUserId() == userId) {
                    return convertOrderToOrderDetails(order);
                } else {
                    throw new myException("invalid fetch,given user has not ordered the given orderId");
                }
            } else {
                throw new myException("invalid order id " + orderId);
            }
        } else {
            throw new myException("invalid user id " + userId);
        }
    }

    public OrderDetails updateOrder(Integer userId, Integer orderId, OrderRequest orderRequest) throws myException {

        Optional<User> existingUser = userRepository.findById(userId);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (existingUser.isPresent()) {
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                if (order.getUserId() == userId) {
                    order.setOrderQuantity(orderRequest.getOrderQuantity());
                    order.setProductId(orderRequest.getProductId());
                    Order updateorder = orderRepository.save(order);
                    return convertOrderToOrderDetails(updateorder);
                } else {
                    throw new myException(" user  is not ordered the given orderId ");
                }
            } else {
                throw new myException(" orderId  is not present ");
            }
        } else {
            throw new myException(" user is not present ");
        }
    }

    public List<OrderDetails> getAllOrdersByUserId(Integer userId, Order order) throws myException {

        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();

            List<Order> allOrders = orderRepository.findAll();
            List<OrderDetails> orderDetailsList = new ArrayList<>();

            allOrders.forEach(item -> {
                if (item.getUserId() == userId) {
                    orderDetailsList.add(convertOrderToOrderDetails(item));
                }
            });

            return orderDetailsList;

        }else{
            throw new myException(" user is not present ");
        }
    }

}



