package com.example.TestOrderPlacement.serviceImp;

import com.example.TestOrderPlacement.entity.CustomerOrder;
import com.example.TestOrderPlacement.entity.ProductInventory;
import com.example.TestOrderPlacement.exception.NoEnoughInventoryException;
import com.example.TestOrderPlacement.repo.OrderRepo;
import com.example.TestOrderPlacement.service.OrderService;
import com.example.TestOrderPlacement.status.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InvoiceService invoiceService;

    @Override
    public ResponseEntity<String> createOrder(CustomerOrder customerOrder) {
        String url = "http://localhost:8084/inventory/getById/" + customerOrder.getProductId();
        ProductInventory product = restTemplate.getForObject(url, ProductInventory.class);
        if (product == null) {
            throw new RuntimeException("Product not found with id: " + customerOrder.getProductId());
        }
        Long quantity = product.getQuantity();
        Long requestedQuantity = customerOrder.getQuantity();
        if (quantity <= 0) {
            throw new NoEnoughInventoryException("Out of stock for productId " + customerOrder.getProductId());
        }
        if (requestedQuantity > quantity) {
            throw new NoEnoughInventoryException("Not enough inventory for productId " + customerOrder.getProductId());
        }
        Long actualQuantity = quantity - requestedQuantity;
        product.setQuantity(actualQuantity);
        restTemplate.put("http://localhost:8084/inventory/update/" + customerOrder.getProductId(),
                new ProductInventory(product.getProductId(), actualQuantity));
        customerOrder.setOrderStatus(String.valueOf(OrderStatus.PENDING));
        CustomerOrder savedOrder = orderRepo.save(customerOrder);
        try{
            invoiceService.generateInvoice(savedOrder);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("Order placed successfully: " + savedOrder);
        return new ResponseEntity<>("Order placed successfully!", HttpStatus.CREATED);
    }

    @Override
    public List<CustomerOrder> getAllOrder() {
        return orderRepo.findAll();
    }

    @Override
    public CustomerOrder updateOrderStatus(Long orderId, OrderStatus status) {
        Optional<CustomerOrder> orderOptional = orderRepo.findById(orderId);
        if (orderOptional.isPresent()) {
            CustomerOrder order = orderOptional.get();
            order.setOrderStatus(String.valueOf(status));
            return orderRepo.save(order);
        } else {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
    }

    @Override
    public CustomerOrder getOrderStatus(Long orderId) {
        Optional<CustomerOrder> orderOptional = orderRepo.findById(orderId);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        } else {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
    }

    @Override
    public ResponseEntity<CustomerOrder> getOrderDetailsById(Long id) {
        Optional<CustomerOrder> byId = orderRepo.findById(id);
        if(byId.isPresent()){
            CustomerOrder customerOrder = byId.get();
            return new ResponseEntity<>(customerOrder,HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
