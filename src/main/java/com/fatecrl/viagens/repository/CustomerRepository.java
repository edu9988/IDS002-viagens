package com.fatecrl.viagens.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fatecrl.viagens.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer , Long>{

}