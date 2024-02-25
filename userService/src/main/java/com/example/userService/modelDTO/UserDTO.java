package com.example.userService.modelDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    @NotNull(message = "FirstName cannot be empty")
    @Pattern(regexp = "^[A-Za-z]{2,30}$",message = "Please provide valid firstName")
    private String firstName;

    @NotNull(message = "LastName cannot be empty")
    @Pattern(regexp = "^[A-Za-z]{2,30}$",message = "Please provide valid lastName")
    private String lastName;

    @NotNull(message = "Phone number cannot be empty")
    @Pattern(regexp = "^\\+91-[1-9]\\d{9}$",message = "Please provide valid phone number")
    private String phNo;

    @Valid
    private List<AddressDTO> addressDTOList;

    @Valid
    private List<CardDTO>cardDTOList;
}
