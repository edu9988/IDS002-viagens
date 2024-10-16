package com.fatecrl.viagens.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatecrl.viagens.model.Location;
import com.fatecrl.viagens.service.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationsController {

    @Autowired
    private LocationService locService;

    @GetMapping
    public ResponseEntity<List<Location>> getAll(){
        return ResponseEntity.ok(locService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> get( @PathVariable("id") Long id ){
        Location loc = locService.find(id);

        if( loc != null )
            return ResponseEntity.ok(loc);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Location> create( @RequestBody Location loc ){
        locService.create( loc );
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand( loc.getId() )
            .toUri();
        return ResponseEntity.created( uri ).body( loc );
    }

    @PutMapping
    public ResponseEntity<Location> update( @RequestBody Location loc ){
        if( locService.update(loc) )
            return ResponseEntity.ok(loc);
        
        return ResponseEntity.notFound().build();
    }
}
