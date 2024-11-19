package com.fatecrl.viagens.service;

import java.util.List;
import java.util.Optional;

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
    public List<Location> findAll(){
        return repo.findAll();
    }

   @Override
    public Optional<Location> find( Long id ){
        return repo.findById(id);
    }

    public List<Location> findByParams(
        String name ,
        String nickname,
        String city
    ){
        List<Location> locations;
        if( name == null || name.isEmpty() ){
            if( nickname == null || nickname.isEmpty() )
                locations = repo.findByCityContaining( city );
            else if( city == null || city.isEmpty() )
                locations = repo.findByNicknameContaining( nickname );
            else /* nickname and city != null */
                locations = repo.findByNicknameContainingAndCityContaining( nickname , city );
        }
        else if( nickname == null || nickname.isEmpty() ){
            if( name == null || name.isEmpty() )
                locations = repo.findByCityContaining( city );
            else if( city == null || city.isEmpty() )
                locations = repo.findByNameContaining( name );
            else /* name and city != null */
                locations = repo.findByNameContainingAndCityContaining( name , city );
        }
        else if( city == null || city.isEmpty() ){
            if( name == null || name.isEmpty() )
                locations = repo.findByNicknameContaining( nickname );
            else if( nickname == null || nickname.isEmpty() )
                locations = repo.findByNameContaining( name );
            else /* name and nickname != null */
                locations = repo.findByNameContainingAndNicknameContaining( name , nickname );
        }
        else /* none is null */
            locations = repo.findByNameContainingAndNicknameContainingAndCityContaining( name , nickname , city );
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
