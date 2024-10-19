package com.fatecrl.viagens.service;

import java.util.List;

public interface IService<T> {

    List<T> findAll();
    T find( Long id );
    void create( T obj );
    Boolean update( T obj );
    Boolean delete( Long id );
    
}
