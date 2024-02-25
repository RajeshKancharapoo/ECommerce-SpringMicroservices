package com.example.productService.service;


import com.example.productService.entity.Product;
import com.example.productService.exception.ProductNotFound;
import com.example.productService.modelDTO.ProductDTO;
import com.example.productService.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

   public List<ProductDTO>showAllProducts(){
       return productRepo.findAll().stream().map((product)-> ProductDTO.
               builder()
               .name(product.getName())
               .price(product.getPrice())
               .description(product.getDescription())
               .build()).collect(Collectors.toList());
   }

   public ProductDTO findProductByName(String name) throws ProductNotFound {
       Optional<Product>product=productRepo.findByName(name);
       if(product.isEmpty())throw new ProductNotFound("Product is not there");
       return ProductDTO.builder()
               .name(product.get().getName())
               .price(product.get().getPrice())
               .description(product.get().getDescription())
               .build();
   }

   public String addProduct(ProductDTO productDTO){
       productRepo.save(Product.builder()
                       .name(productDTO.getName())
                       .price(productDTO.getPrice())
                       .description(productDTO.getDescription())
               .build());
       return "Product added successfully";
   }

   public String deleteProduct(String name) throws ProductNotFound {
       Optional<Product> product=productRepo.findByName(name);
       if (product.isEmpty())throw new ProductNotFound("Product is not there");
       else{
           productRepo.delete(product.get());
       }
       return "Product deleted successfully";
   }
}
