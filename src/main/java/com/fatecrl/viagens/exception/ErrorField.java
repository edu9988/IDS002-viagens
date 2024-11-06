package com.fatecrl.viagens.exception;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class ErrorField {
    public String name;
    public String message;
}
