package com.fatecrl.viagens.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.fatecrl.viagens.dto.LocationDTO;
import com.fatecrl.viagens.model.Location;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class LocationMapper {

    public Location toEntity( LocationDTO dto ){
        Location loc = new Location();
        loc.setAddress(dto.getAddress());
        loc.setCity(dto.getCity());
        loc.setCountry(dto.getCountry());
        loc.setName(dto.getName());
        loc.setNickname(dto.getNickname());
        loc.setState(dto.getState());
        return loc;
    }

    public LocationDTO toDTO( Location loc ){
        return new LocationDTO(
            loc.getId(),
            loc.getName(),
            loc.getNickname(),
            loc.getAddress(),
            loc.getCity(),
            loc.getState(),
            loc.getCountry()
        );
    }

    public Page<LocationDTO> toDTO( Page<Location> ls ){
        return ls.map( l -> toDTO(l) );
    }
}
