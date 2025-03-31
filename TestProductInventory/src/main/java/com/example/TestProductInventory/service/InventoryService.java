package com.example.TestProductInventory.service;

import com.example.TestProductInventory.entities.ProductInventory;
import java.util.List;
import java.util.Optional;


public interface InventoryService {

    ProductInventory create(ProductInventory productInventory);

    List<ProductInventory> getAll();

    ProductInventory update(Long porductId, ProductInventory productInventory);

    Optional<ProductInventory> getById(Long id);
}
