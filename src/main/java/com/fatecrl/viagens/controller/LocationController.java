package com.fatecrl.viagens.controller;

import java.net.URI;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.fatecrl.viagens.exception.InvalidArgumentException;
import com.fatecrl.viagens.mapper.LocationMapper;
import com.fatecrl.viagens.model.Location;
import com.fatecrl.viagens.service.LocationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/locations")
public class LocationController implements IController<LocationDTO>{

    @Autowired
    private LocationService locService;

    @Autowired
    private LocationMapper mapper;

    @GetMapping(produces="application/json")
    @Operation(summary = "Get all Locations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
        description = "Returns Locations list"),
        @ApiResponse(responseCode = "404",
        description = "Location not found")
    })
    public ResponseEntity<Page<LocationDTO>> getAll(
        @RequestParam(required=false) String name,
        @RequestParam(required=false) String nickname,
        @RequestParam(required=false) String city,
        @ParameterObject Pageable pageable
    ){
        if( (name != null && !name.isEmpty()) ||
            (nickname != null && !nickname.isEmpty()) ||
            (city != null && !city.isEmpty())
        ){
            Page<Location> locations = locService
                .findByParams(name,nickname,city,pageable);
            return ResponseEntity.ok( mapper.toDTO(locations) );
        }

        return ResponseEntity.ok( 
            mapper.toDTO( locService.findAll( pageable ) )
        );
    }

    @GetMapping(value="/{id}",produces="application/json")
    @Operation(summary = "Get a Location by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
        description = "Returns Location"),
        @ApiResponse(responseCode = "400",
        description = "Client input error"),
        @ApiResponse(responseCode = "404",
        description = "Location not found")
    })
    public ResponseEntity<LocationDTO> get(
        @PathVariable("id") Long id
    ){
        Location loc = locService.find(id).orElse(null);

        if( loc != null )
            return ResponseEntity.ok(
                mapper.toDTO( loc )
            );
        return ResponseEntity.notFound().build();
    }

    @PostMapping(produces="application/json")
    @Operation(summary = "Create a Location")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
        description = "Created successfully"),
        @ApiResponse(responseCode = "400",
        description = "Client input error")
    })
    public ResponseEntity<LocationDTO> create(
        @Valid @RequestBody LocationDTO dto
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
        /* every input field is String, so there's no
        possible parsing error or inconsistency to
        return 422 */
        //any internalServerError (500) ?
    }

    @PutMapping(value="/{id}", produces="application/json")
    @Operation(summary = "Update a Location by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
        description = "Location updated successfully"),
        @ApiResponse(responseCode = "400",
        description = "Client input error"),
        @ApiResponse(responseCode = "404",
        description = "Location not found")
    })
    public ResponseEntity<LocationDTO> update(
        @PathVariable("id") Long id,
        @Valid @RequestBody LocationDTO dto
    ){
        if( !locService.locationExists(id) )
            return ResponseEntity.notFound().build();

        locService.update( id , mapper.toEntity(dto) );
        dto.setId(id);
        return ResponseEntity.ok(dto);
        //any internalServerError (500) ?
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Location by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
        description = "Deleted successfully"),
        @ApiResponse(responseCode = "404",
        description = "Location not found"),
        @ApiResponse(responseCode = "422",
        description = "Location is referenced by some Travel entity")
    })
    public ResponseEntity<Void> delete( @PathVariable("id") Long id ) {
        if( !locService.locationExists(id) )
            return ResponseEntity.notFound().build();
        if( locService.referencedBySomeTravel( id ) )
            throw new InvalidArgumentException(
                "/api/locations/"+id,
                "Location with ID "
                + id
                + " is referenced by some travel"
            );
            
        locService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
