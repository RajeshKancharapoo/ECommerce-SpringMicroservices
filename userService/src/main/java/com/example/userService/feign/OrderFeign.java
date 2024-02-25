package com.example.userService.feign;

import com.example.userService.entity.Order;
import com.example.userService.modelDTO.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("ORDER-SERVICE")
public interface OrderFeign {

    @PostMapping("order/action/book")
    ResponseEntity<Long> bookOrder(@RequestBody OrderDTO orderDTO);

    @PutMapping("order/action/cancel")
    ResponseEntity<String>cancelOrder(@RequestParam Long orderId);

    @PostMapping("order/action/allOrders")
    ResponseEntity<List<Order>>viewOrders(@RequestBody List<Long>orderIds);

    @PostMapping("order/action/order")
    ResponseEntity<List<Order>>viewOrderByStatus(@RequestBody List<Long>orderIds,@RequestParam String status);
}
