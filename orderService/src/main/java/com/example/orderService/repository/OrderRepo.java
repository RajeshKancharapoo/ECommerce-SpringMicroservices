package com.example.orderService.repository;


import com.example.orderService.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order,Long> {
    Optional<Order>findByProductName(String productName);
}
