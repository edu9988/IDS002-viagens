package com.fatecrl.viagens.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatecrl.viagens.model.Travel;
import com.fatecrl.viagens.service.TravelService;

@RestController
@RequestMapping("/travels")
public class TravelController {

    @Autowired
    private TravelService travelService;

    @GetMapping
    public ResponseEntity<List<Travel>> getAll(){
        return ResponseEntity.ok(travelService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Travel> get( @PathVariable("id") Long id ){
        Travel travel = travelService.find(id);

        if( travel != null )
            return ResponseEntity.ok(travel);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Travel> create( @RequestBody Travel travel ){
        travelService.create( travel );
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand( travel.getId() )
            .toUri();
        return ResponseEntity.created( uri ).body( travel );
        //return ResponseEntity.badRequest()            (400)  (to do)
        //return ResponseEntity.unprocessable()         (422)  (to do)
        //return ResponseEntity.internalServerError()   (500)  (to do)
    }

    @PutMapping
    public ResponseEntity<Travel> update( @RequestBody Travel travel ){
        if( travelService.update(travel) )
            return ResponseEntity.ok(travel);
        
        return ResponseEntity.notFound().build();
        //return ResponseEntity.badRequest()      (400)  (to do)
        //return ResponseEntity.unprocessable()   (422)  (to do)
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Travel> delete( @PathVariable("id") Long id ) {
        if( travelService.delete(id) )
            return ResponseEntity.noContent().build();
        
        return ResponseEntity.notFound().build();
    }
}
