package com.fatecrl.viagens.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatecrl.viagens.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer , Long>{

    Page<Customer> findByNameContaining( String name , Pageable pageable );
    Page<Customer> findByBirthDate( LocalDate birthDate , Pageable pageable );
    Page<Customer> findByNameContainingAndBirthDate(
        String name, LocalDate birthDate , Pageable pageable
    );

}