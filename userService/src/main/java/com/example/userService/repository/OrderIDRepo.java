package com.example.userService.repository;

import com.example.userService.entity.OrderIDS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderIDRepo extends JpaRepository<OrderIDS,Long> {
}
