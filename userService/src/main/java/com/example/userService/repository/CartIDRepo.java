package com.example.userService.repository;

import com.example.userService.entity.CartIDS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartIDRepo extends JpaRepository<CartIDS,Long> {
}
