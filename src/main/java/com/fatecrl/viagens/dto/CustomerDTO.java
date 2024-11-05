package com.fatecrl.viagens.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fatecrl.viagens.model.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

    private Long id;
    @NotBlank(message = "name required")
    private String name;
    @NotBlank(message = "lastname required")
    private String lastname;
    @NotBlank(message = "address required")
    private String address;
    @NotBlank(message = "city required")
    private String city;
    @NotBlank(message = "state required")
    private String state;
    @NotBlank(message = "country required")
    private String country;
    @NotNull(message = "birthDate required")
    private LocalDate birthDate;
    @NotNull(message = "limitAmount required")
    private BigDecimal limitAmount;
    @NotNull(message = "status required")
    private Status status;
}
