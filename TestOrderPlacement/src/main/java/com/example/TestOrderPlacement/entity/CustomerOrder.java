package com.example.TestOrderPlacement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "customerOrder")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "orderNumber is required")
    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "product_id")
    private Long productId;

    @Transient
    private List<ProductInventory> productList = new ArrayList<>();

}
