package com.example.userService.controller;


import com.ctc.wstx.shaded.msv_core.datatype.xsd.UnicodeUtil;
import com.example.userService.entity.*;
import com.example.userService.exception.*;
import com.example.userService.feign.AuthFeign;
import com.example.userService.modelDTO.*;
import com.example.userService.repository.UserRepo;
import com.example.userService.service.UserService;
import feign.FeignException;
import feign.ResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@RestController
@RequestMapping("user/action")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepo userRepo;
    @GetMapping("show")
    public String testMethod(@RequestHeader("currUser") String user){
        return "hello "+user;
    }
    @PostMapping("addUser")
    public ResponseEntity<String>addUserDetails(@RequestBody @Valid UserDTO userDTO,@RequestHeader("currUser") String username){
        return new ResponseEntity<>(userService.addUserDetails(userDTO,username), HttpStatus.CREATED);
    }
    @PutMapping("addCard")
    public ResponseEntity<String>addCard(@RequestBody @Valid CardDTO cardDTO,@RequestHeader("currUser")String username) throws InvalidActionException {
        userRepo.findByUsername(username).orElseThrow(()->new InvalidActionException("Provide your details in the profile section to perform this action"));
        return new ResponseEntity<>(userService.addCard(cardDTO,username),HttpStatus.CREATED);
    }
    @PutMapping ("addAddress")
    public ResponseEntity<String>addAddress(@RequestBody @Valid AddressDTO addressDTO,@RequestHeader("currUser") String username) throws InvalidActionException {
        userRepo.findByUsername(username).orElseThrow(()->new InvalidActionException("Provide your details in the profile section to perform this action"));
        return new ResponseEntity<>(userService.addAddress(addressDTO,username),HttpStatus.CREATED);
    }
    @GetMapping("allCards")
    public ResponseEntity<List<Card>>viewAllCards(@RequestHeader("currUser") String username)throws InvalidActionException{
        userRepo.findByUsername(username).orElseThrow(()->new InvalidActionException("Provide your details in the profile section to perform this action"));
        return new ResponseEntity<>(userService.viewAllCards(username),HttpStatus.OK);
    }
    @GetMapping("allAddress")
    public ResponseEntity<List<Address>>viewAllAddress(@RequestHeader("currUser") String username) throws InvalidActionException{
        userRepo.findByUsername(username).orElseThrow(()->new InvalidActionException("Provide your details in the profile section to perform this action"));
        return new ResponseEntity<>(userService.viewAllAddress(username),HttpStatus.OK);
    }

    @PostMapping("book")
    public ResponseEntity<String>bookOrder(@RequestParam String productName,@RequestHeader("currUser") String username) throws ProductNotFound,InvalidActionException {
        userRepo.findByUsername(username).orElseThrow(()->new InvalidActionException("Provide your details in the profile section to perform this action"));
        return new ResponseEntity<>(userService.bookOrder(productName,username),HttpStatus.CREATED);
    }

    @GetMapping("cancel")
    public ResponseEntity<String>cancelOrder(@RequestParam Long orderId){
        return new ResponseEntity<>(userService.cancelOrder(orderId),HttpStatus.OK);
    }

    @GetMapping("allOrders")
    public ResponseEntity<List<OrderDetails>>viewAllOrders(@RequestHeader("currUser") String username) throws OrdersEmptyException ,InvalidActionException{
        userRepo.findByUsername(username).orElseThrow(()->new InvalidActionException("Provide your details in the profile section to perform this action"));
        return new ResponseEntity<>(userService.viewAllOrders(username),HttpStatus.OK);
    }

    @GetMapping("order")
    public ResponseEntity<List<Order>>viewOrdersByStatus(@RequestParam String status, @RequestHeader("currUser") String username) throws OrdersEmptyException,InvalidActionException{
        userRepo.findByUsername(username).orElseThrow(()->new InvalidActionException("Provide your details in the profile section to perform this action"));
        return new ResponseEntity<>(userService.viewOrdersByStatus(status,username),HttpStatus.OK);
    }

    @GetMapping("add")
    public ResponseEntity<String>addToCart(@RequestParam String productName,@RequestHeader("currUser") String username)throws InvalidActionException{
        userRepo.findByUsername(username).orElseThrow(()->new InvalidActionException("Provide your details in the profile section to perform this action"));
        return new ResponseEntity<>(userService.addToCart(productName,username),HttpStatus.OK);
    }

    @GetMapping("delete")
    public ResponseEntity<String>deleteFromCart(@RequestParam Long cartId){
        return new ResponseEntity<>(userService.deleteFromCart(cartId),HttpStatus.OK);
    }

    @GetMapping("viewCart")
    public ResponseEntity<List<Cart>>viewCart(@RequestHeader("currUser") String username) throws CartEmptyException, InvalidActionException {
        userRepo.findByUsername(username).orElseThrow(()->new InvalidActionException("Provide your details in the profile section to perform this action"));
        return new ResponseEntity<>(userService.viewCart(username),HttpStatus.OK);
    }

    @GetMapping("allProducts")
    public ResponseEntity<List<ProductDTO>>viewProducts(){
        return new ResponseEntity<>(userService.viewProducts(),HttpStatus.OK);
    }

}

