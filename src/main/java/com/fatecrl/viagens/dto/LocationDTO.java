package com.fatecrl.viagens.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDTO {

    private Long id;
    @NotBlank(message = "name required")
    private String name;
    @NotBlank(message = "nickname required")
    private String nickname;
    @NotBlank(message = "address required")
    private String address;
    @NotBlank(message = "city required")
    private String city;
    @NotBlank(message = "state required")
    private String state;
    @NotBlank(message = "country required")
    private String country;
}
