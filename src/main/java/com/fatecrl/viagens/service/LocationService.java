package com.fatecrl.viagens.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatecrl.viagens.model.Location;
import com.fatecrl.viagens.repository.LocationRepository;
import com.fatecrl.viagens.repository.TravelRepository;

@Service
public class LocationService implements IService<Location>{

    @Autowired
    private LocationRepository repo;

    @Autowired
    private TravelRepository travelRepo;

    public LocationService(){
    }

    //@Override
    public Page<Location> findAll( Pageable pageable ){
        return repo.findAll( pageable );
    }

   @Override
    public Optional<Location> find( Long id ){
        return repo.findById(id);
    }

    public Page<Location> findByParams(
        String name ,
        String nickname,
        String city,
        Pageable pageable
    ){
        Page<Location> locations;
        if( name == null || name.isEmpty() ){
            if( nickname == null || nickname.isEmpty() )
                locations = repo.findByCityContaining( city , pageable );
            else if( city == null || city.isEmpty() )
                locations = repo.findByNicknameContaining( nickname , pageable );
            else /* nickname and city != null */
                locations = repo.findByNicknameContainingAndCityContaining( nickname , city , pageable );
        }
        else if( nickname == null || nickname.isEmpty() ){
            if( name == null || name.isEmpty() )
                locations = repo.findByCityContaining( city , pageable );
            else if( city == null || city.isEmpty() )
                locations = repo.findByNameContaining( name , pageable );
            else /* name and city != null */
                locations = repo.findByNameContainingAndCityContaining( name , city , pageable );
        }
        else if( city == null || city.isEmpty() ){
            if( name == null || name.isEmpty() )
                locations = repo.findByNicknameContaining( nickname , pageable );
            else if( nickname == null || nickname.isEmpty() )
                locations = repo.findByNameContaining( name , pageable );
            else /* name and nickname != null */
                locations = repo.findByNameContainingAndNicknameContaining( name , nickname , pageable );
        }
        else /* none is null */
            locations = repo.findByNameContainingAndNicknameContainingAndCityContaining( name , nickname , city , pageable );
        return locations;
    }

    @Override
    public void create( Location location ){
        repo.save(location);
    }

    public Boolean locationExists( Long id ){
        return repo.existsById(id);
    }
    
    @Override
    public void update( Long id , Location location ){
        location.setId(id);
        repo.save(location);
    }

    public Boolean referencedBySomeTravel( Long id ){
        Location target = repo.findById(id).orElse(null);
        return travelRepo.existsBySource( target )
            || travelRepo.existsByDestination( target );
    }

    @Override
    public void delete( Long id ){
        repo.deleteById(id);
    }
}
