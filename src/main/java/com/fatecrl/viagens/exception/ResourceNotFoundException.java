package com.fatecrl.viagens.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus( HttpStatus.BAD_REQUEST )
public class ResourceNotFoundException extends RuntimeException{
    private String path;
    public ResourceNotFoundException(String path,String message){
        super(message);
        this.path = path;
    }
}
