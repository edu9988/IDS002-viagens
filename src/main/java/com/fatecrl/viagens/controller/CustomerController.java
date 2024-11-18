package com.fatecrl.viagens.controller;

import java.net.URI;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.fatecrl.viagens.dto.CustomerDTO;
import com.fatecrl.viagens.dto.CustomerStatusDTO;
import com.fatecrl.viagens.exception.ResourceNotFoundException;
import com.fatecrl.viagens.mapper.CustomerMapper;
import com.fatecrl.viagens.model.Customer;
import com.fatecrl.viagens.service.CustomerService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper mapper;

    @GetMapping(produces="application/json")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
        description = "Returns Customers list"),
        @ApiResponse(responseCode = "400",
        description = "Client input error"),
        @ApiResponse(responseCode = "404",
        description = "No Customer matches search parameters")
    })
    public ResponseEntity<List<CustomerDTO>> getAll(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) LocalDate birthDate
    ){
        if( (name != null && !name.isEmpty()) ||
            (birthDate != null )
        ){
            List<Customer> customers = customerService
                .findByParams(name, birthDate);
            if( customers != null && customers.size() > 0 )
                return ResponseEntity.ok( mapper.toDTO(customers) );
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
            mapper.toDTO( customerService.findAll() )
        );
    }

    @GetMapping(value="/{id}", produces="application/json")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
        description = "Returns Customer"),
        @ApiResponse(responseCode = "400",
        description = "Client input error"),
        @ApiResponse(responseCode = "404",
        description = "Customer not found")
    })
    public ResponseEntity<CustomerDTO> get( @PathVariable("id") Long id ){
        Customer customer = customerService.find(id).orElse(null);

        if( customer != null )
            return ResponseEntity.ok( mapper.toDTO(customer) );
        return ResponseEntity.notFound().build();
    }

    @PostMapping(produces="application/json")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
        description = "Created successfully"),
        @ApiResponse(responseCode = "400",
        description = "Client input error")
    })
    public ResponseEntity<CustomerDTO> create( @Valid @RequestBody CustomerDTO dto ){
        Customer entity = mapper.toEntity(dto);
        customerService.create( entity );
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(  entity.getId() )
            .toUri();
            dto.setId(entity.getId());
        return ResponseEntity.created( uri ).body( dto );
        //return ResponseEntity.badRequest()            (400)  (to do)
        //return ResponseEntity.unprocessable()         (422)  (to do)
        //return ResponseEntity.internalServerError()   (500)  (to do)
    }

    @PutMapping(value="/{id}", produces="application/json")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
        description = "Customer updated successfully"),
        @ApiResponse(responseCode = "400",
        description = "Client input error"),
        @ApiResponse(responseCode = "404",
        description = "Customer not found")
    })
    public ResponseEntity<CustomerDTO> update(
        @PathVariable("id") Long id,
        @RequestBody CustomerDTO dto
    ){
        if( !customerService.customerExists(id) )
            return ResponseEntity.notFound().build();

        customerService.update( id , mapper.toEntity(dto) );
        dto.setId(id);
        return ResponseEntity.ok().body( dto );
        //return ResponseEntity.badRequest()      (400)  (to do)
        //return ResponseEntity.unprocessable()   (422)  (to do)
    }   

    @PatchMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
        description = "Customer updated successfully"),
        @ApiResponse(responseCode = "400",
        description = "Client input error"),
        @ApiResponse(responseCode = "404",
        description = "Customer not found")
    })
    public ResponseEntity<Void> patch(
        @PathVariable("id") Long id,
        @Valid @RequestBody CustomerStatusDTO statusDTO
    ){
        if( customerService.updateStatus(id,mapper.toEntity(statusDTO)) )
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
        description = "Deleted successfully"),
        @ApiResponse(responseCode = "400",
        description = "Customer is referenced by some Travel entity"),
        @ApiResponse(responseCode = "404",
        description = "Customer not found")
    })
    public ResponseEntity<Void> delete( @PathVariable("id") Long id ) {
        if( !customerService.customerExists(id ))
            return ResponseEntity.notFound().build();
        if( customerService.referencedBySomeTravel( id ) )
            throw new ResourceNotFoundException(
                "/api/customers/"+id,
                "Customer with ID "
                + id
                + " is referenced by some travel"
            );

        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}