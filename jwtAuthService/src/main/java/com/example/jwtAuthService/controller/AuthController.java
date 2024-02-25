package com.example.jwtAuthService.controller;

import com.example.jwtAuthService.entity.AuthResponse;
import com.example.jwtAuthService.entity.User;
import com.example.jwtAuthService.jwtConfig.JwtService;
import com.example.jwtAuthService.modelDTO.UserDTO;
import com.example.jwtAuthService.repository.UserRepo;
import com.example.jwtAuthService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("jwt/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    @PostMapping("register")
    public ResponseEntity<AuthResponse>signup(@RequestBody UserDTO userDTO){
        User user=User
                .builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();

        userRepo.save(user);
        String jwt=jwtService.generateToken(user);
        return new ResponseEntity<>(AuthResponse.builder().jwt(jwt).build(), HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDTO userDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword())
        );
        User user= (User) userService.loadUserByUsername(userDTO.getUsername());
        String jwt=jwtService.generateToken(user);
        return new ResponseEntity<>(AuthResponse.builder().jwt(jwt).build(),HttpStatus.OK);
    }

    @GetMapping("validateTkn")
    public User validateToken(@RequestParam String jwt){
        return jwtService.validateToken(jwt);
    }



}
