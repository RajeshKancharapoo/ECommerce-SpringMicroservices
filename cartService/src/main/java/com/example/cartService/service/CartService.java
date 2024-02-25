package com.example.cartService.service;


import com.example.cartService.entity.Cart;
import com.example.cartService.modelDTO.CartDTO;
import com.example.cartService.repository.CartRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo cartRepo;


    public Long addToCart(CartDTO cartDTO) {
        Cart cart=Cart.builder().productName(cartDTO.getProductName()).build();
        cartRepo.save(cart);
        return cart.getCartId();
    }

    public String deleteFromCart(Long cartId) {
        cartRepo.deleteById(cartId);
        return "Product deleted from cart";
    }

    public List<Cart> viewCart(List<Long>cartIds) {
        return cartIds.stream().map(val->cartRepo.findById(val).get()).collect(Collectors.toList());
    }
}
