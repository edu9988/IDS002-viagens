package com.fatecrl.viagens.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fatecrl.viagens.model.TripType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TravelDTO {

    private Long id;
    @NotBlank(message = "orderNumber required")
    private String orderNumber;
    @NotNull(message = "amount required")
    private BigDecimal amount;
    @NotNull(message = "source required")
    private Long source;
    @NotNull(message = "destination required")
    private Long destination;
    @NotNull(message = "startDateTime required")
    private LocalDateTime startDateTime;
    @NotNull(message = "endDateTime required")
    private LocalDateTime endDateTime;
    @NotNull(message = "type required")
    private TripType type;
    @NotNull(message = "customer required")
    private Long customer;
}
