package com.fatecrl.viagens.exception;

import lombok.Getter;

//@ResponseStatus( HttpStatus.UNPROCESSABLE_ENTITY )
@Getter
public class InvalidArgumentException extends RuntimeException{
    private String path;
    public InvalidArgumentException (String path,String message) {
        super(message);
        this.path = path;
    }
}
