package com.example.TestProduct.repo;

import com.example.TestProduct.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    @Query("SELECT p.name, COUNT(p.name) AS Total_sale " +
            "FROM Product p " +
            "GROUP BY p.name " +
            "ORDER BY Total_sale DESC LIMIT 3")
    List<Object[]> findTop3MostSoldProducts();
}
