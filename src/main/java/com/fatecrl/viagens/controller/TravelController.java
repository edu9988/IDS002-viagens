package com.fatecrl.viagens.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatecrl.viagens.dto.TravelDTO;
import com.fatecrl.viagens.dto.TravelDatesDTO;
import com.fatecrl.viagens.exception.ResourceNotFoundException;
import com.fatecrl.viagens.mapper.TravelMapper;
import com.fatecrl.viagens.model.Customer;
import com.fatecrl.viagens.model.Location;
import com.fatecrl.viagens.model.Travel;
import com.fatecrl.viagens.service.TravelService;

@RestController
@RequestMapping("/travels")
public class TravelController {

    @Autowired
    private TravelService travelService;

    @Autowired
    private TravelMapper mapper;
    
    @GetMapping
    public ResponseEntity<List<TravelDTO>> getAll(){
        return ResponseEntity.ok(
            mapper.toDTO( travelService.findAll() )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelDTO> get( @PathVariable("id") Long id ){
        Travel travel = travelService.find(id).orElse(null);

        if( travel != null )
            return ResponseEntity.ok(
                mapper.toDTO( travel )
            );
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TravelDTO> create( @RequestBody TravelDTO dto ){

        Customer customerEntity = travelService
            .findCustomer( dto.getCustomer() )
            .orElseThrow(()->new ResourceNotFoundException(
                "Customer with ID "
                + dto.getCustomer()
                + " does not exist"
            ));

        Location sourceEntity = travelService
            .findLocation( dto.getSource() )
            .orElseThrow(()->new ResourceNotFoundException(
                "Location with ID "
                + dto.getSource()
                + " (source) does not exist"
            ));

        Location destinationEntity = travelService
        .findLocation( dto.getDestination() )
        .orElseThrow(()->new ResourceNotFoundException(
            "Location with ID "
            + dto.getDestination()
            + " (destination) does not exist"
        ));

        Travel entity = mapper.toEntity(
            dto,
            customerEntity,
            sourceEntity,
            destinationEntity
        );

        travelService.create( entity );
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand( entity.getId() )
            .toUri();
        dto.setId( entity.getId() );
        return ResponseEntity.created( uri ).body( dto );
        //return ResponseEntity.badRequest()            (400)  (to do)
        //return ResponseEntity.unprocessable()         (422)  (to do)
        //return ResponseEntity.internalServerError()   (500)  (to do)
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelDTO> update(
        @PathVariable("id") Long id,
        @RequestBody TravelDTO dto
    ){
        Customer customerEntity = travelService
            .findCustomer( dto.getCustomer() )
            .orElseThrow(()->new ResourceNotFoundException(
                "Customer with ID "
                + dto.getCustomer()
                + " does not exist"
            ));

        Location sourceEntity = travelService
            .findLocation( dto.getSource() )
            .orElseThrow(()->new ResourceNotFoundException(
                "Location with ID "
                + dto.getSource()
                + " (source) does not exist"
            ));

        Location destinationEntity = travelService
        .findLocation( dto.getDestination() )
        .orElseThrow(()->new ResourceNotFoundException(
            "Location with ID "
            + dto.getDestination()
            + " (destination) does not exist"
        ));

        Travel entity = mapper.toEntity(
            dto,
            customerEntity,
            sourceEntity,
            destinationEntity
        );
        
        if( travelService.update( id , entity ) ){
            dto.setId(id);
            return ResponseEntity.ok(dto);
        }
        
        return ResponseEntity.notFound().build();
        //return ResponseEntity.badRequest()      (400)  (to do)
        //return ResponseEntity.unprocessable()   (422)  (to do)
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TravelDTO> patch(
        @PathVariable("id") Long id,
        @RequestBody TravelDatesDTO datesDTO
    ){
        if( travelService.updateDates(id, mapper.toEntity(datesDTO)) )
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TravelDTO> delete( @PathVariable("id") Long id ) {
        if( travelService.delete(id) )
            return ResponseEntity.noContent().build();
        
        return ResponseEntity.notFound().build();
    }
}
