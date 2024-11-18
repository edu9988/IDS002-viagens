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
import com.fatecrl.viagens.exception.InvalidArgumentException;
import com.fatecrl.viagens.exception.ResourceNotFoundException;
import com.fatecrl.viagens.mapper.TravelMapper;
import com.fatecrl.viagens.model.Customer;
import com.fatecrl.viagens.model.Location;
import com.fatecrl.viagens.model.Travel;
import com.fatecrl.viagens.service.TravelService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/travels")
public class TravelController {

    @Autowired
    private TravelService travelService;

    @Autowired
    private TravelMapper mapper;
    
    @GetMapping(produces="application/json")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
        description = "Returns Travels list")
    })
    public ResponseEntity<List<TravelDTO>> getAll(){
        return ResponseEntity.ok(
            mapper.toDTO( travelService.findAll() )
        );
    }

    @GetMapping(value="/{id}",produces="application/json")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
        description = "Returns Travel"),
        @ApiResponse(responseCode = "400",
        description = "Client input error"), /* happens when {id} contains alphabetic, even though swagger blocks it */
        @ApiResponse(responseCode = "404",
        description = "Travel not found")
    })
    public ResponseEntity<TravelDTO> get( @PathVariable("id") Long id ){
        Travel travel = travelService.find(id).orElse(null);

        if( travel != null )
            return ResponseEntity.ok(
                mapper.toDTO( travel )
            );
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TravelDTO> create( @Valid @RequestBody TravelDTO dto ){

        Customer customerEntity = travelService
            .findCustomer( dto.getCustomer() )
            .orElseThrow(()->new ResourceNotFoundException(
                "/api/travels",
                "Customer with ID "
                + dto.getCustomer()
                + " does not exist"
            ));

        Location sourceEntity = travelService
            .findLocation( dto.getSource() )
            .orElseThrow(()->new ResourceNotFoundException(
                "/api/travels",
                "Location with ID "
                + dto.getSource()
                + " (source) does not exist"
            ));

        Location destinationEntity = travelService
            .findLocation( dto.getDestination() )
            .orElseThrow(()->new ResourceNotFoundException(
                "/api/travels",
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

        if( travelService.hasNoFunds(
            customerEntity.getId(),
            entity.getAmount()
        ))
            throw new InvalidArgumentException(
                "/api/travels",
                "Not enough funds for customer with ID "
                + customerEntity.getId()
            );

        if( travelService.hasConflictingDates(
            customerEntity,
            entity.getStartDateTime(),
            entity.getEndDateTime()
        ))
            throw new InvalidArgumentException(
                "/api/travels",
                "Customer with ID "
                + customerEntity.getId()
                + " has other travels with overlapping dates"
            );

        if(
            entity.getStartDateTime().isAfter(entity.getEndDateTime())
        )
            throw new InvalidArgumentException(
                "/api/travels",
                "The starting date (startDateTime) of the travel is after its ending date (endDateTime)"
            );
        
        if(
            entity.getStartDateTime().isEqual(entity.getEndDateTime())
        )
            throw new InvalidArgumentException(
                "/api/travels",
                "The starting date (startDateTime) of the travel is the same as its ending date (endDateTime)"
            );

        travelService.create( entity );
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand( entity.getId() )
            .toUri();
        dto.setId( entity.getId() );
        return ResponseEntity.created( uri ).body( dto );

        //return ResponseEntity.unprocessable()         (422)  (to do)
        //return ResponseEntity.internalServerError()   (500)  (to do)
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelDTO> update(
        @PathVariable("id") Long id,
        @RequestBody TravelDTO dto
    ){
        if( !travelService.travelExists( id ) )
            return ResponseEntity.notFound().build();

        Customer customerEntity = travelService
            .findCustomer( dto.getCustomer() )
            .orElseThrow(()->new ResourceNotFoundException(
                "/api/travels/"+id,
                "Customer with ID "
                + dto.getCustomer()
                + " does not exist"
            ));

        Location sourceEntity = travelService
            .findLocation( dto.getSource() )
            .orElseThrow(()->new ResourceNotFoundException(
                "/api/travels/"+id,
                "Location with ID "
                + dto.getSource()
                + " (source) does not exist"
            ));

        Location destinationEntity = travelService
            .findLocation( dto.getDestination() )
            .orElseThrow(()->new ResourceNotFoundException(
                "/api/travels/"+id,
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

        if( travelService.hasNoFunds(
            customerEntity.getId(),
            entity.getAmount()
        ))
            throw new InvalidArgumentException(
                "/api/travels",
                "Not enough funds for customer with ID "
                + customerEntity.getId()
            );

        if( travelService.hasConflictingDates(
            customerEntity,
            entity.getStartDateTime(),
            entity.getEndDateTime()
        ))
            throw new InvalidArgumentException(
                "/api/travels",
                "Customer with ID "
                + customerEntity.getId()
                + " has other travels with overlapping dates"
            );

        if(
            entity.getStartDateTime().isAfter(entity.getEndDateTime())
        )
            throw new InvalidArgumentException(
                "/api/travels",
                "The starting date (startDateTime) of the travel is after its ending date (endDateTime)"
            );
        
        if(
            entity.getStartDateTime().isEqual(entity.getEndDateTime())
        )
            throw new InvalidArgumentException(
                "/api/travels",
                "The starting date (startDateTime) of the travel is the same as its ending date (endDateTime)"
            );

        travelService.update( id , entity );
        dto.setId(id);
        return ResponseEntity.ok(dto);
    
        //return ResponseEntity.badRequest()      (400)  (to do)
        //return ResponseEntity.unprocessable()   (422)  (to do)
    }

    @PatchMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
        description = "Travel dates updated successfully"),
        @ApiResponse(responseCode = "400",
        description = "Client input error"),
        @ApiResponse(responseCode = "404",
        description = "Travel not found"),
        @ApiResponse(responseCode = "422",
        description = "Customer has other travels with conflicting dates")
    })
    public ResponseEntity<Void> patch(
        @PathVariable("id") Long id,
        @Valid
        @RequestBody TravelDatesDTO datesDTO
    ){
        if( !travelService.travelExists( id ) )
            return ResponseEntity.notFound().build();

        Travel travelEntity = travelService.find(id).get();
        Customer customerEntity = travelEntity.getCustomer();

        if( travelService.hasConflictingDates(
            customerEntity,
            datesDTO.getStartDateTime(),
            datesDTO.getEndDateTime(),
            id
        ))
            throw new InvalidArgumentException(
                "/api/travels/"+id,
                "Customer with ID "
                + customerEntity.getId()
                + " has other travels with overlapping dates"
            );

        travelService.updateDates(travelEntity, datesDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
        description = "Deleted successfully"),
        @ApiResponse(responseCode = "400",
        description = "Client input error"),
        @ApiResponse(responseCode = "404",
        description = "Travel not found")
    })
    public ResponseEntity<Void> delete( @PathVariable("id") Long id ) {
        if( !travelService.travelExists(id) )
            return ResponseEntity.notFound().build();
        travelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
