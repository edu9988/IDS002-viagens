package com.fatecrl.viagens.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.annotation.Nullable;

@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler{

    @Nullable
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ){
        APIError err = new APIError();
        
        err.setStatus( status.value() );
        err.setTime( LocalDateTime.now() );
        err.setPath( ((ServletWebRequest) request).getRequest().getRequestURI().toString() );
        err.setMessage("One or more fields invalid");
        err.setFields( retrieveErrorFields(ex) );
        
        return super.handleExceptionInternal(ex, err, headers, status, request);
    }

    private List<ErrorField> retrieveErrorFields(
        MethodArgumentNotValidException ex
    ){
        List<ErrorField> ls = new ArrayList<ErrorField>();
        ex.getBindingResult().getAllErrors().forEach(
            e -> ls.add( new ErrorField(
                ((FieldError) e).getField(),
                e.getDefaultMessage()
            ))
        );
        return ls;
    }
}