package com.fatecrl.viagens.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fatecrl.viagens.model.Travel;

@Repository
public interface TravelRepository extends JpaRepository<Travel , Long>{
    
}
