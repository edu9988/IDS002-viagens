package com.fatecrl.viagens.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatecrl.viagens.model.Customer;
import com.fatecrl.viagens.model.Location;
import com.fatecrl.viagens.model.Travel;

@Repository
public interface TravelRepository extends JpaRepository<Travel , Long>{
    List<Travel> findByCustomer( Customer customer );
    public Boolean existsBySource( Location source );
    public Boolean existsByDestination( Location destination );
    public Boolean existsByCustomer( Customer customer );
}
