package com.example.jwtAuthService.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jwt/demo")
public class DemoController {


    @GetMapping("test")
    public ResponseEntity<String>test(){
        return new ResponseEntity<>("this is secured endpoint", HttpStatus.OK);
    }

}
