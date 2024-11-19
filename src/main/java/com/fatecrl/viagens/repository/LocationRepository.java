package com.fatecrl.viagens.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatecrl.viagens.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location , Long>{

    Page<Location> findByNameContaining(
        String name , Pageable pageable
    );
    Page<Location> findByNicknameContaining(
        String nickname , Pageable pageable
    );
    Page<Location> findByCityContaining(
        String city , Pageable pageable
    );

    Page<Location> findByNameContainingAndNicknameContaining(
        String name , String nickname , Pageable pageable
    );
    Page<Location> findByNameContainingAndCityContaining(
        String name , String city , Pageable pageable
    );
    Page<Location> findByNicknameContainingAndCityContaining(
        String nickname , String city , Pageable pageable
    );

    Page<Location> findByNameContainingAndNicknameContainingAndCityContaining(
        String name , String nickname , String city , Pageable pageable
    );
}
