package com.example.TestProduct.controller;

import com.example.TestProduct.model.Product;
import com.example.TestProduct.model.ProductSalesDto;
import com.example.TestProduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }


    @DeleteMapping("delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/top-sold")
    public ResponseEntity<List<ProductSalesDto>> getTop3MostSoldProducts() {
        return productService.findTopMostSoldProducts();
    }
}

