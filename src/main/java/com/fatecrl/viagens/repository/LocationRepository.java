package com.fatecrl.viagens.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatecrl.viagens.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location , Long>{

    List<Location> findByNameContaining( String name );
    List<Location> findByNicknameContaining( String nickname );
    List<Location> findByCityContaining( String city );

    List<Location> findByNameContainingAndNicknameContaining( String name , String nickname );
    List<Location> findByNameContainingAndCityContaining( String name , String city );
    List<Location> findByNicknameContainingAndCityContaining( String nickname , String city );

    List<Location> findByNameContainingAndNicknameContainingAndCityContaining( String name , String nickname , String city );
}
