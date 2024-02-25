package com.example.userService.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String msg){
        super(msg);
    }
}
