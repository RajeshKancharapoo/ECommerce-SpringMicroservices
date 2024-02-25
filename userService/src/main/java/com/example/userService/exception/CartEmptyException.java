package com.example.userService.exception;

public class CartEmptyException extends Exception{
    public CartEmptyException(String msg){
        super(msg);
    }
}
