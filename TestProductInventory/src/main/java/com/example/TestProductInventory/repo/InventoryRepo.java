package com.example.TestProductInventory.repo;

import com.example.TestProductInventory.entities.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InventoryRepo extends JpaRepository<ProductInventory,Long> {
}
