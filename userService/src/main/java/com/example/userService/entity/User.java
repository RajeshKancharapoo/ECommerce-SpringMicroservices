package com.example.userService.entity;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "USER_TBL")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String phNo;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_user_id")
    private List<Address> addressList;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
    private List<Card>cardList;
}
