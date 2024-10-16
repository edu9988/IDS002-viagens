package com.fatecrl.viagens.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatecrl.viagens.model.Location;

@Service
public class LocationService {

    private static List<Location> locations = new ArrayList<Location>();

    public LocationService(){
        //this is a "fake" location
        Location location = new Location();
        location.setId(1L);
        location.setName("Fatec RL");
        location.setNickname("Fatec Baixada Santista");
        location.setAddress("R. Senador Feijo 777");
        location.setCity("Santos");
        location.setState(45);
        location.setCountry("Brasil");
        locations.add(location);
    }

    public List<Location> findAll(){
        return locations;
    }

    public Location find( Long id ){
        return locations.stream()
            .filter( l -> l.getId() == id )
            .findFirst().orElse(null);
    }

    public Location find( Location loc ){
        return locations.stream()
            .filter( l -> l.equals(loc) )
            .findFirst().orElse(null);
    }

    public void create( Location location ){
        Long newId = (long) (locations.size()+1);
        location.setId( newId );
        locations.add( location );
    }

    public Boolean update( Location location ){
        Location _loc = find( location );
        if( _loc != null ){
            if( !location.getName().isBlank() )
                _loc.setName( location.getName() );
            if( !location.getNickname().isBlank() )
                _loc.setNickname( location.getNickname() );
            if( !location.getAddress().isBlank() )
                _loc.setAddress( location.getAddress() );
            if( !location.getCity().isBlank() )
                _loc.setCity( location.getCity() );
            if( location.getState() != null )
                _loc.setState( location.getState() );
            if( !location.getCountry().isBlank() )
                _loc.setCountry( location.getCountry() );
            return true;
        }
        return false;
    }
}
