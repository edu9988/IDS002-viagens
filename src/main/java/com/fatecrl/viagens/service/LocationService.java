package com.fatecrl.viagens.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatecrl.viagens.model.Location;
import com.fatecrl.viagens.repository.LocationRepository;

@Service
public class LocationService implements IService<Location>{

    @Autowired
    private LocationRepository repo;

    public LocationService(){
    }

    @Override
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
        else /* city == null || city.isEmpty() */{
            if( name == null || name.isEmpty() )
                locations = repo.findByNicknameContaining( nickname );
            else if( nickname == null || nickname.isEmpty() )
                locations = repo.findByNameContaining( name );
            else /* name and nickname != null */
                locations = repo.findByNameContainingAndNicknameContaining( name , nickname );
        }
        
        return locations;
    }

    @Override
    public void create( Location location ){
        repo.save(location);
    }

    @Override
    public Boolean update( Long id , Location location ){
        if( repo.existsById( id ) ){
            location.setId(id);
            repo.save(location);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete( Long id ){
        if( repo.existsById(id) ){
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
