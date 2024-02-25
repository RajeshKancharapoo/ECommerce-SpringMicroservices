package com.example.userService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartIDS {
    @Id
    private Long CartId;
    private String username;
}
