package com.example.TestProduct.serviceImp;

import com.example.TestProduct.model.Product;
import com.example.TestProduct.model.ProductSalesDto;
import com.example.TestProduct.repo.ProductRepo;
import com.example.TestProduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepo productRepository;

    @Override
    public ResponseEntity<Product> createProduct(Product product) {
        try {
            Product savedProduct = productRepository.save(product);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
//    @CachePut(value = "orders", key = "#order.id")
    public ResponseEntity<Product> updateProduct(Product product) {
        Optional<Product> existingProductOptional = productRepository.findById(product.getId());
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());
            Product updatedProduct = productRepository.save(existingProduct);

            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @Override
//    @CacheEvict(value = "orders", key = "#id")
    public ResponseEntity<String> deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<ProductSalesDto>> findTopMostSoldProducts() {
        List<Object[]> result = productRepository.findTop3MostSoldProducts();
        List<ProductSalesDto> topProducts = new ArrayList<>();
        for (Object[] row : result) {
            String productName = (String) row[0];
            long total_item = (Long) row[1];
            topProducts.add(new ProductSalesDto(productName, total_item));
        }
        return new ResponseEntity<>(topProducts,HttpStatus.OK);
    }
}
