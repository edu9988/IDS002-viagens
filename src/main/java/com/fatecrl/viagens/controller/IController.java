package com.fatecrl.viagens.controller;

import org.springframework.http.ResponseEntity;

public interface IController<T> {

    //getAll depends on different parameters
    ResponseEntity<T> get( Long id );
    ResponseEntity<T> create( T obj );
    ResponseEntity<T> update( Long id , T obj );
    ResponseEntity<Void> delete( Long id );

}
