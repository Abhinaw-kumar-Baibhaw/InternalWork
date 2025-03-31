package com.example.TestOrderPlacement.service;


import com.example.TestOrderPlacement.entity.CustomerOrder;
import com.example.TestOrderPlacement.status.OrderStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {

     ResponseEntity<String> createOrder(CustomerOrder customerOrder);

     List<CustomerOrder> getAllOrder();

     CustomerOrder updateOrderStatus(Long orderId, OrderStatus status);

     CustomerOrder getOrderStatus(Long orderId);

     ResponseEntity<CustomerOrder> getOrderDetailsById(Long id);


}


