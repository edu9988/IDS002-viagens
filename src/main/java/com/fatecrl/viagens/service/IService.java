package com.fatecrl.viagens.service;

import java.util.List;
import java.util.Optional;

public interface IService<T> {

    List<T> findAll();
    Optional<T> find( Long id );
    void create( T obj );
    Boolean update( T obj );
    Boolean delete( Long id );
    
}
