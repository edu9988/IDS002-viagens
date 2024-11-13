package com.fatecrl.viagens.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatecrl.viagens.dto.LocationDTO;
import com.fatecrl.viagens.mapper.LocationMapper;
import com.fatecrl.viagens.model.Location;
import com.fatecrl.viagens.service.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locService;

    @Autowired
    private LocationMapper mapper;

    @GetMapping
    public ResponseEntity<List<LocationDTO>> getAll(
        @RequestParam(required=false) String name,
        @RequestParam(required=false) String nickname,
        @RequestParam(required=false) String city
    ){
        if( (name != null && !name.isEmpty()) ||
            (nickname != null && !nickname.isEmpty()) ||
            (city != null && !city.isEmpty())
        ){
            List<Location> locations = locService
                .findByParams(name,nickname,city);
            if( locations != null && locations.size() > 0 )
                return ResponseEntity.ok( mapper.toDTO(locations) );
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok( 
            mapper.toDTO( locService.findAll() )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> get( @PathVariable("id") Long id ){
        Location loc = locService.find(id).orElse(null);

        if( loc != null )
            return ResponseEntity.ok(
                mapper.toDTO( loc )
            );
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<LocationDTO> create(
        @RequestBody LocationDTO dto
    ){
        Location loc = mapper.toEntity(dto);
        locService.create( loc );
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand( loc.getId() )
            .toUri();
        dto.setId( loc.getId() );
        return ResponseEntity.created( uri ).body( dto );
        //return ResponseEntity.badRequest()            (400)  (to do)
        //return ResponseEntity.unprocessable()         (422)  (to do)
        //return ResponseEntity.internalServerError()   (500)  (to do)
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> update(
        @PathVariable("id") Long id,
        @RequestBody LocationDTO dto
    ){
        if( locService.update( id , mapper.toEntity(dto) ) ){
            dto.setId(id);
            return ResponseEntity.ok(dto);
        }
        
        return ResponseEntity.notFound().build();
        //return ResponseEntity.badRequest()      (400)  (to do)
        //return ResponseEntity.unprocessable()   (422)  (to do)
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LocationDTO> delete( @PathVariable("id") Long id ) {
        if( locService.delete(id) )
            return ResponseEntity.noContent().build();
        
        return ResponseEntity.notFound().build();
    }
}
