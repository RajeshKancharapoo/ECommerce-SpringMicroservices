package com.example.productService.repository;

import com.example.productService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product,Long> {

    Optional<Product>findByName(String name);
}
