package com.example.cartService.repository;

import com.example.cartService.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Long> {
    Optional<Cart>findByProductName(String name);
}
