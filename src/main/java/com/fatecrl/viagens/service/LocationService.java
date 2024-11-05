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

    /*
    public Location find( Location loc ){
        return locations.stream()
            .filter( l -> l.equals(loc) )
            .findFirst().orElse(null);
    }
    */


    public List<Location> findByParams( String name ,
        String nickname, String city
    ){
        List<Location> locs = repo.findAll();
        if( name != null && !name.isEmpty() ){
            locs = locs.stream()
                .filter( l -> l.getName()
                    .toLowerCase()
                    .indexOf( name.toLowerCase() ) > -1
                )
                .toList();
            if( nickname != null && !nickname.isEmpty() ){
                locs = locs.stream()
                    .filter( l -> l.getNickname()
                        .toLowerCase()
                        .indexOf( nickname.toLowerCase() ) > -1
                    )
                    .toList();
                if( city != null && !city.isEmpty() ){
                    locs = locs.stream()
                        .filter( l -> l.getCity()
                            .toLowerCase()
                            .indexOf( city.toLowerCase() ) > -1
                        )
                        .toList();
                }
            }
        }
        else if( nickname != null && !nickname.isEmpty() ){
            locs = locs.stream()
                .filter( l -> l.getNickname()
                    .toLowerCase()
                    .indexOf( nickname.toLowerCase() ) > -1
                )
                .toList();
            if( city != null && !city.isEmpty() ){
                locs = locs.stream()
                    .filter( l -> l.getCity()
                        .toLowerCase()
                        .indexOf( city.toLowerCase() ) > -1
                    )
                    .toList();
            }
        }
        else{
            locs = locs.stream()
                .filter( l -> l.getCity()
                    .toLowerCase()
                    .indexOf( city.toLowerCase() ) > -1
                )
                .toList();
        }
        return locs;
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
