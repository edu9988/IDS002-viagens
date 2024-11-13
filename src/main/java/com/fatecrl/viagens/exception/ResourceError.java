package com.fatecrl.viagens.exception;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourceError {
    private int status;
    private LocalDateTime time;
    private String path;
    private String message;
}
