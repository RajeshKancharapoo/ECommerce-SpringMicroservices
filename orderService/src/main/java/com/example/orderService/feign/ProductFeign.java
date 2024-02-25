package com.example.orderService.feign;

import com.example.orderService.exception.ProductNotFound;
import com.example.orderService.modelDTO.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("PRODUCT-SERVICE")
public interface ProductFeign {

    @GetMapping("product/action/product")
    ResponseEntity<ProductDTO> findProductByName(@RequestParam String name) throws ProductNotFound;
}
