package com.example.productService.controller;

import com.example.productService.exception.ProductNotFound;
import com.example.productService.modelDTO.ProductDTO;
import com.example.productService.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product/action")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("test")
    public ResponseEntity<String>test(){
        return new ResponseEntity<>("productController", HttpStatus.OK);
    }

    @GetMapping("allProducts")
    public ResponseEntity<List<ProductDTO>>showAllProducts(){
        return new ResponseEntity<>(productService.showAllProducts(),HttpStatus.OK);
    }

    @GetMapping("product")
    public ResponseEntity<ProductDTO>findProductByName(@RequestParam String name) throws ProductNotFound {
        return new ResponseEntity<>(productService.findProductByName(name),HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<String>addProduct(@RequestBody @Valid ProductDTO productDTO){
        return new ResponseEntity<>(productService.addProduct(productDTO),HttpStatus.CREATED);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String>deleteProduct(@RequestParam String name) throws ProductNotFound {
        return new ResponseEntity<>(productService.deleteProduct(name),HttpStatus.OK);
    }
}
