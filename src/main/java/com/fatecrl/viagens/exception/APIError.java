package com.fatecrl.viagens.exception;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class APIError {
    private int status;
    private LocalDateTime time;
    private String path;
    private String message;
    private List<ErrorField> fields;
}
