package com.example.orderService.modelDTO;


import com.example.orderService.entity.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    @NotBlank(message = "Product name cannot be empty")
    private String productName;
}
