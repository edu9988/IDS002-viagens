package com.fatecrl.viagens.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TravelDatesDTO {

    @NotNull(message = "startDateTime required")
    private LocalDateTime startDateTime;
    @NotNull(message = "endDateTime required")
    private LocalDateTime endDateTime;
}
