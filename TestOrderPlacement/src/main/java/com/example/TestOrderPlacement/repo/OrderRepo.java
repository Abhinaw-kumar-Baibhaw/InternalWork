package com.example.TestOrderPlacement.repo;

import com.example.TestOrderPlacement.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepo extends JpaRepository<CustomerOrder,Long> {
    Optional<CustomerOrder> findByUserId(Long userId);
}
