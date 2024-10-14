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
        location.setName("Joseph");
        location.setNickname("joe");
        location.setAddress("R. Senador Feijo 777");
        location.setCity("Santos");
        location.setCountry("Brasil");
        locations.add(location);
    }

    public List<Location> findAll(){
        return locations;
    }
}
