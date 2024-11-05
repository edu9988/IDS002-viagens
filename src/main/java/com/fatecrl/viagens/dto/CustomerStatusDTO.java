package com.fatecrl.viagens.dto;

import com.fatecrl.viagens.model.Status;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerStatusDTO {

    @NotNull(message = "status required")
    private Status status;
}