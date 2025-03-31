package com.example.TestProductInventory.serviceImpl;

import com.example.TestProductInventory.entities.ProductInventory;
import com.example.TestProductInventory.repo.InventoryRepo;
import com.example.TestProductInventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class InventoryServiceImplementation implements InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Override
    public ProductInventory create(ProductInventory productInventory) {
        return inventoryRepo.save(productInventory);
    }

    @Override
    public List<ProductInventory> getAll() {
        return inventoryRepo.findAll();
    }

    @Override
    public ProductInventory update(Long productId,ProductInventory productInventory) {
        Optional<ProductInventory> inventory = inventoryRepo.findById(productId);
        if(inventory.isPresent()){
            ProductInventory productInventory1 = inventory.get();
            productInventory1.setQuantity(productInventory.getQuantity());
            inventoryRepo.save(productInventory1);
        }
        return productInventory;
    }

    @Override
    public Optional<ProductInventory> getById(Long id) {
       return inventoryRepo.findById(id);
    }
}
