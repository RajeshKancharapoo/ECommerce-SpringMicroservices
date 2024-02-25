package com.example.orderService.controller;


import com.example.orderService.entity.Order;
import com.example.orderService.exception.ProductNotFound;
import com.example.orderService.modelDTO.OrderDTO;
import com.example.orderService.service.OrderService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order/action")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @GetMapping("test")
    public ResponseEntity<String>test(){
        return new ResponseEntity<>("orderController", HttpStatus.OK);
    }

    @PostMapping("book")
    public ResponseEntity<Long>bookOrder(@RequestBody OrderDTO orderDTO) throws ProductNotFound {
        return new ResponseEntity<>(orderService.bookOrder(orderDTO),HttpStatus.CREATED);
    }

    @PutMapping("cancel")
    public ResponseEntity<String>cancelOrder(@RequestParam Long orderId){
        return new ResponseEntity<>(orderService.cancelOrder(orderId),HttpStatus.OK);
    }

    @PostMapping("allOrders")
    public ResponseEntity<List<Order>>viewOrders(@RequestBody List<Long>orderIds){
        return new ResponseEntity<>(orderService.viewOrders(orderIds),HttpStatus.OK);
    }

    @PostMapping("order")
    public ResponseEntity<List<Order>>viewOrderByStatus(@RequestBody List<Long>orderIds,@RequestParam String status){
        return new ResponseEntity<>(orderService.viewOrderByStatus(orderIds,status),HttpStatus.OK);
    }
}
