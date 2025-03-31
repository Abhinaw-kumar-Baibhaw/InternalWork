package com.example.TestProduct.service;

import com.example.TestProduct.model.Product;
import com.example.TestProduct.model.ProductSalesDto;
import org.springframework.http.ResponseEntity;
import java.util.List;


public interface ProductService {

    ResponseEntity<Product> createProduct(Product product);

    ResponseEntity<Product> updateProduct(Product product);

    ResponseEntity<String> deleteProduct(Long productId);

    ResponseEntity<List<ProductSalesDto>> findTopMostSoldProducts();

}
