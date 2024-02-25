package com.example.userService.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@FeignClient("JWTAUTH-SERVICE")
public interface AuthFeign {

}
