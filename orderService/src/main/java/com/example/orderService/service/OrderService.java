package com.example.orderService.service;

import com.example.orderService.entity.Order;
import com.example.orderService.entity.Status;
import com.example.orderService.exception.ProductNotFound;
import com.example.orderService.feign.ProductFeign;
import com.example.orderService.modelDTO.OrderDTO;
import com.example.orderService.modelDTO.ProductDTO;
import com.example.orderService.repository.OrderRepo;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final ProductFeign productFeign;

    public Long bookOrder(OrderDTO orderDTO) throws ProductNotFound{
        try {
            ResponseEntity<ProductDTO>productDTOResponseEntity=productFeign.findProductByName(orderDTO.getProductName());
            Order order=Order.builder()
                    .productName(orderDTO.getProductName())
                    .status(Status.BOOKED)
                    .build();
            orderRepo.save(order);
            return order.getOrderId();
        }catch (FeignException ex){
            throw new ProductNotFound("Product is not there in the inventory");
        }
    }

    public String cancelOrder(Long orderId){
        Optional<Order>order=orderRepo.findById(orderId);
        order.get().setStatus(Status.CANCELLED);
        orderRepo.save(order.get());
        return "Order cancelled successfully";
    }

    public List<Order> viewOrders(List<Long>orderIds){
        return orderIds.stream().map(val->orderRepo.findById(val).get()).collect(Collectors.toList());
    }

    public List<Order>viewOrderByStatus(List<Long> orderIds, String status){
       return orderIds.stream().map(val->orderRepo.findById(val).get()).filter(val->val.getStatus().toString().equals(status)).collect(Collectors.toList());
    }

}
