package com.example.userService.feign;

import com.example.userService.modelDTO.Cart;
import com.example.userService.modelDTO.CartDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("CART-SERVICE")
public interface CartFeign {

    @PostMapping("cart/action/addProduct")
    ResponseEntity<Long> addToCart(@RequestBody @Valid CartDTO cartDTO);

    @DeleteMapping("cart/action/deleteProduct")
    ResponseEntity<String>deleteFromCart(@RequestParam Long cartId);

    @PostMapping("cart/action/allProducts")
    ResponseEntity<List<Cart>>viewCart(List<Long>cartIds);
}
