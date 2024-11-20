package com.fatecrl.viagens.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IService<T> {

    Page<T> findAll( Pageable p );
    Optional<T> find( Long id );
    void create( T obj );
    void update( Long id , T obj );
    void delete( Long id );
    
}
