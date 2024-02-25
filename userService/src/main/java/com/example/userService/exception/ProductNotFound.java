package com.example.userService.exception;

import com.example.userService.feign.ProductFeign;

public class ProductNotFound extends Exception{

    public ProductNotFound(String msg){
        super(msg);
    }
}
