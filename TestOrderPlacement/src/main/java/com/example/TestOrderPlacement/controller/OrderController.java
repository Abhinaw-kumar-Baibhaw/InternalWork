package com.example.TestOrderPlacement.controller;


import com.example.TestOrderPlacement.entity.CustomerOrder;
import com.example.TestOrderPlacement.service.OrderService;
import com.example.TestOrderPlacement.status.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CustomerOrder customerOrder) {
       return orderService.createOrder(customerOrder);
    }

    @GetMapping("/allOrder")
    public List<CustomerOrder> allOrder(){
        return orderService.getAllOrder();
    }

    @GetMapping("/getStatus/{orderId}")
    public CustomerOrder getOrderStatus(@PathVariable Long orderId) {
        return orderService.getOrderStatus(orderId);
    }

    @PutMapping("/updateStatus/{orderId}/status")
    public CustomerOrder updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    @GetMapping("/specificUserOrderDetails/{userId}")
    public ResponseEntity<CustomerOrder> detailsById(@PathVariable Long userId){
        return orderService.getOrderDetailsById(userId);
    }
}
