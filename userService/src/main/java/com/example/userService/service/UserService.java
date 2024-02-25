package com.example.userService.service;


import com.example.userService.entity.*;
import com.example.userService.exception.CartEmptyException;
import com.example.userService.exception.OrdersEmptyException;
import com.example.userService.exception.ProductNotFound;
import com.example.userService.feign.AuthFeign;
import com.example.userService.feign.CartFeign;
import com.example.userService.feign.OrderFeign;
import com.example.userService.feign.ProductFeign;
import com.example.userService.modelDTO.*;
import com.example.userService.repository.CartIDRepo;
import com.example.userService.repository.OrderIDRepo;
import com.example.userService.repository.UserRepo;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {


    private final UserRepo userRepo;
    private final AuthFeign authFeign;
    private final OrderFeign orderFeign;
    private final CartFeign cartFeign;
    private final ProductFeign productFeign;
    private final CartIDRepo cartIDRepo;
    private final OrderIDRepo orderIDRepo;
    public String addUserDetails(UserDTO userDTO,String username){
        //fetch the username from auth service;
        log.info("current logged in user is "+username);

        List<Address>addressList=userDTO.getAddressDTOList().stream().map((addressDTO)->{
            return Address.builder()
                    .name(addressDTO.getName())
                    .phNo(addressDTO.getPhNo())
                    .state(addressDTO.getState())
                    .city(addressDTO.getCity())
                    .pinCode(addressDTO.getPinCode())
                    .addressType(addressDTO.getAddressType())
                    .build();
        }).toList();

        List<Card>cardList=userDTO.getCardDTOList().stream().map((cardDTO)-> Card.builder()
                .cardNumber(cardDTO.getCardNumber())
                .nameOnCard(cardDTO.getNameOnCard())
                .build()).toList();

        User user=User.builder()
                .username(username)
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .phNo(userDTO.getPhNo())
                .addressList(addressList)
                .cardList(cardList)
                .build();

        userRepo.save(user);
        return "User Details added successfully";

    }

    public String addCard(CardDTO cardDTO,String username){
        Optional<User>user=userRepo.findByUsername(username);
        List<Card>cardList=user.get().getCardList();
        cardList.add(Card.builder()
                        .cardNumber(cardDTO.getCardNumber())
                        .nameOnCard(cardDTO.getNameOnCard())
                .build());
        user.get().setCardList(cardList);
        userRepo.save(user.get());
        return "Card Details added successfully";
    }

    public String addAddress(AddressDTO addressDTO,String username){
        Optional<User>user=userRepo.findByUsername(username);
        List<Address>addressList=user.get().getAddressList();
        addressList.add(Address.builder()
                        .name(addressDTO.getName())
                        .phNo(addressDTO.getPhNo())
                        .pinCode(addressDTO.getPinCode())
                        .state(addressDTO.getState())
                        .city(addressDTO.getCity())
                        .addressType(addressDTO.getAddressType())
                .build());
        user.get().setAddressList(addressList);
        userRepo.save(user.get());
        return "Address Details added successfully";
    }

    public List<Card> viewAllCards(String username) {
        return userRepo.findByUsername(username).get().getCardList();
    }

    public List<Address>viewAllAddress(String username){
        return userRepo.findByUsername(username).get().getAddressList();
    }

    public String bookOrder(String productName,String username) throws ProductNotFound {
        try {
            ResponseEntity<Long>ans=orderFeign.bookOrder(OrderDTO.builder().productName(productName).build());
            orderIDRepo.save(OrderIDS.builder().orderId(ans.getBody()).username(username).build());
        }catch (FeignException ex){
            throw new ProductNotFound("Product is not there");
        }
        return "Order booked successfully";
    }

    public String cancelOrder(Long orderId) {
        return orderFeign.cancelOrder(orderId).getBody();
    }

    public List<OrderDetails> viewAllOrders(String username) throws OrdersEmptyException {
       List<Long>orderIds= orderIDRepo.findAll().stream().filter(orderIDs->orderIDs.getUsername().equals(username))
                .map(OrderIDS::getOrderId).toList();
       if(orderIds.isEmpty())throw new OrdersEmptyException("User does not have any orders");
       log.info("The orderIds of the "+username +"is "+orderIds);
       List<Order>orderList=orderFeign.viewOrders(orderIds).getBody();
        return orderList.stream().map(order->{
            ProductDTO productDTO=productFeign.findProductByName(order.getProductName()).getBody();
            return OrderDetails.builder()
                    .orderId(order.getOrderId())
                    .productName(order.getProductName())
                    .price(productDTO.getPrice())
                    .status(order.getStatus())
                    .description(productDTO.getDescription())
                    .build();
        }).collect(Collectors.toList());
    }

    public List<Order> viewOrdersByStatus(String status, String username) throws OrdersEmptyException{
        //get the list of orderIds for the current user from the orderIDS repo;
        //send the list to order service to get the order details by status;
        List<Long>orderIds=orderIDRepo.findAll().stream().filter(val->val.getUsername().equals(username))
                .map(OrderIDS::getOrderId).toList();
        if (orderIds.isEmpty())throw new OrdersEmptyException("User does not have any orders");
        log.info("The orderIds of the "+username +"is "+orderIds);
        return orderFeign.viewOrderByStatus(orderIds,status).getBody();
    }

    public String addToCart(String productName,String username) {
        Long cartId=cartFeign.addToCart(CartDTO.builder().productName(productName).build()).getBody();
        cartIDRepo.save(CartIDS.builder().CartId(cartId).username(username).build());
        return "Product added to the cart";
    }

    public String deleteFromCart(Long cartId) {
        cartIDRepo.deleteById(cartId);
        return cartFeign.deleteFromCart(cartId).getBody();
    }

    public List<Cart> viewCart(String username) throws CartEmptyException {
        List<Long>cartIds=cartIDRepo.findAll().stream().filter(val->val.getUsername().equals(username))
                .map(CartIDS::getCartId).toList();
        if (cartIds.isEmpty())throw new CartEmptyException("Cart is empty");
        return cartFeign.viewCart(cartIds).getBody();
    }

    public List<ProductDTO> viewProducts() {
        return productFeign.showAllProducts().getBody();
    }
}

