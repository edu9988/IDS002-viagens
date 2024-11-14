package com.fatecrl.viagens.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatecrl.viagens.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer , Long>{

    List<Customer> findByNameContaining( String name );
    List<Customer> findByBirthDate( LocalDate birthDate );
    List<Customer> findByNameContainingAndBirthDate( String name, LocalDate birthDate );

}