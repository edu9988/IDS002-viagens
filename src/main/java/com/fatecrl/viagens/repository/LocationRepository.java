package com.fatecrl.viagens.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fatecrl.viagens.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location , Long>{

}
